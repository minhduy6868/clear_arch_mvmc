package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.presentation.view_model.NoteViewModel

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    var noteName by remember { mutableStateOf("") }
    var noteBody by remember { mutableStateOf("") }
    val notes by viewModel.notes.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Ô nhập tiêu đề ghi chú
        OutlinedTextField(
            value = noteName,
            onValueChange = { noteName = it },
            label = { Text("Tiêu đề") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập nội dung ghi chú
        OutlinedTextField(
            value = noteBody,
            onValueChange = { noteBody = it },
            label = { Text("Nội dung") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nút thêm ghi chú
        Button(
            onClick = {
                if (noteName.isNotEmpty() && noteBody.isNotEmpty()) {
                    viewModel.addNote(noteName, noteBody)
                    noteName = ""  // Xóa nội dung sau khi thêm
                    noteBody = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Thêm Ghi Chú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị danh sách ghi chú
        LazyColumn {
            items(notes) { note ->
                NoteItem(note = note, onDelete = { viewModel.deleteNote(note) })
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tiêu đề: ${note.noteName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Nội dung: ${note.noteBody}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onDelete(note) },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Xóa Ghi Chú")
            }
        }
    }
}
