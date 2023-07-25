package pe.ralvaro.encryptionloginsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(MyTree())
    }

}

class MyTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "$tag FATAL", message, t)
    }
}