package com.aliceresponde.transactionboard.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aliceresponde.transactionboard.data.local.AppDatabase
import com.aliceresponde.transactionboard.data.local.RoomDataSource
import com.aliceresponde.transactionboard.data.remote.LealApiService
import com.aliceresponde.transactionboard.data.remote.NetworkConnectionInterceptor
import com.aliceresponde.transactionboard.data.remote.RetrofitDataSource
import com.aliceresponde.transactionboard.data.repository.*
import com.aliceresponde.transactionboard.data.repository.source.LocalDataSource
import com.aliceresponde.transactionboard.data.repository.source.RemoteDataSource
import com.aliceresponde.transactionboard.domain.useCase.transaction.GetTransactionsUseCase
import com.aliceresponde.transactionboard.domain.useCase.transaction.GetTransactionsUseCaseImp
import com.aliceresponde.transactionboard.domain.useCase.transactionInfo.GetTransactionsInfoUseCase
import com.aliceresponde.transactionboard.domain.useCase.transactionInfo.GetTransactionsInfoUseCaseImp
import com.aliceresponde.transactionboard.domain.useCase.userinfo.GetUserInfoUseCase
import com.aliceresponde.transactionboard.domain.useCase.userinfo.GetUserInfoUseCaseImpl
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
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "database-name"
        ).build()
    }

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

    @Provides
    @Singleton
    fun provideUserInfoRepository(remoteDataSource: RemoteDataSource): UserInfoRepository =
        UserInfoRepositoryImp(remoteDataSource)

    @Provides
    @Singleton
    fun provideTransactionInfoRepository(remoteDataSource: RemoteDataSource): TransactionInfoRepository =
        TransactionInfoRepositoryImpl(remoteDataSource)

//    =============use cases ===================

    @Provides
    @Singleton
    fun provideGetTransactionUseCase(repository: TransactionRepository): GetTransactionsUseCase =
        GetTransactionsUseCaseImp(repository)

    @Provides
    @Singleton
    fun provideGetTransactionInfoUseCase(repository: TransactionInfoRepository): GetTransactionsInfoUseCase =
        GetTransactionsInfoUseCaseImp(repository)

    @Provides
    @Singleton
    fun providesGetUserInfoUseCase(repository : UserInfoRepository) : GetUserInfoUseCase = GetUserInfoUseCaseImpl(repository)
}