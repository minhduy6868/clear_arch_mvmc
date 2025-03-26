package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clean_arch_demo.data.local.Note

@Composable
fun NoteDetailScreen(
    note: Note,
    isEditing: Boolean,
    onBack: () -> Unit,
    onEditToggle: () -> Unit,
    onSave: (Note) -> Unit,
    onCancel: () -> Unit
) {
    if (isEditing) {
        NoteEditContent(
            note = note,
            onSave = onSave,
            onCancel = onCancel
        )
    } else {
        NoteViewContent(
            note = note,
            onBack = onBack,
            onEditToggle = onEditToggle
        )
    }
}

@Composable
private fun NoteViewContent(
    note: Note,
    onBack: () -> Unit,
    onEditToggle: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
            }
            IconButton(onClick = onEditToggle) {
                Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = note.noteName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = note.noteBody,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun NoteEditContent(
    note: Note,
    onSave: (Note) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(note.noteName) }
    var content by remember { mutableStateOf(note.noteBody) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Hủy")
            }
            Button(onClick = {
                onSave(Note(title, content, note.noteId))
            }) {
                Text("Lưu")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tiêu đề") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Nội dung") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )
    }
}