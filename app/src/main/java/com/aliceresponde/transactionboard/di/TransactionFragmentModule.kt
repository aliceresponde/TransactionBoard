package com.aliceresponde.transactionboard.di

import com.aliceresponde.transactionboard.domain.useCase.transaction.GetTransactionsUseCase
import com.aliceresponde.transactionboard.presentation.transactions.TransactionsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object TransactionFragmentModule {
    @Provides
    fun provideTransactionViewModel(useCase: GetTransactionsUseCase) =
        TransactionsViewModel(useCase)
}