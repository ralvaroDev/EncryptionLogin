package pe.ralvaro.encryptionloginsample.domain.login

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.ralvaro.encryptionloginsample.data.repository.CredentialsDataRepository
import pe.ralvaro.encryptionloginsample.data.network.NetUserCredentials
import pe.ralvaro.encryptionloginsample.data.repository.PreferenceStoreRepository
import pe.ralvaro.encryptionloginsample.di.IoDispatcher
import pe.ralvaro.encryptionloginsample.domain.FlowUseCase
import pe.ralvaro.encryptionloginsample.util.Result
import pe.ralvaro.encryptionloginsample.util.Result.Loading
import pe.ralvaro.encryptionloginsample.util.succeeded
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val credentialsDataRepository: CredentialsDataRepository,
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Triple<String, String, Boolean>, NetUserCredentials>(dispatcher) {

    override fun execute(parameters: Triple<String, String, Boolean>): Flow<Result<NetUserCredentials>> {
        val (email, password, saveCredentials) = parameters
        return flow {
            emit(Loading)
            val responseServer = credentialsDataRepository.makeLoginInServer(email, password)
            responseServer.let {
                if (it.succeeded && saveCredentials) {
                    Timber.d("Saving credentials in preferences $email")
                    preferenceStoreRepository.saveCredentialUser(email)
                } else if (!saveCredentials) {
                    Timber.d("Cleaning credentials")
                    preferenceStoreRepository.cleanCredential()
                }
                Timber.d("Sending data to viewModel")
                emit(it)
            }
        }
    }

}