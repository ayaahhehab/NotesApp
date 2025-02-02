package com.example.notesapp.home.dagger

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.notesapp.NotesApp
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
//inject dependencies (like NotesRepository,
// GetNotesUseCase, and ListenNotesUseCase)
// automatically into the classes that need them,
// without having to manually create instances.


@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {



    @Provides
    fun provideGetNotesUseCase(repository: NotesRepository): GetNotesUseCase{
        return GetNotesUseCase(repository) //Returning an instance of GetNotesUseCase
    }

    @Provides
    fun provideListenNotesUseCase(repository: NotesRepository): ListenNotesUseCase{
        return ListenNotesUseCase(repository) //Returning an instance of ListenNotesUseCase
    }
}