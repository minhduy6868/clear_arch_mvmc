package com.example.clean_arch_demo.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clean_arch_demo.data.local.Hocphan
import com.example.clean_arch_demo.domain.usecase.HocphanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HocphanViewModel(private val useCase: HocphanUseCase) : ViewModel() {
    private val _danhSachHocPhan = MutableStateFlow<List<Hocphan>>(emptyList())
    val danhSachHocPhan = _danhSachHocPhan.asStateFlow()

    private val _tuKhoaTimKiem = MutableStateFlow("")
    val tuKhoaTimKiem = _tuKhoaTimKiem.asStateFlow()

    private val _hocPhanDuocChon = MutableStateFlow<Hocphan?>(null)
    val hocPhanDuocChon = _hocPhanDuocChon.asStateFlow()

    private val _dangChinhSua = MutableStateFlow(false)
    val dangChinhSua = _dangChinhSua.asStateFlow()

    init {
        taiDanhSachHocPhan()
    }

    fun taiDanhSachHocPhan() {
        viewModelScope.launch {
            if (_tuKhoaTimKiem.value.isEmpty()) {
                useCase.getAllHocPhan().collect { danhSach ->
                    _danhSachHocPhan.value = danhSach
                }
            } else {
                useCase.searchHocPhan(_tuKhoaTimKiem.value).collect { danhSach ->
                    _danhSachHocPhan.value = danhSach
                }
            }
        }
    }

    fun datTuKhoaTimKiem(tuKhoa: String) {
        _tuKhoaTimKiem.value = tuKhoa
        taiDanhSachHocPhan()
    }

    fun chonHocPhan(hocphan: Hocphan) {
        _hocPhanDuocChon.value = hocphan
        _dangChinhSua.value = false
    }

    fun batDauChinhSua() {
        _dangChinhSua.value = true
    }

    fun huyChinhSua() {
        _dangChinhSua.value = false
    }

    fun xoaHocPhanDuocChon() {
        _hocPhanDuocChon.value = null
        _dangChinhSua.value = false
    }

    fun themHocPhan(tenHocPhan: String, soTinChi: Int, hocKy: String) {
        viewModelScope.launch {
            useCase.addHocPhan(tenHocPhan, soTinChi, hocKy)
            taiDanhSachHocPhan()
        }
    }

    fun capNhatHocPhan(hocphanDaCapNhat: Hocphan) {
        viewModelScope.launch {
            useCase.updateHocPhan(
                tenhocphan = hocphanDaCapNhat.tenhocphan,
                sotinchi = hocphanDaCapNhat.sotinchi,
                hocky = hocphanDaCapNhat.hocky
            )
            _hocPhanDuocChon.value = hocphanDaCapNhat
            _dangChinhSua.value = false
            taiDanhSachHocPhan()
        }
    }

    fun xoaHocPhan(hocphan: Hocphan) {
        viewModelScope.launch {
            useCase.deleteHocPhan(hocphan)
            if (_hocPhanDuocChon.value?.mahocphan == hocphan.mahocphan) {
                xoaHocPhanDuocChon()
            }
            taiDanhSachHocPhan()
        }
    }
}