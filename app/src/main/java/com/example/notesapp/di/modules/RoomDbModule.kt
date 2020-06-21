package com.example.notesapp.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.db.entity.Note
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class RoomDbModule(val application: Application) {

    private lateinit var notesDatabase: NotesDatabase

    @Provides
    @Singleton
    fun provideRoomDatabase(): NotesDatabase {
        notesDatabase = Room.databaseBuilder(application.applicationContext, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
        return notesDatabase
    }

    @Provides
    @Singleton
    fun provideNoteDao() = notesDatabase.getNoteDao()

    private val databaseCallback = object: RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                prefillDb()
            }
        }
    }

    private fun prefillDb() {
        val note1 = Note(0, "Note1", "Description of note 1")
        val note2 = Note(1, "Note2", "Description of note 2")
        val note3 = Note(2, "Note3", "Description of note 3")

        val noteDao = notesDatabase.getNoteDao()
        noteDao.add(note1)
        noteDao.add(note2)
        noteDao.add(note3)
    }


}