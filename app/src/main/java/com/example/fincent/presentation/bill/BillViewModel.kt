package com.example.fincent.presentation.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincent.data.repository.BillRepository
import com.example.fincent.domain.model.Bill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillViewModel @Inject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    private val _bills = MutableStateFlow<List<Bill>>(emptyList())
    val bills: StateFlow<List<Bill>> = _bills.asStateFlow()

    private val _unpaidBills = MutableStateFlow<List<Bill>>(emptyList())
    val unpaidBills: StateFlow<List<Bill>> = _unpaidBills.asStateFlow()

    fun loadBills(userId: String) {
        viewModelScope.launch {
            billRepository.getAllBills(userId).collect { billList ->
                _bills.value = billList
            }
        }
    }

    fun loadUnpaidBills(userId: String) {
        viewModelScope.launch {
            billRepository.getUnpaidBills(userId).collect { billList ->
                _unpaidBills.value = billList
            }
        }
    }

    fun addBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.insertBill(bill)
        }
    }

    fun updateBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.updateBill(bill)
        }
    }

    fun deleteBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.deleteBill(bill)
        }
    }
}
