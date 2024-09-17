package feature.weather.dagger

import dagger.Subcomponent
import feature.weather.flow.WeatherFlowView
import library.core.dagger.qualifiers.PerFlow

@PerFlow
@Subcomponent(modules = [WeatherFlowModule::class])
interface WeatherFlowComponent : WeatherFlowInjector {
    fun flowView(): WeatherFlowView
}
