package feature.weather.flow

import library.core.dagger.qualifiers.PerFlow
import library.core.network.models.WeatherData
import javax.inject.Inject

@PerFlow
class WeatherFlowModel @Inject constructor() {
    lateinit var weatherData: WeatherData
}
