//package com.example.clean_arch_demo.presentation.screen
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.clean_arch_demo.data.local.Hocphan
//
//@Composable
//fun HocphanDetailScreen(
//    hocphan: Hocphan,
//    isEditing: Boolean,
//    onBack: () -> Unit,
//    onEditToggle: () -> Unit,
//    onSave: (Hocphan) -> Unit,
//    onCancel: () -> Unit
//) {
//    if (isEditing) {
//        HocphanEditContent(
//            hocphan = hocphan,
//            onSave = onSave,
//            onCancel = onCancel
//        )
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                IconButton(onClick = onBack) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
//                }
//                IconButton(onClick = onEditToggle) {
//                    Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = hocphan.tenhocphan,
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Số tín chỉ: ${hocphan.sotinchi}",
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Học kỳ: ${hocphan.hocky}",
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//    }
//}