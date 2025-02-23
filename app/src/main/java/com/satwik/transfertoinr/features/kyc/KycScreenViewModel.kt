package com.satwik.transfertoinr.features.kyc

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.kyc.KycRepository
import com.satwik.transfertoinr.features.recipient.RecipientsState
import com.sumsub.sns.core.SNSMobileSDK
import com.sumsub.sns.core.data.listener.TokenExpirationHandler
import kotlinx.coroutines.launch
import java.util.Locale

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
            val id = accountRepository.getUserInfo().ttf_user_id

            try{
                kycRepository.updateKycStatus(status = status, id = id)
                println("KYC SUCCESS")
            }
            catch (e:Exception){
                println("KYC ERROR"+ e.message)
            }
        }
    }

    private fun getAccessToken(){
        viewModelScope.launch {
            val user = accountRepository.getUserInfo()
            val email = user.email
            val phone = user.phone
            val userId = user.ttf_user_id.toString()

            _accessTokenState.value = AccessTokenState(isLoading = true)
            try {
                val accessToken = kycRepository.getAccessToken(email, phone, userId)
                _accessTokenState.value = AccessTokenState(accessToken= accessToken)
            }
            catch (e:Exception){
                _accessTokenState.value = AccessTokenState(error = e.message.toString())
            }
        }
    }
}