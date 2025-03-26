package com.example.clean_arch_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.clean_arch_demo.data.local.NoteDatabase
import com.example.clean_arch_demo.data.repository.NoteRepository
import com.example.clean_arch_demo.domain.usecase.NoteUseCase
import com.example.clean_arch_demo.presentation.screen.NoteScreen
import com.example.clean_arch_demo.presentation.view_model.NoteViewModel
import com.example.clean_arch_demo.ui.theme.Clean_arch_demoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = NoteDatabase.getInstance(this)
        val repository = NoteRepository(database.noteDao())
        val useCase = NoteUseCase(repository)
        val viewModel = NoteViewModel(useCase)

        setContent {
            NoteScreen(viewModel)
        }
    }
}
