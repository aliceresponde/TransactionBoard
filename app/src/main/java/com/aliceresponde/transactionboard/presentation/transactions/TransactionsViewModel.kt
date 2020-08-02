package com.aliceresponde.transactionboard.presentation.transactions

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aliceresponde.transactionboard.data.remote.NetworkConnection
import com.aliceresponde.transactionboard.domain.ErrorState
import com.aliceresponde.transactionboard.domain.SuccessState
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.useCase.transaction.GetTransactionsUseCase
import com.aliceresponde.transactionboard.presentation.transactions.TransactionMessage.EMPTY
import com.aliceresponde.transactionboard.presentation.transactions.TransactionMessage.INTERNET_ERROR
import com.aliceresponde.transactionboard.utils.Event
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ActivityScoped
class TransactionsViewModel @ViewModelInject constructor
    (private val useCase: GetTransactionsUseCase) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>(listOf())
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _navigateToDetail = MutableLiveData<Event<Transaction>>()
    val navigateToDetail: LiveData<Event<Transaction>> get() = _navigateToDetail


    private val _labelMessage = MutableLiveData<TransactionMessage>()
    val labelMessage: LiveData<TransactionMessage> get() = _labelMessage

    private val _transactionVisibility = MutableLiveData<Int>(GONE)
    val transactionVisibility: LiveData<Int> get() = _transactionVisibility

    private val _loadingVisibility = MutableLiveData<Int>(GONE)
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _messageVisibility = MutableLiveData<Int>(GONE)
    val messageVisibility: LiveData<Int> get() = _messageVisibility

    private var isNetworkConnected: Boolean  = false

    fun updateConnectionState(isConnected: Boolean) {
        isNetworkConnected = isConnected
    }

    fun getTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                when (val data = useCase.getTransactions()) {
                    is SuccessState -> {
                        val transactions = data.data ?: listOf()
                        if (transactions.isEmpty()) setMessage(EMPTY)
                        else updateContentToShow(transactions)
                    }
                    is ErrorState -> setMessage(INTERNET_ERROR)
                }
                hideLoading()
            }
        }
    }

    fun restoreTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                when (val data = useCase.restoreData()) {
                    is SuccessState -> {
                        val transactions = data.data ?: listOf()
                        if (transactions.isEmpty()) setMessage(EMPTY)
                        else updateContentToShow(transactions)
                    }
                    is ErrorState -> setMessage(INTERNET_ERROR)
                }
                hideLoading()
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                when (val data = useCase.deleteTransaction(transaction)) {
                    is SuccessState -> {
                        val transactions = data.data ?: listOf()
                        if (transactions.isEmpty()) setMessage(EMPTY)
                        else updateContentToShow(transactions)
                    }
                    is ErrorState -> setMessage(INTERNET_ERROR)
                }
                hideLoading()
            }
        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch {
            withContext(IO) {
                showLoading()
                when (val data = useCase.deleteAllTransactions()) {
                    is SuccessState -> {
                        val transactions = data.data ?: listOf()
                        if (transactions.isEmpty()) setMessage(EMPTY)
                        else updateContentToShow(transactions)
                    }
                    is ErrorState -> setMessage(INTERNET_ERROR)
                }
                hideLoading()
            }
        }
    }

    fun onTransactionClickedListener(transaction: Transaction) {
        if (transaction.isNew) {
            viewModelScope.launch {
                withContext(IO) {
                    showLoading()
                    useCase.updateTransaction(transaction, false)
                    hideLoading()
                }
            }
        }
        _navigateToDetail.value = Event(transaction)
    }

    private fun hideLoading() {
        _loadingVisibility.postValue(GONE)
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
        _transactions.postValue(listOf())
        _labelMessage.postValue(message)
        _messageVisibility.postValue(VISIBLE)
        _transactionVisibility.postValue(GONE)
    }
}

enum class TransactionMessage { EMPTY, INTERNET_ERROR }