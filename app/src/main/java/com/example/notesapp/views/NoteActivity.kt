package com.example.notesapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityNoteBinding
import com.example.notesapp.db.entity.Note
import com.example.notesapp.di.factories.NoteViewModelFactory
import com.example.notesapp.viewmodels.NoteViewModel
import javax.inject.Inject

class NoteActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NoteViewModelFactory

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: ActivityNoteBinding
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performInjection()
        initExtra()
        initViewModel()
        initBinding()
        initObservables()
    }

    private fun initObservables() {
        viewModel.isNoteNameEmpty.observe(this, Observer {
            if (it)
                binding.noteNameEt.error = getString(R.string.note_name_error)
        })
        viewModel.isNoteDescriptionEmpty.observe(this, Observer {
            if (it)
                binding.noteDescriptionEt.error = getString(R.string.note_description_error)
        })
        viewModel.isShouldCloseEditor.observe(this, Observer {
            if (it)
                finish()
        })

        viewModel.savedNoteLd.observe(this, Observer {
            Toast.makeText(this, "'" + it.name +"' " +  getString(R.string.was_saved), Toast.LENGTH_SHORT).show()

        })

        CommonUI.observeDeletingNote(this, this, viewModel.deletedNoteLd, getString(R.string.was_deleted))
    }

    private fun initBinding() {
        binding = ActivityNoteBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.note = Note()
        binding.isEditMode = false

        note?.let {
            binding.note = note
            binding.isEditMode = true
        }

        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun initExtra() {
        note = intent.getParcelableExtra<Note>("note")
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)
    }

    private fun performInjection() {
        NotesApplication.instance.component.inject(this)
    }
}