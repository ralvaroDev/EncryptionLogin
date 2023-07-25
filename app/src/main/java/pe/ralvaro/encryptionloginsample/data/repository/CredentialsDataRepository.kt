package pe.ralvaro.encryptionloginsample.data.repository

import pe.ralvaro.encryptionloginsample.data.network.FakeApi
import pe.ralvaro.encryptionloginsample.data.network.NetUserCredentials
import pe.ralvaro.encryptionloginsample.util.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to the fake server point
 */
@Singleton
class CredentialsDataRepository @Inject constructor(
    private val serverSource: FakeApi
) {

    suspend fun makeLoginInServer(email: String, password: String): Result<NetUserCredentials> {
        return serverSource.putLogin(email, password)
    }

}