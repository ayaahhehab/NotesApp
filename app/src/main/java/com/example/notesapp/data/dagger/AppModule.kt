package com.example.notesapp.data.dagger

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.data.local.AppDatabase
import com.example.notesapp.data.data.repository.NotesRepositoryImpl
import com.example.notesapp.data.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(appDatabase: AppDatabase): NotesRepository {
        return NotesRepositoryImpl(appDatabase.noteDao())
    }
}





