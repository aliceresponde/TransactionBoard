package com.aliceresponde.transactionboard.domain.useCase.userinfo

import com.aliceresponde.transactionboard.domain.BusinessState
import com.aliceresponde.transactionboard.domain.model.User

interface GetUserInfoUseCase {
    suspend fun getUserInfo(id: Int): BusinessState<User>
}
