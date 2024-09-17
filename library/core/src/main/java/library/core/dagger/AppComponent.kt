package library.core.dagger

import android.app.Application
import android.content.res.Resources
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import library.core.CoreController
import library.core.dagger.qualifiers.PerApp
import library.core.network.api.WeatherApi
import javax.inject.Singleton

@PerApp
@Component(modules = [AppModule::class, ApiModule::class])
@Singleton
interface AppComponent {
    fun inject(application: CoreController)

    fun getApplication(): Application

    fun getResources(): Resources

    fun getWeatherApi(): WeatherApi

    fun getCoroutineScope(): CoroutineScope
}
