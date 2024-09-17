package feature.weather.card

import library.core.network.models.DailyWeather

interface WeatherForecastCardHandler {
    fun onCardClicked(position: Int, dailyWeather: DailyWeather)
}
