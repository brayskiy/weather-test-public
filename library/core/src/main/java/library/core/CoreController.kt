package library.core

import android.app.Application
import library.core.dagger.ApiModule
import library.core.dagger.AppComponent
import library.core.dagger.AppModule
import library.core.dagger.DaggerAppComponent

open class CoreController: Application() {
    companion object {
        @JvmStatic
        lateinit var instance: CoreController
            private set
    }

    open lateinit var appComponent: AppComponent

    open fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule(this))
            .build()

        appComponent.inject(this)
    }

    override fun onCreate() {
        initAppComponent()
        super.onCreate()

        instance = this
    }
}
