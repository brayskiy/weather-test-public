package library.core.network.models

import com.google.gson.annotations.SerializedName

data class DailyWeather(
    val dt: Long,
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val pressure: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    val humidity: Int
)

/*
"dt": 1634572800,
"sunrise": 1634555430,
"sunset": 1634595080,
"moonrise": 1634593260,
"moonset": 1634547420,
"moon_phase": 0.44,
"temp": {
    "day": 58.01,
	"min": 49.87,
	"max": 59.05,
	"night": 49.87,
	"eve": 55.4,
	"morn": 52.07
},
"feels_like": {
	"day": 55.9,
	"night": 44.69,
	"eve": 52.99,
	"morn": 50.07
},
"pressure": 1010,
"humidity": 51,
"dew_point": 40.01,
"wind_speed": 19.64,
"wind_deg": 305,
"wind_gust": 28.97,
"weather": [{
"id": 803,
"main": "Clouds",
"description": "broken clouds",
"icon": "04d"
}],
"clouds": 70,
"pop": 0.04,
"uvi": 0
 */