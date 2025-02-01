package com.example.notesapp.addNote.domain

import com.example.notesapp.data.domain.models.NoteModel
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteUseCase (
    private val repository: NotesRepository

) {

    suspend fun execute(id:Int): NoteModel {
        return withContext(Dispatchers.IO){
            repository.get(id)

        }

    }
//    companion object{
//        private var _instance: GetNoteUseCase? = null
//        fun getInstance (): GetNoteUseCase {
//            if(_instance == null){
//                _instance = GetNoteUseCase()
//            }
//            return  _instance!!
//        }
//    }
}