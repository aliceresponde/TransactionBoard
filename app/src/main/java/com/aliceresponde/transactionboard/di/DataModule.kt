package com.aliceresponde.transactionboard.di

import android.content.Context
import androidx.room.Room
import com.aliceresponde.transactionboard.data.local.AppDatabase
import com.aliceresponde.transactionboard.data.local.RoomDataSource
import com.aliceresponde.transactionboard.data.remote.LealApiService
import com.aliceresponde.transactionboard.data.remote.NetworkConnectionInterceptor
import com.aliceresponde.transactionboard.data.remote.RetrofitDataSource
import com.aliceresponde.transactionboard.data.repository.TransactionRepository
import com.aliceresponde.transactionboard.data.repository.TransactionRepositoryImp
import com.aliceresponde.transactionboard.data.repository.source.LocalDataSource
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource
import com.aliceresponde.transactionboard.domain.useCase.GetTransactionsUseCase
import com.aliceresponde.transactionboard.domain.useCase.GetTransactionsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-name"
        ).build()
    }


    @Provides
    @Singleton
    fun provideGetTransactionUseCase(repository: TransactionRepository): GetTransactionsUseCase =
        GetTransactionsUseCaseImp(repository)

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): Interceptor =
        NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideApiService(interceptor: Interceptor) = LealApiService.invoke(interceptor)

    // dataSource
    @Provides
    @Singleton
    fun provideRemoteDataSource(service: LealApiService): RemoteDataSource =
        RetrofitDataSource(service)

    @Provides
    @Singleton
    fun provideLocalDataSource(db: AppDatabase): LocalDataSource = RoomDataSource(db)

    // repository
    @Provides
    @Singleton
    fun provideTransactionRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): TransactionRepository = TransactionRepositoryImp(remoteDataSource, localDataSource)
}