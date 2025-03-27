package com.example.clean_arch_demo.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clean_arch_demo.data.local.Maytinh
import com.example.clean_arch_demo.domain.usecase.MaytinhUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MaytinhViewModel(private val useCase: MaytinhUseCase) : ViewModel() {
    private val _danhSachMaytinh = MutableStateFlow<List<Maytinh>>(emptyList())
    val danhSachMaytinh = _danhSachMaytinh.asStateFlow()

    private val _tuKhoaTimKiem = MutableStateFlow("")
    val tuKhoaTimKiem = _tuKhoaTimKiem.asStateFlow()

    private val _maytinhDuocChon = MutableStateFlow<Maytinh?>(null)
    val maytinhDuocChon = _maytinhDuocChon.asStateFlow()

    private val _dangChinhSua = MutableStateFlow(false)
    val dangChinhSua = _dangChinhSua.asStateFlow()

    init {
        taiDanhSachMaytinh()
    }

    fun taiDanhSachMaytinh() {
        viewModelScope.launch {
            if (_tuKhoaTimKiem.value.isEmpty()) {
                useCase.getAllMaytinh().collect { danhSach ->
                    _danhSachMaytinh.value = danhSach
                }
            } else {
                useCase.searchMaytinh(_tuKhoaTimKiem.value).collect { danhSach ->
                    _danhSachMaytinh.value = danhSach
                }
            }
        }
    }

    fun datTuKhoaTimKiem(tuKhoa: String) {
        _tuKhoaTimKiem.value = tuKhoa
        taiDanhSachMaytinh()
    }

    fun chonMaytinh(maytinh: Maytinh) {
        _maytinhDuocChon.value = maytinh
        _dangChinhSua.value = false
    }

    fun batDauChinhSua() {
        _dangChinhSua.value = true
    }

    fun huyChinhSua() {
        _dangChinhSua.value = false
    }

    fun xoaMaytinhDuocChon() {
        _maytinhDuocChon.value = null
        _dangChinhSua.value = false
    }

    fun themMaytinh(tenMay: String, soLuong: Int, donGia: Double, loaiMay: String) {
        viewModelScope.launch {
            useCase.addMaytinh(tenMay, donGia, loaiMay, soLuong)
            taiDanhSachMaytinh()
        }
    }

    fun capNhatMaytinh(maytinhDaCapNhat: Maytinh) {
        viewModelScope.launch {
            useCase.updateMaytinh(
               // mamay = maytinhDaCapNhat.mamay,
                tenmay = maytinhDaCapNhat.tenmay,
                dongia = maytinhDaCapNhat.dongia,
                soluong = maytinhDaCapNhat.soluong,
                loaimay = maytinhDaCapNhat.loaimay
            )
            _maytinhDuocChon.value = maytinhDaCapNhat
            _dangChinhSua.value = false
            taiDanhSachMaytinh()
        }
    }

    fun xoaMaytinh(maytinh: Maytinh) {
        viewModelScope.launch {
            useCase.deleteMaytinh(maytinh)
            if (_maytinhDuocChon.value?.mamay == maytinh.mamay) {
                xoaMaytinhDuocChon()
            }
            taiDanhSachMaytinh()
        }
    }
}