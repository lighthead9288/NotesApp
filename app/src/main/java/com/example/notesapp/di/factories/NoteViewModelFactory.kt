package com.example.notesapp.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.viewmodels.NoteViewModel
import javax.inject.Inject

class NoteViewModelFactory @Inject constructor(val notesRepository: NotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(notesRepository) as T
        throw IllegalAccessException()
    }
}