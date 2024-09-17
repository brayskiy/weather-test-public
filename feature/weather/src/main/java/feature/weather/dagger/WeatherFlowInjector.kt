package feature.weather.dagger

import feature.weather.fragment.CurrentWeatherFragment

interface WeatherFlowInjector {
    fun inject(fragment: CurrentWeatherFragment)
}
