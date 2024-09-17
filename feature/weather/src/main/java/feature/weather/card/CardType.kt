package feature.weather.card

import feature.weather.card.base.BaseCard

enum class CardType constructor(val cardClass: Class<out BaseCard<*, *, *>>) {
    INVALID(BaseCard::class.java),
    DAILY_WEATHER(WeatherCard::class.java);

    companion object {

        fun getType(ind: Int): CardType {
            return if (ind < 1 || ind >= values().size) INVALID else values()[ind]
        }
    }
}
