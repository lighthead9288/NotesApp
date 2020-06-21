package com.example.notesapp.di.modules

import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.repository.NotesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotesRepositoryModule {

    @Singleton
    @Provides
    fun provideNotesRepository(database: NotesDatabase): NotesRepository {
        return NotesRepository(database)
    }
}