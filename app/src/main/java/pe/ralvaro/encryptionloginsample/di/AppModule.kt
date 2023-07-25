package pe.ralvaro.encryptionloginsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.encryptionloginsample.data.network.FakeServerSource

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFakeServer() = FakeServerSource

}
