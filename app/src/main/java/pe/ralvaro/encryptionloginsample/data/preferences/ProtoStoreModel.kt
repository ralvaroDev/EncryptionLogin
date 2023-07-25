package pe.ralvaro.encryptionloginsample.data.preferences

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import pe.ralvaro.encryptionloginsample.data.security.CryptoRepository
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class ProtoStoreModel(
    val userCredentials: ProtoUserCredential = ProtoUserCredential()
)

@Serializable
data class ProtoUserCredential(
    val email: String = ""
)

@Singleton
class ProtoStoreSerializer @Inject constructor(
    private val cryptoRepository: CryptoRepository
) : Serializer<ProtoStoreModel> {
    override val defaultValue: ProtoStoreModel
        get() = ProtoStoreModel()

    override suspend fun readFrom(input: InputStream): ProtoStoreModel {
        val decryptedBytes = cryptoRepository.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = ProtoStoreModel.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            Timber.e(e)
            defaultValue
        }
    }

    override suspend fun writeTo(t: ProtoStoreModel, output: OutputStream) {
        cryptoRepository.encrypt(
            byteArray = Json.encodeToString(
                serializer = ProtoStoreModel.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }

}