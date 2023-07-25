package pe.ralvaro.encryptionloginsample.data.preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ProtoDataStorage {

    suspend fun cleanCredentialsUser()
    suspend fun saveCredentialsUser(email: String)
    val userCredentials: Flow<ProtoUserCredential>

}

class ProtoDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<ProtoStoreModel>
) : ProtoDataStorage {

    override val userCredentials: Flow<ProtoUserCredential> = dataStore.data.map {
        it.userCredentials
    }

    override suspend fun cleanCredentialsUser() {
        dataStore.updateData { it.copy(userCredentials = ProtoUserCredential()) }
    }

    override suspend fun saveCredentialsUser(email: String) {
        dataStore.updateData {
            it.copy(userCredentials = it.userCredentials.copy(email = email))
        }
    }

}
