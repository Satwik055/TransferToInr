package com.satwik.transfertoinr.features.account

import com.satwik.transfertoinr.core.model.UserInfo

data class UserInfoState(
    val userInfo: UserInfo = UserInfo(),
    val isLoading: Boolean = false,
    val error: String = ""
)
