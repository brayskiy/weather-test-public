package feature.weather.fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import feature.weather.R
import feature.weather.dagger.WeatherComponent
import feature.weather.dagger.WeatherFlowInjector
import feature.weather.flow.WeatherFlowModel
import feature.weather.flow.WeatherFlowView
import feature.weather.mvvm.contract.CurrentWeatherFragmentContract
import feature.weather.mvvm.viewmodel.CurrentWeatherFragmentViewModel
import feature.weather.mvvm.viewmodel.CurrentWeatherViewModelFactory
import library.core.base.BaseFragment
import library.core.common.DialogData
import library.core.common.DialogData.Type.ERROR
import library.core.network.models.LocationData
import library.core.network.models.WeatherData
import library.core.util.degreeToCompass
import library.core.util.getWeatherIconUri
import library.core.util.toFahrenheit
import javax.inject.Inject


class CurrentWeatherFragment :
    BaseFragment<CurrentWeatherFragmentViewModel, WeatherComponent>(),
    CurrentWeatherFragmentContract.View {

    companion object {

        val TAG = CurrentWeatherFragment::class.java.canonicalName!!

        const val LAST_LOCATION = "last location"

        fun newInstance() = CurrentWeatherFragment()
    }

    @Inject
    lateinit var weatherFlowModel: WeatherFlowModel

    override val layoutId: Int = R.layout.fragment_current_weather

    lateinit var factory: CurrentWeatherViewModelFactory @Inject set

    protected lateinit var injector: WeatherComponent @Inject set

    lateinit var flowView: WeatherFlowView @Inject set

    // Lost binding, applied by id
    private val currentWeatherLocation by lazy { view?.findViewById<TextView>(R.id.currentWeatherLocation) }
    private val currentWeatherTemperature by lazy { view?.findViewById<TextView>(R.id.currentWeatherTemperature) }
    private val currentWeatherFeelsLike by lazy { view?.findViewById<TextView>(R.id.currentWeatherFeelsLike) }
    private val currentWeatherWind by lazy { view?.findViewById<TextView>(R.id.currentWeatherWind) }
    private val currentWeatherHumidity by lazy { view?.findViewById<TextView>(R.id.currentWeatherHumidity) }
    private val currentWeatherPressure by lazy { view?.findViewById<TextView>(R.id.currentWeatherPressure) }

    private val currentWeatherGet by lazy { view?.findViewById<TextView>(R.id.currentWeatherGet) }
    private val currentWeatherCity by lazy { view?.findViewById<TextView>(R.id.currentWeatherCity) }

    private val currentWeatherImage by lazy { view?.findViewById<ImageView>(R.id.currentWeatherImage) }
    private val currentWeatherDescription by lazy { view?.findViewById<TextView>(R.id.currentWeatherDescription) }

    override fun setupFragmentView(view: View) {
        (activity as WeatherFlowInjector).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun injectFragment() {
        requireActivity().setTitle(R.string.first_fragment_label)

        setLocationButton()
        setViewModel(factory, CurrentWeatherFragmentViewModel::class.java)

        viewModel.setCoordObserver(this, ::onCoordError, ::onCoordSuccess)
        viewModel.setWeatherDataObserver(this, ::onWeatherError, ::onWeatherSuccess)
        viewModel.startLocationManager(requireActivity())

        (PreferenceManager.getDefaultSharedPreferences(context).getString(LAST_LOCATION, null))?.let {
            currentWeatherCity?.text = it
            viewModel.getLocation(it)
        }
    }

    private fun onCoordError(error: Throwable) {
        hideProgress()
        DialogData(ERROR).let {
            it.titleResId = R.string.general_error_title
            it.message = error.message ?: "Unknown Error"
            displayDialog(it)
        }
    }

    private fun onCoordSuccess(coord: LocationData) {
        showProgress()
        viewModel.getWeather(coord.coord)
    }

    private fun onWeatherError(error: Throwable) {
        hideProgress()
        DialogData(ERROR).let {
            it.titleResId = R.string.general_error_title
            it.message = error.message ?: "Unknown Error"
            displayDialog(it)
        }
    }

    private fun onWeatherSuccess(weatherData: WeatherData) {
        hideProgress()
        weatherFlowModel.weatherData = weatherData

        weatherData.name.let {
            currentWeatherLocation?.text = getString(R.string.current_weather_location, it)
        }

        weatherData.main.let {
            currentWeatherTemperature?.text = getString(R.string.current_weather_temperature, it.temp.toFahrenheit())
            currentWeatherFeelsLike?.text = getString(R.string.current_weather_feels_like, it.feelsLike.toFahrenheit())
            currentWeatherHumidity?.text = getString(R.string.current_weather_humidity, it.humidity)
            currentWeatherPressure?.text = getString(R.string.current_weather_atmospheric_pressure, it.pressure)
        }

        weatherData.wind.let {
            currentWeatherWind?.text = getString(R.string.current_weather_wind_speed, it.speed, degreeToCompass(it.deg))
        }

        weatherData.weather[0].let { icon ->
            currentWeatherDescription?.text = icon.description
            currentWeatherImage?.let { image ->
                Glide.with(this).load(getWeatherIconUri(icon.icon)).into(image)
            }
        }
    }

    private fun setLocationButton() {
        currentWeatherGet?.setOnClickListener {
            currentWeatherCity?.text.let {
                viewModel.getLocation(it.toString())
                (PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(LAST_LOCATION, it.toString())).commit()
            }
        }
    }
}
