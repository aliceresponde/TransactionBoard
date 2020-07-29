package com.aliceresponde.transactionboard.presentation.transactions

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.useCase.transaction.GetTransactionsUseCase
import com.aliceresponde.transactionboard.presentation.transactions.TransactionMessage.EMPTY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsViewModel @ViewModelInject constructor
    (private val useCase: GetTransactionsUseCase) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>(listOf())
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _labelMessage = MutableLiveData<TransactionMessage>()
    val labelMessage: LiveData<TransactionMessage> get() = _labelMessage

    private val _transactionVisibility = MutableLiveData<Int>(GONE)
    val transactionVisibility: LiveData<Int> get() = _transactionVisibility

    private val _loadingVisibility = MutableLiveData<Int>(GONE)
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _messageVisibility = MutableLiveData<Int>(GONE)
    val messageVisibility: LiveData<Int> get() = _messageVisibility

    fun getTransactions() {
        transactions.value?.let {
            if (it.isNotEmpty()) return
        }
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                val data = useCase.getTransactions()
                updateContentToShow(data)
                _loadingVisibility.postValue(GONE)
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            withContext(IO) {
                val data = useCase.deleteTransaction(transaction)
                showLoading()
                updateContentToShow(data)
                _loadingVisibility.postValue(GONE)
            }
        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                val data = useCase.deleteAllTransactions()
                updateContentToShow(data)
                _loadingVisibility.postValue(GONE)
            }
        }
    }

    fun restoreTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                val data = useCase.restoreData()
                updateContentToShow(data)
                _loadingVisibility.postValue(GONE)
            }
        }
    }

    private fun updateContentToShow(data: List<Transaction>) {
        if (data.isEmpty()) noDataAble()
        else showData(data)
    }

    private fun showLoading() {
        _loadingVisibility.postValue(VISIBLE)
        _messageVisibility.postValue(GONE)
        _transactionVisibility.postValue(GONE)
    }

    private fun noDataAble() {
        _messageVisibility.postValue(VISIBLE)
        _labelMessage.postValue(EMPTY)
        _transactionVisibility.postValue(GONE)
        _transactions.postValue(listOf())
    }

    private fun showData(data: List<Transaction>) {
        _messageVisibility.postValue(GONE)
        _transactionVisibility.postValue(VISIBLE)
        _transactions.postValue(data)
    }

    private fun setMessage(message: TransactionMessage) {
        _labelMessage.postValue(message)
        _messageVisibility.postValue(VISIBLE)
        _transactionVisibility.postValue(GONE)
    }
}


enum class TransactionMessage { EMPTY, INTERNET_ERROR }