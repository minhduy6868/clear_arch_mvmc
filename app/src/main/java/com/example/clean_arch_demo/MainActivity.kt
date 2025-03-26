package com.example.clean_arch_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.clean_arch_demo.data.local.HocphanDatabase
import com.example.clean_arch_demo.data.repository.HocphanRepository
import com.example.clean_arch_demo.domain.usecase.HocphanUseCase
import com.example.clean_arch_demo.presentation.screen.TabScreen
import com.example.clean_arch_demo.presentation.view_model.HocphanViewModel
import com.example.clean_arch_demo.ui.theme.Clean_arch_demoTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteDao = HocphanDatabase.getInstance(this).hocphanDao()
        val repository = HocphanRepository(noteDao)
        val useCase = HocphanUseCase(repository)
        val viewModel = HocphanViewModel(useCase)

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