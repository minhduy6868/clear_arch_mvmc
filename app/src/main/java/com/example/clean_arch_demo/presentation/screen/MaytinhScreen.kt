package com.example.clean_arch_demo.presentation.screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý máy tính", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    if (maytinhDuocChon != null && !dangChinhSua) {
                        IconButton(onClick = { viewModel.batDauChinhSua() }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Chỉnh sửa",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text("Giới thiệu", style = MaterialTheme.typography.bodyMedium)
                                },
                                onClick = {
                                    expanded = false
                                    showDialog = true
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text("Điều lệ", style = MaterialTheme.typography.bodyMedium)
                                },
                                onClick = { expanded = false },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when {
                maytinhDuocChon != null && dangChinhSua -> {
                    MaytinhEditContent(
                        maytinh = maytinhDuocChon!!,
                        onSave = { updatedMaytinh ->
                            viewModel.capNhatMaytinh(updatedMaytinh)
                        },
                        onCancel = { viewModel.huyChinhSua() }
                    )
                }

                maytinhDuocChon != null -> {
                    MaytinhViewContent(
                        maytinh = maytinhDuocChon!!,
                        onBack = { viewModel.xoaMaytinhDuocChon() }
                    )
                }

                else -> {
                    // Search bar with improved styling
                    OutlinedTextField(
                        value = tuKhoaTimKiem,
                        onValueChange = { viewModel.datTuKhoaTimKiem(it) },
                        label = { Text("Tìm kiếm...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Tìm kiếm")
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add form with improved card styling
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Thêm máy tính mới",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OutlinedTextField(
                                value = tenMay,
                                onValueChange = { tenMay = it },
                                label = { Text("Tên máy") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small,
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedTextField(
                                    value = soLuong,
                                    onValueChange = { soLuong = it },
                                    label = { Text("Số lượng") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = MaterialTheme.shapes.small
                                )

                                OutlinedTextField(
                                    value = donGia,
                                    onValueChange = { donGia = it },
                                    label = { Text("Đơn giá") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = MaterialTheme.shapes.small
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = loaiMay,
                                onValueChange = { loaiMay = it },
                                label = { Text("Loại máy") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small,
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (tenMay.isNotEmpty() && soLuong.isNotEmpty() &&
                                        donGia.isNotEmpty() && loaiMay.isNotEmpty()) {
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = MaterialTheme.shapes.medium,
                                enabled = tenMay.isNotEmpty() && soLuong.isNotEmpty() &&
                                        donGia.isNotEmpty() && loaiMay.isNotEmpty()
                            ) {
                                Text("Thêm máy tính", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // List section with improved empty state
                    if (danhSachMaytinh.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    "Không có máy tính nào",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Đóng")
                }
            },
            title = {
                Text(
                    "Giới thiệu",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    "Ứng dụng quản lý máy tính phiên bản 1.0\n\n" +
                            "Quản lý thông tin các loại máy tính trong kho",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MaytinhViewContent(
    maytinh: Maytinh,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Quay lại",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = maytinh.tenmay,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                InfoRow(label = "Số lượng", value = maytinh.soluong.toString())
                InfoRow(label = "Đơn giá", value = maytinh.dongia.toString())
                InfoRow(label = "Loại máy", value = maytinh.loaimay)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("Hủy")
            }
            Button(
                onClick = {
                    onSave(
                        Maytinh(
                            tenmay = tenMay,
                            soluong = soLuong.toIntOrNull() ?: 0,
                            dongia = donGia.toDoubleOrNull() ?: 0.0,
                            loaimay = loaiMay,
                            mamay = maytinh.mamay
                        )
                    )
                },
                modifier = Modifier.weight(1f),
                enabled = tenMay.isNotEmpty() && soLuong.isNotEmpty() &&
                        donGia.isNotEmpty() && loaiMay.isNotEmpty()
            ) {
                Text("Lưu")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = tenMay,
                    onValueChange = { tenMay = it },
                    label = { Text("Tên máy") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = soLuong,
                        onValueChange = { soLuong = it },
                        label = { Text("Số lượng") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = MaterialTheme.shapes.small
                    )

                    OutlinedTextField(
                        value = donGia,
                        onValueChange = { donGia = it },
                        label = { Text("Đơn giá") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = MaterialTheme.shapes.small
                    )
                }

                OutlinedTextField(
                    value = loaiMay,
                    onValueChange = { loaiMay = it },
                    label = { Text("Loại máy") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    singleLine = true
                )
            }
        }
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
            .clickable { onClick(maytinh) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = maytinh.tenmay,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "SL: ${maytinh.soluong} • ${maytinh.dongia} VND • ${maytinh.loaimay}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = { onDelete(maytinh) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Xóa",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}