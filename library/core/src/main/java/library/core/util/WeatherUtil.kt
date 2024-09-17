package library.core.util

import library.core.BuildConfig
import kotlin.math.roundToInt

fun degreeToCompass(degrees: Int): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")

    val split = (degrees.toDouble() * 8 / 360).roundToInt()

    return directions[(split + 8) % 8]
}

fun Double.toFahrenheit() = (this - 273.15) * (9.0 / 5.0) + 32.0

fun getWeatherIconUri(icon: String) = BuildConfig.ICON_URL + icon + "@2x.png"