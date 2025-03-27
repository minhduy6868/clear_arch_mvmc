package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.clean_arch_demo.R
import com.example.clean_arch_demo.presentation.view_model.MaytinhViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(viewModel: MaytinhViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf(
        TabItem("Học phần", R.drawable.ic_launcher_foreground), // Thay bằng icon thực tế
        TabItem("Số hoàn hảo", R.drawable.ic_launcher_background),
        TabItem("ax + b", R.drawable.ic_launcher_foreground )
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = tab.icon),
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(tab.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> MaytinhScreen(viewModel)
                1 -> HoanhaoTabScreen()
                2 -> PhuongtrinhTabScreen()
            }
        }
    }
}

@Composable
fun HoanhaoTabScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Đây là tab khác")
    }
}

// Data class cho mỗi tab item
data class TabItem(
    val title: String,
    val icon: Int
)

@Composable
fun PhuongtrinhTabScreen() {
}