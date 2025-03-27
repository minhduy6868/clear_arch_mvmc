package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clean_arch_demo.data.local.Maytinh
import com.example.clean_arch_demo.presentation.view_model.MaytinhViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaytinhScreen(viewModel: MaytinhViewModel) {
    var tenMay by remember { mutableStateOf("") }
    var soLuong by remember { mutableStateOf("") }
    var donGia by remember { mutableStateOf("") }
    var loaiMay by remember { mutableStateOf("") }

    val danhSachMaytinh by viewModel.danhSachMaytinh.collectAsStateWithLifecycle()
    val tuKhoaTimKiem by viewModel.tuKhoaTimKiem.collectAsStateWithLifecycle()
    val maytinhDuocChon by viewModel.maytinhDuocChon.collectAsStateWithLifecycle()
    val dangChinhSua by viewModel.dangChinhSua.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }


    var showDialog by remember { mutableStateOf(false) }
    // Hiển thị Popup khi showDialog = true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Đóng")
                }
            },
            title = { Text("Giới thiệu") },
            text = { Text("Ứng dụng quản lý học sinh phiên bản 1.0") }
        )
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý máy tính") },
                actions = {
                    if (maytinhDuocChon != null && !dangChinhSua) {
                        IconButton(onClick = { viewModel.batDauChinhSua() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                        }
                    }

                    // Nút menu dropdown
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Giới thiệu") },
                            onClick = {
                                expanded = false
                                showDialog = true // Mở popup
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Điều lệ và bản quyền") },
                            onClick = { }
                        )
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
                maytinhDuocChon != null && dangChinhSua -> {
                    MaytinhEditContent(
                        maytinh = maytinhDuocChon!!,
                        onSave = { updatedMaytinh ->
                            viewModel.capNhatMaytinh(updatedMaytinh)
                        },
                        onCancel = {
                            viewModel.huyChinhSua()
                        }
                    )
                }

                maytinhDuocChon != null -> {
                    MaytinhViewContent(
                        maytinh = maytinhDuocChon!!,
                        onBack = { viewModel.xoaMaytinhDuocChon() }
                    )
                }

                else -> {
                    // Search bar
                    OutlinedTextField(
                        value = tuKhoaTimKiem,
                        onValueChange = { viewModel.datTuKhoaTimKiem(it) },
                        label = { Text("Tìm kiếm...") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add form
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = tenMay,
                                onValueChange = { tenMay = it },
                                label = { Text("Tên máy") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = soLuong,
                                onValueChange = { soLuong = it },
                                label = { Text("Số lượng") },
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

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (tenMay.isNotEmpty() && soLuong.isNotEmpty() && donGia.isNotEmpty() && loaiMay.isNotEmpty()) {
                                        viewModel.themMaytinh(
                                            tenMay,
                                            soLuong.toIntOrNull() ?: 0,
                                            donGia.toDoubleOrNull() ?: 0.0,
                                            loaiMay
                                        )
                                        tenMay = ""
                                        soLuong = ""
                                        donGia = ""
                                        loaiMay = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Thêm máy tính")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // List
                    if (danhSachMaytinh.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Không có máy tính nào")
                        }
                    } else {
                        LazyColumn {
                            items(danhSachMaytinh) { maytinh ->
                                MaytinhItem(
                                    maytinh = maytinh,
                                    onDelete = { viewModel.xoaMaytinh(maytinh) },
                                    onClick = { viewModel.chonMaytinh(maytinh) }
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
private fun MaytinhViewContent(
    maytinh: Maytinh,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = maytinh.tenmay,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Số lượng: ${maytinh.soluong}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Đơn giá: ${maytinh.dongia}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Loại máy: ${maytinh.loaimay}",
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
    var soLuong by remember { mutableStateOf(maytinh.soluong.toString()) }
    var donGia by remember { mutableStateOf(maytinh.dongia.toString()) }
    var loaiMay by remember { mutableStateOf(maytinh.loaimay) }

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
                        soluong = soLuong.toIntOrNull() ?: 0,
                        dongia = donGia.toDoubleOrNull() ?: 0.0,
                        loaimay = loaiMay,
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
            value = soLuong,
            onValueChange = { soLuong = it },
            label = { Text("Số lượng") },
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
    }
}

@Composable
fun MaytinhItem(
    maytinh: Maytinh,
    onDelete: (Maytinh) -> Unit,
    onClick: (Maytinh) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(maytinh) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = maytinh.tenmay,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Button(
                    onClick = { onDelete(maytinh) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Xóa")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "SL: ${maytinh.soluong} - Giá: ${maytinh.dongia} - Loại: ${maytinh.loaimay}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}