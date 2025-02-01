package com.example.notesapp.data.domain.repository

import com.example.notesapp.data.data.local.NoteEntity
import com.example.notesapp.data.domain.models.NoteModel
import kotlinx.coroutines.flow.SharedFlow

interface NotesRepository {
    val newNoteInsertionListener: SharedFlow<NoteModel>
    val updateNoteListener: SharedFlow<NoteModel>
    val deleteNoteListener: SharedFlow<Int>

    suspend fun getAll(): List<NoteModel>
    suspend fun get(id: Int): NoteModel

    suspend fun insert(item: NoteModel): Int
    suspend fun update(item: NoteModel)
    suspend fun delete(id: Int)
}