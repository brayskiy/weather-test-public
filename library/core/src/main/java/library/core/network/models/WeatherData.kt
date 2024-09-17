package library.core.network.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val timezone: String,
    val name: String,
)

data class LocationData(
    val coord: Coord,
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
)

data class Wind(
    val speed: Double,
    val deg: Int
)

//data class Weather(
//    val id: Int //{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"base":"stations","main":{"temp":296.7,"feels_like":296.36,"temp_min":295.27,"temp_max":297.77,"pressure":1024,"humidity":48,"sea_level":1024,"grnd_level":1023},"visibility":10000,"wind":{"speed":4.12,"deg":60},"clouds":{"all":40},"dt":1726520552,"sys":{"type":2,"id":2009754,"country":"US","sunrise":1726483088,"sunset":1726527806},"timezone":-14400,"id":5128581,"name":"New York","cod":200}
//)

/*
"coord":{"lon":-74,"lat":40.7332},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"base":"stations","main":{"temp":296.7,"feels_like":296.36,"temp_min":295.27,"temp_max":297.77,"pressure":1024,"humidity":48,"sea_level":1024,"grnd_level":1023},"visibility":10000,"wind":{"speed":4.12,"deg":60},"clouds":{"all":40},"dt":1726520552,"sys":{"type":2,"id":2009754,"country":"US","sunrise":1726483088,"sunset":1726527806},"timezone":-14400,"id":5128581,"name":"New York","cod":200}
2024-09-16 17:04:32.298  7723-7762  OkHttp                  com.example.android_challenge        D
*/

/*
{
    "lat": 40.12,
    "lon": -96.66,
    "timezone": "America/Chicago",
    "timezone_offset": -18000,
    "current": {
    "dt": 1595243443,
    "sunrise": 1595243663,
    "sunset": 1595296278,
    "temp": 293.28,
    "feels_like": 293.82,
    "pressure": 1016,
    "humidity": 100,
    "dew_point": 293.28,
    "uvi": 10.64,
    "clouds": 90,
    "visibility": 10000,
    "wind_speed": 4.6,
    "wind_deg": 310,
    "weather": [
    {
        "id": 501,
        "main": "Rain",
        "description": "moderate rain",
        "icon": "10n"
    },
    {
        "id": 201,
        "main": "Thunderstorm",
        "description": "thunderstorm with rain",
        "icon": "11n"
    }
    ],
    "rain": {
        "1h": 2.93
    }
},
    }*/