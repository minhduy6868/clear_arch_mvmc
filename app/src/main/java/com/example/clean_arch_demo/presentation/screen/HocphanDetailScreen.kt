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
import com.example.clean_arch_demo.data.local.Hocphan

@Composable
fun HocphanDetailScreen(
    hocphan: Hocphan,
    isEditing: Boolean,
    onBack: () -> Unit,
    onEditToggle: () -> Unit,
    onSave: (Hocphan) -> Unit,
    onCancel: () -> Unit
) {
    if (isEditing) {
        HocphanEditContent(
            hocphan = hocphan,
            onSave = onSave,
            onCancel = onCancel
        )
    } else {
        HocphanViewContent(
            hocphan = hocphan,
            onBack = onBack,
            onEditToggle = onEditToggle
        )
    }
}

@Composable
private fun HocphanViewContent(
    hocphan: Hocphan,
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
            text = hocphan.tenhocphan,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Số tín chỉ: ${hocphan.sotinchi}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Học kỳ: ${hocphan.hocky}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun HocphanEditContent(
    hocphan: Hocphan,
    onSave: (Hocphan) -> Unit,
    onCancel: () -> Unit
) {
    var ten by remember { mutableStateOf(hocphan.tenhocphan) }
    var tinChi by remember { mutableStateOf(hocphan.sotinchi.toString()) }
    var kyHoc by remember { mutableStateOf(hocphan.hocky) }

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
                    Hocphan(
                        tenhocphan = ten,
                        sotinchi = tinChi.toIntOrNull() ?: 0,
                        hocky = kyHoc,
                        mahocphan = hocphan.mahocphan
                    )
                )
            }) {
                Text("Lưu")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = ten,
            onValueChange = { ten = it },
            label = { Text("Tên học phần") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tinChi,
            onValueChange = { tinChi = it },
            label = { Text("Số tín chỉ") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = kyHoc,
            onValueChange = { kyHoc = it },
            label = { Text("Học kỳ") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}