package com.example.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase
        .getDatabase(application)
        .noteDao()

    val notes = noteDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addNote(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) return

        viewModelScope.launch {
            noteDao.insert(
                Note(
                    title = title.trim(),
                    content = content.trim(),
                    date = LocalDateTime.now()
                )
            )
        }
    }

    fun updateNote(note: Note, title: String, content: String) {
        if (title.isBlank() || content.isBlank()) return

        viewModelScope.launch {
            noteDao.update(
                note.copy(
                    title = title.trim(),
                    content = content.trim(),
                    date = LocalDateTime.now()
                )
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            noteDao.deleteAll()
        }
    }
}
