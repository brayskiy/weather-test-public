package com.example.android_challenge.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.android_challenge.R
import com.example.android_challenge.dagger.DaggerMainComponent
import feature.weather.activity.WeatherActivity
import library.core.base.BaseActivity
import com.example.android_challenge.dagger.MainComponent

class MainActivity : BaseActivity<ViewModel, MainComponent>() {
    companion object {
        const val SPLASH_DELAY_MS = 3500L
    }

    override val layoutId = R.layout.activity_main

    private val activityComponent: MainComponent = DaggerMainComponent.factory().create(this, appComponent)

    override fun setupActivity(savedInstanceState: Bundle?) {
        activityComponent.inject(this)

        Handler(Looper.getMainLooper()).postDelayed({ startCurrentWeatherActivity() }, SPLASH_DELAY_MS)
    }

    private fun startCurrentWeatherActivity() {
        Intent(this, WeatherActivity::class.java).apply { startActivity(this) }
        finish()
    }

    override fun finishActivity(resultCode: Int, resultData: Bundle) {
    }
}
