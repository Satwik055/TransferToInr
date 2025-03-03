package com.satwik.transfertoinr.features.kyc

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.kyc.KycRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KycScreenViewModel(
    private val kycRepository: KycRepository,
    private val accountRepository: AccountRepository
):ViewModel() {


    private val _accessTokenState = mutableStateOf(AccessTokenState())
    val accessTokenState: State<AccessTokenState> = _accessTokenState

    init {
        getAccessToken()
    }

    fun updateKycStatus(status:Boolean){
        viewModelScope.launch {
            try{
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    kycRepository.updateKycStatus(status = status, id = profile.ttf_user_id)
                }
            }
            catch (e:Exception){
                println("KYC ERROR"+ e.message)
            }
        }
    }

    private fun getAccessToken(){
        viewModelScope.launch {
            _accessTokenState.value = AccessTokenState(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    val accessToken = kycRepository.getAccessToken(profile.email, profile.phone, profile.name)
                    _accessTokenState.value = AccessTokenState(accessToken= accessToken)
                }
            }
            catch (e:Exception){
                _accessTokenState.value = AccessTokenState(error = e.message.toString())
            }
        }
    }
}