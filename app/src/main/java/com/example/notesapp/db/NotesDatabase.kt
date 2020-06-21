package com.example.notesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.db.dao.NoteDao
import com.example.notesapp.db.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}