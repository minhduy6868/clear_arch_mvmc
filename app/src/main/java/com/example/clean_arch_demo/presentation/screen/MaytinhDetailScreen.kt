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
import com.example.clean_arch_demo.data.local.Maytinh

@Composable
fun MaytinhDetailScreen(
    maytinh: Maytinh,
    isEditing: Boolean,
    onBack: () -> Unit,
    onEditToggle: () -> Unit,
    onSave: (Maytinh) -> Unit,
    onCancel: () -> Unit
) {
    if (isEditing) {
        MaytinhEditContent(
            maytinh = maytinh,
            onSave = onSave,
            onCancel = onCancel
        )
    } else {
        MaytinhViewContent(
            maytinh = maytinh,
            onBack = onBack,
            onEditToggle = onEditToggle
        )
    }
}

@Composable
private fun MaytinhViewContent(
    maytinh: Maytinh,
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
            text = maytinh.tenmay,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Đơn giá - nhâp dạng so: ${maytinh.dongia}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Loại máy: ${maytinh.loaimay}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Số lượng - dạng số: ${maytinh.soluong}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun MaytinhEditContent(
    maytinh: Maytinh,
    onSave: (Maytinh) -> Unit,
    onCancel: () -> Unit
) {
    var tenMay by remember { mutableStateOf(maytinh.tenmay) }
    var donGia by remember { mutableStateOf(maytinh.dongia.toString()) }
    var loaiMay by remember { mutableStateOf(maytinh.loaimay) }
    var soLuong by remember { mutableStateOf(maytinh.soluong.toString()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Hủy")
            }
            Button(onClick = {
                onSave(
                    Maytinh(
                        tenmay = tenMay,
                        dongia = donGia.toDoubleOrNull() ?: 0.0,
                        loaimay = loaiMay,
                        soluong = soLuong.toIntOrNull() ?: 0,
                        mamay = maytinh.mamay
                    )
                )
            }) {
                Text("Lưu")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tenMay,
            onValueChange = { tenMay = it },
            label = { Text("Tên máy") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = donGia,
            onValueChange = { donGia = it },
            label = { Text("Đơn giá") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = loaiMay,
            onValueChange = { loaiMay = it },
            label = { Text("Loại máy") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = soLuong,
            onValueChange = { soLuong = it },
            label = { Text("Số lượng") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}