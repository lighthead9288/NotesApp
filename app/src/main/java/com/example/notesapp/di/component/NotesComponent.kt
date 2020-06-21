package com.example.notesapp.di.component

import com.example.notesapp.di.modules.NotesRepositoryModule
import com.example.notesapp.di.modules.RoomDbModule
import com.example.notesapp.views.NoteActivity
import com.example.notesapp.views.NotesListActivity
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules=[RoomDbModule::class, NotesRepositoryModule::class])
interface NotesComponent {
    fun inject(notesListActivity: NotesListActivity)
    fun inject(noteActivity: NoteActivity)
}