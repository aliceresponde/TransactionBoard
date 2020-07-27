package com.aliceresponde.transactionboard.presentation.transactions

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.useCase.GetTransactionsUseCase
import com.aliceresponde.transactionboard.presentation.transactions.TransactionMessage.EMPTY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsViewModel @ViewModelInject constructor(private val useCase: GetTransactionsUseCase) :
    ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _transactionVisibility = MutableLiveData<Int>(GONE)
    val transactionVisibility: LiveData<Int> get() = _transactionVisibility

    private val _loadingVisibility = MutableLiveData<Int>(GONE)
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _messageVisibility = MutableLiveData<Int>(GONE)
    val messageVisibility: LiveData<Int> get() = _messageVisibility

    private val _labelMessage = MutableLiveData<TransactionMessage>()
    val labelMessage: LiveData<TransactionMessage> get() = _labelMessage

    fun getTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                val data = useCase.getTransactions()
                if (data.isEmpty()) setMessage(EMPTY)
                else showData(data)
            }
        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                useCase.deleteAllTransactions()
            }
        }
    }

    fun restoreTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                useCase.restoreData()
            }
        }
    }

    private fun showData(data: List<Transaction>) {
        _messageVisibility.postValue(GONE)
        _loadingVisibility.postValue(GONE)
        _transactionVisibility.postValue(VISIBLE)
        _transactions.postValue(data)
    }

    private fun setMessage(message: TransactionMessage) {
        _loadingVisibility.postValue(GONE)
        _labelMessage.postValue(message)
        _messageVisibility.postValue(VISIBLE)
        _transactionVisibility.postValue(GONE)
    }
}


enum class TransactionMessage { EMPTY, INTERNET_ERROR }