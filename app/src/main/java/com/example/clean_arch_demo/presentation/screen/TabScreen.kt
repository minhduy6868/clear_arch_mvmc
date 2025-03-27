package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.clean_arch_demo.R
import com.example.clean_arch_demo.presentation.view_model.HocphanViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(viewModel: HocphanViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf(
        TabItem("Học phần", Icons.Default.Home), // Using Material icon
        TabItem("Khác", Icons.Default.DateRange)  // Using Material icon
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = tab.icon, // Directly using the ImageVector
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
                0 -> HocphanScreen(viewModel)
                1 -> MathOperationsScreen()
            }
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



@Composable
fun MathOperationsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card 1: Tính tổng 2 số
        SumCard()

        // Card 2: Kiểm tra số nguyên tố
        PrimeCheckCard()
    }
}

@Composable
fun SumCard() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var sum by remember { mutableStateOf<Int?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tính tổng 2 số",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = number1,
                onValueChange = { number1 = it },
                label = { Text("Số thứ nhất") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = number2,
                onValueChange = { number2 = it },
                label = { Text("Số thứ hai") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val num1 = number1.toIntOrNull() ?: 0
                    val num2 = number2.toIntOrNull() ?: 0
                    sum = num1 + num2
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tính tổng")
            }

            if (sum != null) {
                Text(
                    text = "Kết quả: $sum",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PrimeCheckCard() {
    var number by remember { mutableStateOf("") }
    var isPrimeResult by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Kiểm tra số nguyên tố",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                label = { Text("Nhập số cần kiểm tra") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val num = number.toIntOrNull() ?: 0
                    isPrimeResult = if (isPrime(num))
                        "$num là số nguyên tố"
                    else
                        "$num không phải số nguyên tố"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kiểm tra")
            }

            if (isPrimeResult != null) {
                Text(
                    text = isPrimeResult!!,
                    fontSize = 20.sp,
                    color = if (isPrimeResult!!.contains("là"))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// Hàm kiểm tra số nguyên tố (giữ nguyên)
fun isPrime(n: Int): Boolean {
    if (n <= 1) return false
    if (n == 2) return true
    if (n % 2 == 0) return false

    for (i in 3..Math.sqrt(n.toDouble()).toInt() step 2) {
        if (n % i == 0) return false
    }
    return true
}

// Data class cho mỗi tab item
data class TabItem(
    val title: String,
    val icon: ImageVector
)