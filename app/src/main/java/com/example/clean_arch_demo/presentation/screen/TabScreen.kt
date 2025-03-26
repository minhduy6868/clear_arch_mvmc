package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.clean_arch_demo.presentation.view_model.NoteViewModel
import androidx.compose.ui.Modifier

@Composable
fun TabScreen(viewModel: NoteViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Ghi chú", "Khác")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when (selectedTab) {
            0 -> NoteScreen(viewModel)
            1 -> OtherTabScreen()
        }
    }
}

@Composable
fun OtherTabScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Đây là tab khác")
    }
}