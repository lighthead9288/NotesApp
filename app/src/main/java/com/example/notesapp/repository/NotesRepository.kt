package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.db.entity.Note
import kotlinx.coroutines.*
import java.lang.Exception

class NotesRepository(private val notesDatabase: NotesDatabase) {

    private val noteDao = notesDatabase.getNoteDao()

    fun getAllNotes(): LiveData<List<Note>> {
         return noteDao.getAllNotes()

    }

    fun addNote(note: Note): LiveData<Note> {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.add(note)
        }
        return MutableLiveData<Note>().apply { value = note }
    }

    fun updateNote(note: Note): LiveData<Note> {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(note)
        }
        return MutableLiveData<Note>().apply { value = note }
    }

    fun deleteNote(note: Note): LiveData<Note> {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
        return MutableLiveData<Note>().apply { value = note }
    }


}