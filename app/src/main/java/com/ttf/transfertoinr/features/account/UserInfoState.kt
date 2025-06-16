package com.ttf.transfertoinr.features.account

import com.ttf.transfertoinr.core.model.Profile

data class UserInfoState(
    val profile: Profile = Profile(),
    val isLoading: Boolean = false,
    val error: String = ""
)
