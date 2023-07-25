package pe.ralvaro.encryptionloginsample.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

interface CryptoEngineProvider {
    /**
     * Cipher instance to decrypt
     * @param iv the buffer with the Initialization Vector, this is a unique, random value that is
     *  used before data is encrypted to increase the security of the encryption process and prevent
     *  repeating patterns in the ciphertext.
     */
    fun getCipherInstance(iv: ByteArray): Cipher
    /**
     * Cipher instance to encrypt, here using PKCS7 padding we don't care to pass an iv because this
     * is automatically created
     */
    fun getCipherInstance(): Cipher
}


@Singleton
class CryptoEngineProviderImpl @Inject constructor(
    private val keyStore: KeyStore
) : CryptoEngineProvider {

    override fun getCipherInstance(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(
                Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv)
            )
        }
    }

    override fun getCipherInstance(): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }
    }

    private fun getKey(alias: String = "secret"): SecretKey {
        val existingKey = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey(alias)
    }

    private fun createKey(alias: String): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

}