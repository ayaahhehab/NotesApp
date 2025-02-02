package com.example.notesapp.home.domain

import com.example.notesapp.data.data.local.toModel
import com.example.notesapp.data.domain.models.NoteModel
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNotesUseCase (
    private val repository: NotesRepository

){
    //make sure the data fetching happens
    suspend fun execute(): List<NoteModel>{
        return withContext(Dispatchers.IO){
            repository.getAll()
        }

    }
//    companion object{
//        private var _instance: GetNotesUseCase? = null
//        fun getInstance (): GetNotesUseCase{
//            if(_instance == null){
//                _instance = GetNotesUseCase()
//            }
//            return  _instance!!
//        }
//    }
}