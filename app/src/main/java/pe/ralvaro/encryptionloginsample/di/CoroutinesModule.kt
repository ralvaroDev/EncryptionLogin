package pe.ralvaro.encryptionloginsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideCoroutineScope(
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher)
    }

}
