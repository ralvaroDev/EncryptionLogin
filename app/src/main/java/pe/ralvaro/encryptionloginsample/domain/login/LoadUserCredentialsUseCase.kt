package pe.ralvaro.encryptionloginsample.domain.login

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.encryptionloginsample.data.repository.PreferenceStoreRepository
import pe.ralvaro.encryptionloginsample.di.IoDispatcher
import pe.ralvaro.encryptionloginsample.domain.FlowUseCase
import pe.ralvaro.encryptionloginsample.util.Result
import timber.log.Timber
import javax.inject.Inject

class LoadUserCredentialsUseCase @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<Unit, String>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<String>> {
        return preferenceStoreRepository.obtainCredentialUser().map {
            Timber.d("Mapping inside use case")
            Result.Success(it)
        }
    }
}