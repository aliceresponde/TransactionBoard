package com.aliceresponde.transactionboard.presentation.detail

import android.view.View.GONE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliceresponde.transactionboard.domain.SuccessState
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.model.TransactionInfo
import com.aliceresponde.transactionboard.domain.model.User
import com.aliceresponde.transactionboard.domain.useCase.transactionInfo.GetTransactionsInfoUseCase
import com.aliceresponde.transactionboard.domain.useCase.userinfo.GetUserInfoUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionDetailViewModel @ViewModelInject constructor(
    private val getUserInfo: GetUserInfoUseCase,
    private val getTransactionInfo: GetTransactionsInfoUseCase
) : ViewModel() {

    private val _userInfoVisibility = MutableLiveData<Int>(GONE)
    val userInfoVisibility: LiveData<Int> get() = _userInfoVisibility

    private val _transactionInfoVisibility = MutableLiveData<Int>(GONE)
    val transactionInfoVisibility: LiveData<Int> get() = _transactionInfoVisibility

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> get() = _transaction

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _transactionInfo = MutableLiveData<TransactionInfo>()
    val transactionInfo: LiveData<TransactionInfo> get() = _transactionInfo

    fun getUserInfo(id: Int) {
        viewModelScope.launch {
            withContext(IO) {
                val user = getUserInfo.getUserInfo(id)
                if (user is SuccessState)
                    _user.postValue(user.data)
            }
        }
    }

    fun getTransactionInfo(id: Int) {

        viewModelScope.launch {
            withContext(IO) {
                val transactionInfo = getTransactionInfo.getTransactionInfo(id)
                if (transactionInfo is SuccessState) _transactionInfo.postValue(transactionInfo.data)
            }
        }
    }
}