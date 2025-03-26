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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.clean_arch_demo.data.local.Hocphan
import com.example.clean_arch_demo.presentation.view_model.HocphanViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HocphanScreen(viewModel: HocphanViewModel) {
    var tenHocPhan by remember { mutableStateOf("") }
    var soTinChi by remember { mutableStateOf("") }
    var hocKy by remember { mutableStateOf("") }

    val danhSachHocPhan by viewModel.danhSachHocPhan.collectAsStateWithLifecycle()
    val tuKhoaTimKiem by viewModel.tuKhoaTimKiem.collectAsStateWithLifecycle()
    val hocPhanDuocChon by viewModel.hocPhanDuocChon.collectAsStateWithLifecycle()
    val dangChinhSua by viewModel.dangChinhSua.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý học sinh") },
                actions = {
                    if (hocPhanDuocChon != null && !dangChinhSua) {
                        IconButton(onClick = { viewModel.batDauChinhSua() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                        }
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
                hocPhanDuocChon != null && dangChinhSua -> {
                    HocphanEditContent(
                        hocphan = hocPhanDuocChon!!,
                        onSave = { updatedHocphan ->
                            viewModel.capNhatHocPhan(updatedHocphan)
                        },
                        onCancel = {
                            viewModel.huyChinhSua()
                        }
                    )
                }
                hocPhanDuocChon != null -> {
                    HocphanViewContent(
                        hocphan = hocPhanDuocChon!!,
                        onBack = { viewModel.xoaHocPhanDuocChon() }
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
                                value = tenHocPhan,
                                onValueChange = { tenHocPhan = it },
                                label = { Text("Tên học sinh") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = soTinChi,
                                onValueChange = { soTinChi = it },
                                label = { Text("Số tín chỉ") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = hocKy,
                                onValueChange = { hocKy = it },
                                label = { Text("Học kỳ") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (tenHocPhan.isNotEmpty() && soTinChi.isNotEmpty() && hocKy.isNotEmpty()) {
                                        viewModel.themHocPhan(
                                            tenHocPhan,
                                            soTinChi.toIntOrNull() ?: 0,
                                            hocKy
                                        )
                                        tenHocPhan = ""
                                        soTinChi = ""
                                        hocKy = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Thêm học phần")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // List
                    if (danhSachHocPhan.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Không có học phần nào")
                        }
                    } else {
                        LazyColumn {
                            items(danhSachHocPhan) { hocphan ->
                                HocphanItem(
                                    hocphan = hocphan,
                                    onDelete = { viewModel.xoaHocPhan(hocphan) },
                                    onClick = { viewModel.chonHocPhan(hocphan) }
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
private fun HocphanViewContent(
    hocphan: Hocphan,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
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

@Composable
fun HocphanItem(
    hocphan: Hocphan,
    onDelete: (Hocphan) -> Unit,
    onClick: (Hocphan) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(hocphan) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hocphan.tenhocphan,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Button(
                    onClick = { onDelete(hocphan) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Xóa")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${hocphan.sotinchi} tín chỉ - Học kỳ ${hocphan.hocky}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}