package pe.ralvaro.encryptionloginsample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pe.ralvaro.encryptionloginsample.data.network.NetUserCredentials
import pe.ralvaro.encryptionloginsample.domain.login.LoadUserCredentialsUseCase
import pe.ralvaro.encryptionloginsample.domain.login.LoginUseCase
import pe.ralvaro.encryptionloginsample.util.Result
import pe.ralvaro.encryptionloginsample.util.data
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loadUserCredentialsUseCase: LoadUserCredentialsUseCase
) : ViewModel() {

    var isSaveActiveSaveCredentials = false

    private val _channelCredential = Channel<String>()
    val channelCredential = _channelCredential.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadUserCredentialsUseCase(Unit).collect {
                //Timber.d("Getting credentials in loginViewModel -> ${it.data}")
                if (!it.data.isNullOrEmpty()) {
                    _channelCredential.send(it.data!!)
                } else {
                    // We cancel the collection to avoid future emissions
                    // from DataStore
                    cancel()
                }
            }
        }
    }

    fun sendCredentialsToLogin(email: String, password: String): Flow<Result<NetUserCredentials>> {
        return loginUseCase(
            Triple(email, password, isSaveActiveSaveCredentials)
        )
    }

}