package feature.weather.mvvm.datasource

import android.app.Activity
import android.content.Context
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import library.core.base.BaseContract
import library.core.common.ApiResponse
import library.core.common.toLeft
import library.core.common.toRight
import library.core.network.api.WeatherApi
import library.core.network.models.Coord
import library.core.network.models.LocationData
import library.core.network.models.WeatherData
import javax.inject.Inject

class CurrentWeatherFragmentDataSource @Inject constructor(
    private val api: WeatherApi,
    private val context: Context
) : BaseContract.IDataSource, OnRequestPermissionsResultCallback {
    companion object {
        private const val NEW_YORK_LATITUDE = 40.725302
        private const val NEW_YORK_LONGITUDE = -73.997776

        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        // TODO: Move me out from here
        private const val appid = "9f10b3ac738fcd2c31ab686372b59622"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var latitude: Double = NEW_YORK_LATITUDE // Defaulted to NYC
    var longitude: Double = NEW_YORK_LONGITUDE // Defaulted to NYC

    fun startLocationManager(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        if (
            checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
            checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED
        ) {
                requestLocationPermission(activity)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    latitude = location?.latitude ?: NEW_YORK_LATITUDE
                    longitude = location?.longitude ?: NEW_YORK_LONGITUDE
                }
        }
    }

    private fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    if (checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                latitude = location?.latitude ?: NEW_YORK_LATITUDE
                                longitude = location?.longitude ?: NEW_YORK_LONGITUDE
                            }
                    }
                }
            }
        }
    }

    suspend fun getWeather(coord: Coord): ApiResponse<WeatherData> {
        val response = api.getWeatherForecast(
            coord.lat,
            coord.lon,
            appid
        )
        return if (response.isSuccessful) {
            response.body()?.let {
                toRight(it)
            } ?: toLeft(Throwable("Unknown Error"))

        } else {
            toLeft(Throwable(response.errorBody()?.string()))
        }
    }

    suspend fun getLocation(name: String): ApiResponse<LocationData> {
        val response = api.getLocation(name, appid)
        return if (response.isSuccessful) {
            response.body()?.let {
                toRight(it)
            } ?: toLeft(Throwable("Unknown Error"))

        } else {
            toLeft(Throwable(response.errorBody()?.string()))
        }
    }
}
