package com.aliceresponde.transactionboard.di

import com.aliceresponde.transactionboard.domain.useCase.transactionInfo.GetTransactionsInfoUseCase
import com.aliceresponde.transactionboard.domain.useCase.userinfo.GetUserInfoUseCase
import com.aliceresponde.transactionboard.presentation.detail.TransactionDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object TransactionDetailFragmentModule {
    @Provides
    @Singleton
    fun provideTransactionDetailViewModel(
        getUserInfo: GetUserInfoUseCase,
        getTransactionInfo: GetTransactionsInfoUseCase
    ) = TransactionDetailViewModel(getUserInfo, getTransactionInfo)
}