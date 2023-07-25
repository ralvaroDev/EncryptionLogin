package pe.ralvaro.encryptionloginsample.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import pe.ralvaro.encryptionloginsample.data.preferences.ProtoStoreModel
import pe.ralvaro.encryptionloginsample.data.preferences.ProtoStoreSerializer
import pe.ralvaro.encryptionloginsample.data.security.CryptoEngineProvider
import pe.ralvaro.encryptionloginsample.data.security.CryptoEngineProviderImpl
import java.security.KeyStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SecurityModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope,
        protoStoreSerializer: ProtoStoreSerializer
    ): DataStore<ProtoStoreModel> = DataStoreFactory.create(
        serializer = protoStoreSerializer,
        corruptionHandler = null,
        scope = coroutineScope,
        produceFile = { context.dataStoreFile(PREFS_NAME) }
    )

    @Provides
    @Singleton
    fun provideKeyStore(): KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    @Provides
    fun provideCipherImpl(
        keyStore: KeyStore
    ): CryptoEngineProvider = CryptoEngineProviderImpl(keyStore)

}


private const val PREFS_NAME = "encrypted_store"


