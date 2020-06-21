package com.example.notesapp

import android.app.Application
import com.example.notesapp.di.component.DaggerNotesComponent
import com.example.notesapp.di.component.NotesComponent
import com.example.notesapp.di.modules.RoomDbModule

class NotesApplication: Application() {

    companion object {
        lateinit var instance: NotesApplication

    }

    lateinit var component: NotesComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        component = DaggerNotesComponent.builder().roomDbModule(RoomDbModule(this)).build()

    }
}