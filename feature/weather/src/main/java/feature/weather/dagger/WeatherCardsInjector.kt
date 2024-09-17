package feature.weather.dagger

import feature.weather.card.WeatherCard

interface WeatherCardsInjector {
    fun inject(card: WeatherCard)
}