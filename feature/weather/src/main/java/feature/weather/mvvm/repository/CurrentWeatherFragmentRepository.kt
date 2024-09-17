package feature.weather.mvvm.repository

import android.app.Activity
import feature.weather.mvvm.datasource.CurrentWeatherFragmentDataSource
import library.core.base.BaseContract
import library.core.network.models.Coord
import javax.inject.Inject

class CurrentWeatherFragmentRepository @Inject constructor(
    private val dataSource: CurrentWeatherFragmentDataSource
) : BaseContract.IRepository {
    suspend fun getWeather(coord: Coord) = dataSource.getWeather(coord)
    suspend fun getLocation(name: String) = dataSource.getLocation(name)
    fun startLocationManager(activity: Activity) = dataSource.startLocationManager(activity)
}
