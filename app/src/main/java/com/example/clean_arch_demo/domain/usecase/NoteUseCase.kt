package com.example.clean_arch_demo.domain.usecase

import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteUseCase(private val repository: NoteRepository) {   // xử li logic nghiep vu
    fun getAllNotes(): Flow<List<Note>> = repository.getAllNotes()

    suspend fun addNote(title: String, content: String) {
        repository.insertNote(Note(title, content))
    }

    suspend fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }
}
