package pe.ralvaro.encryptionloginsample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.encryptionloginsample.data.network.FakeApi
import pe.ralvaro.encryptionloginsample.data.network.FakeServerSource
import pe.ralvaro.encryptionloginsample.data.preferences.ProtoDataStorage
import pe.ralvaro.encryptionloginsample.data.preferences.ProtoDataStorageImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindPreferenceDataStorage(
        preferenceDataStorageImpl: ProtoDataStorageImpl
    ): ProtoDataStorage

    @Binds
    abstract fun bindFakeApiImpl(
        fakeServerSource: FakeServerSource
    ): FakeApi

}