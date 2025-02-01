package com.example.notesapp.addNote.domain

import com.example.notesapp.data.domain.models.NoteModel
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNoteUseCase(
    private val repository: NotesRepository
) {

    suspend fun execute(noteModel: NoteModel){
        withContext(Dispatchers.IO){
            if (noteModel.id == -1){
                repository.insert(noteModel)
            }else{
                repository.update(noteModel)
            }
        }

    }
//    companion object{
//        private var _instance: AddNoteUseCase? = null
//        fun getInstance (): AddNoteUseCase {
//            if(_instance == null){
//                _instance = AddNoteUseCase()
//            }
//            return  _instance!!
//        }
//    }
}