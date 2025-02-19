package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.UserInfo

interface AccountRepository {
    suspend fun getUserInfo():UserInfo
}