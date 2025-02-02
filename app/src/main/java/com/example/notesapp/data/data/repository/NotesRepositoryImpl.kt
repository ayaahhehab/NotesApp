package com.example.notesapp.data.data.repository

import com.example.notesapp.data.data.local.AppDatabase
import com.example.notesapp.data.data.local.NoteDao
import com.example.notesapp.data.data.local.NoteEntity
import com.example.notesapp.data.data.local.toModel
import com.example.notesapp.data.domain.models.NoteModel
import com.example.notesapp.data.domain.models.toEntity
import com.example.notesapp.data.domain.repository.NotesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepositoryImpl (
    val dao: NoteDao

) : NotesRepository {


    private val _newNoteInsertionListener = MutableSharedFlow<NoteModel>()
    override val newNoteInsertionListener: SharedFlow<NoteModel> = _newNoteInsertionListener.asSharedFlow()

    private val _updateNoteListener = MutableSharedFlow<NoteModel>()
    override val updateNoteListener: SharedFlow<NoteModel> = _updateNoteListener.asSharedFlow()

    private val _deleteNoteListener = MutableSharedFlow<Int>()
    override val deleteNoteListener: SharedFlow<Int> = _deleteNoteListener.asSharedFlow()

//    companion object{
//        private var _instance: NotesRepositoryImpl? = null
//
//        fun getInstance(): NotesRepositoryImpl {
//            if (_instance == null)
//                _instance = NotesRepositoryImpl()
//            return _instance as NotesRepositoryImpl
//        }
//    }

    // Fetches all notes from the database and converts them to NoteModel objects.
    override suspend fun getAll(): List<NoteModel>{
        return dao.getAll().map {it.toModel()}
    }

    //Fetches a single note by its ID and converts it to NoteModel.
    override suspend fun get(id:Int) : NoteModel {
        return dao.getItem(id).toModel()
    }

    override suspend fun insert (item: NoteModel): Int{

        val newId = dao.insertItem(item.toEntity()).toInt()
        val newNote = item.copy(
            id = newId
        )
        _newNoteInsertionListener.emit(newNote)
        return newId
    }


    override suspend fun update (item: NoteModel){

        dao.updateItem(item.toEntity())
        _updateNoteListener.emit(item)
    }
    override suspend fun delete(id:Int){
        dao.deleteItem(id)
        _deleteNoteListener.emit(id)
    }
}