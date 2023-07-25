package pe.ralvaro.encryptionloginsample.data.network

import kotlinx.coroutines.delay
import pe.ralvaro.encryptionloginsample.util.Result

interface FakeApi {
    suspend fun putLogin(email: String, password: String): Result<NetUserCredentials>
}

/**
 * This class simulates implementation of retrofit with server connection
 */
object FakeServerSource : FakeApi {

    // Inserts a delay to simulate real connection
    override suspend fun putLogin(email: String, password: String): Result<NetUserCredentials> {
        val response = checkCredential(email, password)
        delay(3000)
        return when {
            response != null -> Result.Success(response)
            else -> Result.Error(Exception("Error trying to login, check credentials"))
        }
    }

    private fun checkCredential(email: String, password: String): NetUserCredentials? {
        return databaseCredentials.find {
            it.email == email && it.password == password
        }
    }

    private val databaseCredentials = listOf(
        NetUserCredentials("John Doe", "john1234", "john.doe@gmail.com"),
        NetUserCredentials("Jane Smith", "jane5678", "jane.smith@gmail.com"),
        NetUserCredentials("Mike Johnson", "mike4321", "mike.johnson@gmail.com"),
        NetUserCredentials("Emily Brown", "emily9876", "emily.brown@gmail.com"),
        NetUserCredentials("Alex Wilson", "alex5432", "alex.wilson@gmail.com"),
        NetUserCredentials("Sarah Taylor", "sarah2468", "sarah.taylor@gmail.com"),
        NetUserCredentials("Chris Parker", "chris1357", "chris.parker@gmail.com"),
        NetUserCredentials("Lisa Miller", "lisa7890", "lisa.miller@gmail.com"),
        NetUserCredentials("Robert White", "robert6543", "robert.white@gmail.com"),
        NetUserCredentials("Olivia Smith", "olivia3210", "olivia.smith@gmail.com")
    )


}

