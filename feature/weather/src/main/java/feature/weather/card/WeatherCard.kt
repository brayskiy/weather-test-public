package feature.weather.card

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import feature.weather.R
import feature.weather.card.base.BaseCard
import feature.weather.card.CardType.DAILY_WEATHER
import feature.weather.dagger.WeatherComponent
import library.core.card.BaseViewHolder
import library.core.card.CardHandlerProvider
import library.core.network.models.DailyWeather
import library.core.util.ImagesLoader
import library.core.util.degreeToCompass
import library.core.util.epochToDate
import javax.inject.Inject

class WeatherCard(
    private val context: Context,
    dailyWeather: DailyWeather,
    injector: WeatherComponent,
    cardHandlerProvider: CardHandlerProvider<WeatherForecastCardHandler>
) : BaseCard<DailyWeather, WeatherForecastCardHandler, WeatherCard.ViewHolder>(R.layout.card_daily_weather, dailyWeather, cardHandlerProvider) {

    lateinit var application: Application @Inject set

    lateinit var imagesLoader: ImagesLoader @Inject set

    override val cardType: CardType
        get() = DAILY_WEATHER

    init {
        injector.inject(this)
    }

    override fun onCreateViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun updateCardViews() {
        viewHolder?.weatherCardContainer?.setOnClickListener { onCardClicked(it) }

        viewHolder?.dailyWeatherDate?.text = epochToDate(cardData.dt)
        viewHolder?.dailyWeatherTemperature?.text = context.getString(R.string.daily_weather_temperature, cardData.temp.day)
        viewHolder?.dailyWeatherFeelsLike?.text = context.getString(R.string.daily_weather_feels_like, cardData.feelsLike.day)
        viewHolder?.dailyWeatherWind?.text = context.getString(R.string.daily_weather_wind_speed, cardData.windSpeed, degreeToCompass(cardData.windDeg))
        viewHolder?.dailyWeatherHumidity?.text = context.getString(R.string.daily_weather_humidity, cardData.humidity)
        viewHolder?.dailyWeatherPressure?.text = context.getString(R.string.daily_weather_atmospheric_pressure, cardData.pressure)
    }

    private fun onCardClicked(view: View) {
        viewHolder?.let {
            cardHandler.onCardClicked(it.adapterPosition, cardData)
        }
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val weatherCardContainer: CardView = itemView.findViewById(R.id.weatherCardContainer)
        val dailyWeatherDate: TextView = itemView.findViewById(R.id.dailyWeatherDate)
        val dailyWeatherTemperature: TextView = itemView.findViewById(R.id.dailyWeatherTemperature)
        val dailyWeatherFeelsLike: TextView = itemView.findViewById(R.id.dailyWeatherFeelsLike)
        val dailyWeatherWind: TextView = itemView.findViewById(R.id.dailyWeatherWind)
        val dailyWeatherHumidity: TextView = itemView.findViewById(R.id.dailyWeatherHumidity)
        val dailyWeatherPressure: TextView = itemView.findViewById(R.id.dailyWeatherPressure)
    }
}
