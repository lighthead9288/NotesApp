package com.example.notesapp.views

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.notesapp.db.entity.Note



open class CommonUI: Activity() {


    companion object {
        fun observeDeletingNote(owner: LifecycleOwner, context: Context, ld: LiveData<Note>, message: String) {
            ld.observe(owner, Observer {
                Toast.makeText(context, "'" + it.name +"' " +   message, Toast.LENGTH_SHORT).show()
            })
        }
    }
}