package feature.weather.mvvm.viewmodel

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feature.weather.mvvm.contract.CurrentWeatherFragmentContract
import feature.weather.mvvm.repository.CurrentWeatherFragmentRepository
import kotlinx.coroutines.*
import library.core.network.models.WeatherData
import library.core.common.ApiResponse
import library.core.network.models.Coord
import library.core.network.models.LocationData

class CurrentWeatherFragmentViewModel(
    private val repository: CurrentWeatherFragmentRepository,
) : ViewModel(), CurrentWeatherFragmentContract.ViewModel {

    private var job: Job? = null

    override val weather = MutableLiveData<ApiResponse<WeatherData>>()
    override val location = MutableLiveData<ApiResponse<LocationData>>()

    override fun getWeather(coord: Coord) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).async {
            weather.postValue(repository.getWeather(coord))
        }
    }

    override fun getLocation(name: String) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).async {
            location.postValue(repository.getLocation(name))
        }
    }

    override fun setWeatherDataObserver(owner: LifecycleOwner, left: (Throwable) -> Unit, right: (WeatherData) -> Unit) {
        weather.observe(owner) {
            it.fold(left, right)
        }
    }

    override fun setCoordObserver(owner: LifecycleOwner, left: (Throwable) -> Unit, right: (LocationData) -> Unit) {
        location.observe(owner) {
            it.fold(left, right)
        }
    }

    override fun startLocationManager(activity: Activity) = repository.startLocationManager(activity)
}
