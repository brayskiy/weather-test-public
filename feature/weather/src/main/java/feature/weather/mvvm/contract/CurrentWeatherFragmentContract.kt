package feature.weather.mvvm.contract

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import library.core.network.models.WeatherData
import library.core.common.ApiResponse
import library.core.network.models.Coord
import library.core.network.models.LocationData

interface CurrentWeatherFragmentContract {

    interface View

    interface ViewModel {
        val weather: MutableLiveData<ApiResponse<WeatherData>>
        val location: MutableLiveData<ApiResponse<LocationData>>

        fun getWeather(coord: Coord)
        fun getLocation(name: String)
        fun setWeatherDataObserver(owner: LifecycleOwner, left: (Throwable) -> Unit, right: (WeatherData) -> Unit)
        fun setCoordObserver(owner: LifecycleOwner, left: (Throwable) -> Unit, right: (LocationData) -> Unit)
        fun startLocationManager(activity: Activity)
    }
}
