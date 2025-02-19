package com.satwik.transfertoinr.features.home

import com.satwik.transfertoinr.core.model.UserInfo

data class UserInfoStateHome(
    val userInfo: UserInfo = UserInfo(),
    val isLoading: Boolean = false,
    val error: String = ""
)
