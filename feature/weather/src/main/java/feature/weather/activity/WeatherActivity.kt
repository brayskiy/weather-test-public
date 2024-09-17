package feature.weather.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import feature.weather.R
import feature.weather.dagger.DaggerWeatherComponent
import feature.weather.dagger.WeatherFlowInjector
import feature.weather.dagger.WeatherComponent
import feature.weather.dagger.WeatherFlowComponent
import feature.weather.flow.WeatherFlowView
import feature.weather.fragment.CurrentWeatherFragment
import library.core.base.BaseActivity
import library.core.common.DialogData

class WeatherActivity : BaseActivity<ViewModel, WeatherComponent>(), WeatherFlowInjector {
    override val layoutId: Int = R.layout.activity_weather

    private val activityComponent: WeatherComponent by lazy { DaggerWeatherComponent.factory().create(this, appComponent) }
    private val flowComponent: WeatherFlowComponent by lazy { activityComponent.flowComponent() }
    private val flowView: WeatherFlowView by lazy { flowComponent.flowView() }

    override fun setupActivity(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        flowView.onCreate(activityComponent)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onResume() {
        super.onResume()
        flowView.showCurrentWeather()
    }

    override fun onBackPressed() {
        val visibleFragment = getVisibleFragment()
        if (visibleFragment is CurrentWeatherFragment) {
            showExitDialog()
        } else {
            super.onBackPressed()
        }
    }

    override fun finishActivity(resultCode: Int, resultData: Bundle) {
    }

    private fun showExitDialog() {
        val dialogData = DialogData(DialogData.Type.ALERT)
        dialogData.titleResId = R.string.exit_title
        dialogData.messageResId = R.string.exit_message
        dialogData.positiveButtonResId = R.string.ok
        dialogData.negativeButtonResId = R.string.cancel
        dialogData.positiveCallback = { finishAndRemoveTask() }
        displayDialog(dialogData)
    }

    override fun inject(fragment: CurrentWeatherFragment) {
        flowComponent.inject(fragment)
    }
}
