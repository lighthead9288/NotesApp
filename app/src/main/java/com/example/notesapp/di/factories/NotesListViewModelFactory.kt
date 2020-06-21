package com.example.notesapp.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.viewmodels.NotesListViewModel
import javax.inject.Inject

class NotesListViewModelFactory @Inject constructor(val notesRepository: NotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            return NotesListViewModel(notesRepository) as T
        }
        throw IllegalAccessException()
    }
}