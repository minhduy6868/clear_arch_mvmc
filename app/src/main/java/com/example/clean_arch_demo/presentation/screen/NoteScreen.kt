package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.presentation.view_model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    var noteName by remember { mutableStateOf("") }
    var noteBody by remember { mutableStateOf("") }
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedNote by viewModel.selectedNote.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ghi chú") },
                actions = {
                    if (selectedNote != null && !isEditing) {
                        IconButton(onClick = { viewModel.startEditing() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                selectedNote != null && isEditing -> {
                    NoteEditContent(
                        note = selectedNote!!,
                        onSave = { updatedNote ->
                            viewModel.updateNote(updatedNote)
                        },
                        onCancel = {
                            viewModel.cancelEditing()
                        }
                    )
                }
                selectedNote != null -> {
                    NoteViewContent(
                        note = selectedNote!!,
                        onBack = { viewModel.clearSelectedNote() }
                    )
                }
                else -> {
                    // Search bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        label = { Text("Tìm kiếm...") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add note form
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = noteName,
                                onValueChange = { noteName = it },
                                label = { Text("Tiêu đề") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = noteBody,
                                onValueChange = { noteBody = it },
                                label = { Text("Nội dung") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (noteName.isNotEmpty() && noteBody.isNotEmpty()) {
                                        viewModel.addNote(noteName, noteBody)
                                        noteName = ""
                                        noteBody = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Thêm Ghi Chú")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Notes list
                    if (notes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Không có ghi chú nào")
                        }
                    } else {
                        LazyColumn {
                            items(notes) { note ->
                                NoteItem(
                                    note = note,
                                    onDelete = { viewModel.deleteNote(note) },
                                    onClick = { viewModel.selectNote(note) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteViewContent(
    note: Note,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
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
                onSave(Note(
                    noteName = title,
                    noteBody = content,
                    noteId = note.noteId
                ))
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

@Composable
fun NoteItem(
    note: Note,
    onDelete: (Note) -> Unit,
    onClick: (Note) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(note) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.noteName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Button(
                    onClick = { onDelete(note) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Xóa")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.noteBody,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}