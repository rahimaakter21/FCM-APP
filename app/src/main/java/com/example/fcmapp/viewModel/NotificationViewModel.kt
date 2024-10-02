package com.example.fcmapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcmapp.repository.FCMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private  val fcmRepository: FCMRepository ): ViewModel() {
    private  var _notificationSent = MutableLiveData<Boolean>()
    val   notificationSent:LiveData<Boolean> get() = _notificationSent

    private  val _errorMessage = MutableLiveData<String>()
    val errorMessage:LiveData<String> get() =_errorMessage

    fun sendNotification(title:String,body:String){
        viewModelScope.launch (Dispatchers.IO) {


            try {
                fcmRepository.sendNotification( title, body, {

                    _notificationSent.postValue(true)
                }, {
                    _errorMessage.postValue(it.message ?: "unknown error")
                })
                 }catch(e:Exception) {
                     _errorMessage.postValue(e.message ?: "unexpected  error")

                 }
        }

    }
}