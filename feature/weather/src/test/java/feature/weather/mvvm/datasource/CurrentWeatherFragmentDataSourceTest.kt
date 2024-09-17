package feature.weather.mvvm.datasource

import android.content.Context
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import library.core.network.api.WeatherApi
import library.core.network.models.Coord
import library.core.network.models.WeatherData
import library.core.testutil.SpyOnInjectMockKsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(SpyOnInjectMockKsExtension::class)
class CurrentWeatherFragmentDataSourceTest {

    @MockK
    lateinit var api: WeatherApi

    @MockK
    lateinit var context: Context

    @InjectMockKs(overrideValues = true)
    lateinit var testObject: CurrentWeatherFragmentDataSource

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `getWeather is calling mobile service method`() {
        val lat = 40.0
        val lon = -70.0
        val coord = Coord(lat, lon)
        val appid = ""
        val weatherData: Response<WeatherData> = mockk()

        testObject.latitude = lat
        testObject.longitude = lon
        coEvery { api.getWeatherForecast(lat, lon, appid) } returns weatherData

        CoroutineScope(Dispatchers.IO).launch {
            testObject.getWeather(coord)
        }
        coVerify { api.getWeatherForecast(any(), any(), any()) }
    }
}
