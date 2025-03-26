package com.example.clean_arch_demo.domain.usecase

import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteUseCase(private val repository: NoteRepository) {   // xử li logic nghiep vu
    fun getAllNotes(): Flow<List<Note>> = repository.getAllNotes()

    suspend fun addNote(title: String, content: String) {
        repository.insertNote(Note(title, content))
    }

    suspend fun updateNote(title: String, content: String) {
        repository.updateNote(Note(title, content))
    }

    suspend fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }

    fun searchNotes(query: String): Flow<List<Note>> = repository.searchNotes(query)

    suspend fun getNoteById(id: Int): Note? = repository.getNoteById(id)
}
