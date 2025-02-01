package com.example.notesapp.data.data.local

import android.icu.text.CaseMap.Title
import android.util.EventLogTags.Description
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.data.domain.models.NoteModel

@Entity (tableName = "Notes")
data class NoteEntity(
    @PrimaryKey (autoGenerate = true) val id: Int?,
    val title: String?,
    val description: String?,
)
fun NoteEntity.toModel(): NoteModel {
    return NoteModel(
        id = this.id ?: -1,
        title = this.title ?: "",
        description = this.description ?: ""
    )
}
