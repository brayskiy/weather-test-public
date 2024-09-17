package feature.weather.activity

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// TODO Tests are coming.

class WeatherActivityTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("feature.weather.activity", appContext.packageName)
    }

    @Before
    fun setupTest() {
    }

    @After
    fun clearTest() {

    }
}
