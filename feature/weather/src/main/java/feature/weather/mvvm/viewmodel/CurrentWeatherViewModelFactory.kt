package feature.weather.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import feature.weather.mvvm.repository.CurrentWeatherFragmentRepository
import library.core.util.ImagesLoader
import javax.inject.Inject

class CurrentWeatherViewModelFactory @Inject constructor(
    private val repository: CurrentWeatherFragmentRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherFragmentViewModel::class.java)) {
            return CurrentWeatherFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
