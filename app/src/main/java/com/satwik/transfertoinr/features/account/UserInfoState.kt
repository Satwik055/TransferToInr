package com.satwik.transfertoinr.features.account

import com.satwik.transfertoinr.core.model.Profile

data class UserInfoState(
    val profile: Profile = Profile(),
    val isLoading: Boolean = false,
    val error: String = ""
)
