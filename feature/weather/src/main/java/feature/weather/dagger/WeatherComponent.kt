package feature.weather.dagger

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import feature.weather.activity.WeatherActivity
import library.core.dagger.ActivityComponent
import library.core.dagger.ActivityModule
import library.core.dagger.AppComponent
import library.core.dagger.qualifiers.PerActivity

@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class]
)
@PerActivity
interface WeatherComponent: ActivityComponent, WeatherCardsInjector {
    fun inject(activity: WeatherActivity)

    fun flowComponent(): WeatherFlowComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance activity: AppCompatActivity, appComponent: AppComponent): WeatherComponent
    }
}
