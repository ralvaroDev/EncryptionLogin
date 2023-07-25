package pe.ralvaro.encryptionloginsample.data.security

import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoEngineProvider: CryptoEngineProvider
) {
    fun encrypt(byteArray: ByteArray, outputStream: OutputStream): ByteArray {
        val encryptCipher = cryptoEngineProvider.getCipherInstance()
        val encryptedBytes: ByteArray = encryptCipher.doFinal(byteArray)
        outputStream.use {
            it.write(encryptCipher.iv.size)
            it.write(encryptCipher.iv)
            it.write(encryptedBytes.size)
            it.write(encryptedBytes)
        }
        return encryptedBytes
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val ivSize = it.read()
            val iv = ByteArray(ivSize)
            it.read(iv)

            val encryptedBytesSize = it.read()
            val encryptedBytes = ByteArray(encryptedBytesSize)
            it.read(encryptedBytes)
            cryptoEngineProvider.getCipherInstance(iv).doFinal(encryptedBytes)
        }
    }

}