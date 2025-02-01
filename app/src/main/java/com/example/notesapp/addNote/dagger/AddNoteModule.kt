package com.example.notesapp.addNote.dagger

import android.content.Context
import androidx.room.Room
import com.example.notesapp.addNote.domain.AddNoteUseCase
import com.example.notesapp.addNote.domain.DeleteNoteUseCase
import com.example.notesapp.addNote.domain.GetNoteUseCase
import com.example.notesapp.data.data.local.AppDatabase
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import com.example.notesapp.home.domain.GetNotesUseCase
import com.example.notesapp.home.domain.ListenNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object AddNoteModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    fun provideGetNoteUseCase(repository: NotesRepository): GetNoteUseCase {
        return GetNoteUseCase(repository)
    }

    @Provides
    fun provideAddNoteUseCase(repository: NotesRepository): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    fun deleteNoteRepository(repository: NotesRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
}