package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.clean_arch_demo.R
import com.example.clean_arch_demo.presentation.view_model.MaytinhViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(viewModel: MaytinhViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf(
        TabItem("Máy tính", R.drawable.ic_launcher_foreground), // Replace with your actual icons
        TabItem("Số hoàn hảo", R.drawable.ic_launcher_background),
        TabItem("Phương trình", R.drawable.ic_launcher_foreground)
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
                1 -> HoanHaoScreen()
                2 -> PhuongTrinhScreen()
            }
        }
    }
}

@Composable
fun HoanHaoScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Chương trình tính toán số hoàn hảo")
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Nhập số nguyên dương") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val n = number.toIntOrNull() ?: 0
                result = if (isPerfectNumber(n)) {
                    "$n là số hoàn hảo"
                } else {
                    "$n không phải số hoàn hảo"
                }
            }
        ) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}

fun isPerfectNumber(n: Int): Boolean {
    if (n <= 1) return false
    var sum = 1 // 1 is proper divisor for all n > 1
    for (i in 2 until n) {
        if (n % i == 0) {
            sum += i
        }
    }
    return sum == n
}

@Composable
fun PhuongTrinhScreen() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Chương trình tính phương trinh ax + b = 0 ")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = a,
            onValueChange = { a = it },
            label = { Text("Hệ số a") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = b,
            onValueChange = { b = it },
            label = { Text("Hệ số b") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val aValue = a.toDoubleOrNull() ?: 0.0
                val bValue = b.toDoubleOrNull() ?: 0.0
                result = solveLinearEquation(aValue, bValue)
            }
        ) {
            Text("Giải phương trình")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}

fun solveLinearEquation(a: Double, b: Double): String {
    return when {
        a == 0.0 && b == 0.0 -> "Phương trình có vô số nghiệm"
        a == 0.0 -> "Phương trình vô nghiệm"
        else -> "Nghiệm x = ${-b / a}"
    }
}

// Data class cho mỗi tab item
data class TabItem(
    val title: String,
    val icon: Int
)