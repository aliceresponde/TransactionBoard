package com.aliceresponde.transactionboard.presentation.detail

import android.view.View.GONE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.domain.model.User
import com.aliceresponde.transactionboard.domain.useCase.TransactionInfo

class TransactionDetailViewModel : ViewModel(){
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

    


}