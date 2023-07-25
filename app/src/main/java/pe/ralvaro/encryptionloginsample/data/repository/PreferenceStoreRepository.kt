package pe.ralvaro.encryptionloginsample.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.encryptionloginsample.data.preferences.ProtoDataStorage
import javax.inject.Inject

class PreferenceStoreRepository @Inject constructor(
    private val preferenceRepository: ProtoDataStorage
) {

    suspend fun cleanCredential() {
        preferenceRepository.cleanCredentialsUser()
    }

    suspend fun saveCredentialUser(email: String) {
        preferenceRepository.saveCredentialsUser(email)
    }

    fun obtainCredentialUser(): Flow<String> {
        return preferenceRepository.userCredentials.map {
            it.email
        }
    }

}