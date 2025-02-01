package com.example.notesapp.data.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.NotesApp

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

//    companion object{
//        private var _instance: AppDatabase? = null
//        fun getInstance(): AppDatabase {
//            if (_instance == null){
//                _instance = Room.databaseBuilder(
//                    NotesApp.appContext,
//                    AppDatabase::class.java,
//                    "AppDatabase"
//                ).build()
//            }
//            return _instance!!
//        }
//    }
}