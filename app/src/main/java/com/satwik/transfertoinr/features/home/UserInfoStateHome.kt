package com.satwik.transfertoinr.features.home

import com.satwik.transfertoinr.core.model.Profile

data class UserInfoStateHome(
    val profile: Profile = Profile(),
    val isLoading: Boolean = false,
    val error: String = ""
)
