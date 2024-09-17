package feature.weather.mvvm.repository

import android.app.Activity
import android.app.Application
import feature.weather.mvvm.datasource.CurrentWeatherFragmentDataSource
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import library.core.network.models.Coord
import library.core.testutil.SpyOnInjectMockKsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SpyOnInjectMockKsExtension::class)
class CurrentWeatherFragmentRepositoryTest {

    @MockK(relaxed = true)
    lateinit var application: Application

    @MockK
    lateinit var dataSource: CurrentWeatherFragmentDataSource

    @InjectMockKs(overrideValues = true)
    lateinit var testObject: CurrentWeatherFragmentRepository

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `getWeather is calling dataSource getWeather`() {
        val lat = 40.0
        val lon = -70.0
        val coord = Coord(lat, lon)

        CoroutineScope(Dispatchers.IO).launch {
            testObject.getWeather(coord)
        }

        coVerify { dataSource.getWeather(any()) }
    }

    @Test
    fun `startLocationManager calls data source`() {
        val activity: Activity = mockk()

        testObject.startLocationManager(activity)

        verify { dataSource.startLocationManager(activity) }
    }
}
