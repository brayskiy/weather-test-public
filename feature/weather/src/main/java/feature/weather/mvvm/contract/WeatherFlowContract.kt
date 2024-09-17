package feature.weather.mvvm.contract

import feature.weather.dagger.WeatherComponent

interface WeatherFlowContract {

    interface View {
        fun onCreate(component: WeatherComponent)
        fun showCurrentWeather()
    }
}
