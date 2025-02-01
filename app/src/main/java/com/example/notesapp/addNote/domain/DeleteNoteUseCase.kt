package com.example.notesapp.addNote.domain

import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase (
    private val repository: NotesRepository

){

    suspend fun execute(id: Int){
        withContext(Dispatchers.IO){
            repository.delete(id)
        }

    }
//    companion object{
//        private var _instance: DeleteNoteUseCase? = null
//        fun getInstance (): DeleteNoteUseCase {
//            if(_instance == null){
//                _instance = DeleteNoteUseCase()
//            }
//            return  _instance!!
//        }
//    }
}