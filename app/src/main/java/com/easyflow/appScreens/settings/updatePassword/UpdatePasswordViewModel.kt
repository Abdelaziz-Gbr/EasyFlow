package com.easyflow.appScreens.settings.updatePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.ResetPasswordModel
import com.easyflow.network.models.UpdatePasswordModel
import kotlinx.coroutines.launch

class UpdatePasswordViewModel: ViewModel() {
    private val _res = MutableLiveData<String?>()
    val res : LiveData<String?>
        get() = _res
    fun updatePassword(oldPassword: String, newPassword: String, newPasswordConfrim: String) {
        viewModelScope.launch {
            try{
                val updatePasswordRes = Network.easyFlowServices.updatePassword(
                    UpdatePasswordModel(
                        oldPassword = oldPassword,
                        resetPassword = ResetPasswordModel(
                            newPassword,
                            newPasswordConfrim
                        )
                    )
                )
                _res.value = updatePasswordRes.body()?.message
            }
            catch (e : Exception){
                _res.value = "sorry something went wrong please try."
            }

        }
    }
    fun onMsgRecieved(){
        _res.value = null
    }
}