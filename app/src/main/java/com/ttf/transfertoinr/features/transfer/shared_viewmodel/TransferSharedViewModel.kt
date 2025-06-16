package com.ttf.transfertoinr.features.transfer.shared_viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttf.transfertoinr.core.model.Recipient
import com.ttf.transfertoinr.core.model.Result
import com.ttf.transfertoinr.core.utils.generateTransactionCode
import com.ttf.transfertoinr.data.account.AccountRepository
import com.ttf.transfertoinr.data.exchange_rate.ExchangeRateRepository
import com.ttf.transfertoinr.data.transfer.TransferRepository
import com.ttf.transfertoinr.features.home.UserInfoStateHome
import com.ttf.transfertoinr.features.transfer.model.TransactionSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransferSharedViewModel(
    private val transferRepository: TransferRepository,
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
):ViewModel() {

    private val _transactionSummary = MutableStateFlow(TransactionSummary())
    val transactionSummary: StateFlow<TransactionSummary> = _transactionSummary

    private val _transactionCode = mutableStateOf("")
    val transactionCode:State<String> = _transactionCode

    private val _uploadScreenshotResult = MutableStateFlow(Result())
    val uploadScreenshotResult:StateFlow<Result> = _uploadScreenshotResult

    private val _userInfoState = MutableStateFlow(UserInfoStateHome())
    val userInfoState: StateFlow<UserInfoStateHome> = _userInfoState

    init {
        getUserInfo()
        initTransactionSummary()
    }

    private fun initTransactionSummary(){
        setCurrency()
        setTransactionCode()
        setRateFee()
    }

    private fun setTransactionCode(){
        _transactionCode.value = generateTransactionCode()
    }

    fun createTransfer(){
        viewModelScope.launch {
            transferRepository.createTransfer(
                transactionCode = transactionCode.value,
                sent = transactionSummary.value.send,
                currency = transactionSummary.value.currency,
                reason = transactionSummary.value.reason,
                recipientId = transactionSummary.value.recipient.id,
                screenshotLink = transactionSummary.value.screenshotLink
            )
        }
    }

    fun setSendAmount(send:Int){
        _transactionSummary.value = _transactionSummary.value.copy(send = send)
    }

    fun setReceiveAmount(send:Int){
        _transactionSummary.value = _transactionSummary.value.copy(receive = send)
    }

    fun setReason(reason:String){
        _transactionSummary.value = _transactionSummary.value.copy(reason = reason)
    }

    fun setRecipient(recipient: Recipient){
        _transactionSummary.value = _transactionSummary.value.copy(recipient = recipient)
    }

    private fun setRateFee(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile ->
                exchangeRatesRepository.getExchangeRates(profile.preferred_currency).collectLatest{rate->
                    _transactionSummary.value = _transactionSummary.value.copy(exchangeRate = rate.tti)
                    _transactionSummary.value = _transactionSummary.value.copy(fee = rate.tti_fees)
                }
            }
        }
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            _userInfoState.value = UserInfoStateHome(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    _userInfoState.value = UserInfoStateHome(profile = profile)
                }
            }
            catch (e:Exception){
                _userInfoState.value = UserInfoStateHome(error = e.message.toString())
            }
        }
    }

    private fun setCurrency(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile ->
                _transactionSummary.value = _transactionSummary.value.copy(currency =profile.preferred_currency)
            }
        }
    }

    private fun setScreenshotLink(fileName: String){
        val imageUrl = "https://fqsnwalptczelvhiwohd.supabase.co/storage/v1/object/public/transaction-screenshots/${fileName}"
        _transactionSummary.value = _transactionSummary.value.copy(screenshotLink = imageUrl)

    }

    fun resetUploadScreenshotState(){
        _uploadScreenshotResult.value = Result()
    }

    fun uploadScreenshot(fileName:String, fileBytes:ByteArray){
        viewModelScope.launch {
            _uploadScreenshotResult.value = Result(isLoading = true)
            try {
                transferRepository.uploadScreenshot(fileName, fileBytes)

                _uploadScreenshotResult.value = Result(success = true)
                setScreenshotLink(fileName)

            }
            catch (e:Exception){
                _uploadScreenshotResult.value = Result(error = e.message.toString())
            }
        }
    }
}