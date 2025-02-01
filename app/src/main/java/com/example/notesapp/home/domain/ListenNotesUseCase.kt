package com.example.notesapp.home.domain

import com.example.notesapp.data.domain.models.NoteModel
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListenNotesUseCase(
    private val repository: NotesRepository

) {
    suspend fun execute(): Flow<NotesEvent> {

            return channelFlow {
                withContext(Dispatchers.IO){
                    launch {
                        repository.newNoteInsertionListener.collect{ newNote ->
                            send(NotesEvent.Insert(newNote))
                        }
                    }
                    launch {
                        repository.updateNoteListener.collect{updateNote ->
                            send (NotesEvent.Update(updateNote))
                        }
                    }
                    launch {
                        repository.deleteNoteListener.collect{id ->
                            send(NotesEvent.Delete(id))
                        }
                    }
                }
            }
    }
//    companion object{
//        private var _instance: ListenNotesUseCase?= null
//        fun getInstance(): ListenNotesUseCase{
//            if (_instance == null){
//                _instance = ListenNotesUseCase()
//            }
//            return _instance!!
//        }
//    }
}
sealed interface NotesEvent{
    data class Insert(val value: NoteModel): NotesEvent
    data class Update(val value: NoteModel): NotesEvent
    data class Delete(val value: Int): NotesEvent

}