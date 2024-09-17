package feature.weather.flow

import feature.weather.R
import feature.weather.dagger.WeatherComponent
import feature.weather.fragment.CurrentWeatherFragment
import feature.weather.mvvm.contract.WeatherFlowContract
import library.core.dagger.qualifiers.PerFlow
import library.core.util.FragmentTransactionManager
import javax.inject.Inject

@PerFlow
class WeatherFlowView @Inject constructor(
    private val fragmentTransactionManager: FragmentTransactionManager
): WeatherFlowContract.View {

    var activityComponent: WeatherComponent? = null

    override fun onCreate(component: WeatherComponent) {
        activityComponent = component
    }

    override fun showCurrentWeather() {
        fragmentTransactionManager.replaceFragment(
            R.id.fragment_container,
            CurrentWeatherFragment.newInstance(),
            CurrentWeatherFragment.TAG,
            true,
            CurrentWeatherFragment.TAG
        )
    }
}
