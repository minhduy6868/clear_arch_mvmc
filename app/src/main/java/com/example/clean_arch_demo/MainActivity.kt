package com.example.clean_arch_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.clean_arch_demo.data.local.MaytinhDatabase
import com.example.clean_arch_demo.data.repository.MaytinhRepository
import com.example.clean_arch_demo.domain.usecase.MaytinhUseCase
import com.example.clean_arch_demo.presentation.screen.TabScreen
import com.example.clean_arch_demo.presentation.view_model.MaytinhViewModel
import com.example.clean_arch_demo.ui.theme.Clean_arch_demoTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteDao = MaytinhDatabase.getInstance(this).maytinhDao()
        val repository = MaytinhRepository(noteDao)
        val useCase = MaytinhUseCase(repository)
        val viewModel = MaytinhViewModel(useCase)

        setContent {
            Clean_arch_demoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TabScreen(viewModel)
                }
            }
        }
    }
}