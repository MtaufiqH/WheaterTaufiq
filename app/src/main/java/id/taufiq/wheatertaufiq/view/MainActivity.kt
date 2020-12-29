package id.taufiq.wheatertaufiq.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import id.taufiq.wheatertaufiq.BuildConfig
import id.taufiq.wheatertaufiq.R
import id.taufiq.wheatertaufiq.vm.MainActivityViewModel
import id.taufiq.wheatertaufiq.vm.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private const val KELVIN_CONVERSION = 273.15

class MainActivity : AppCompatActivity() {

    // view models
    private val factory: MainActivityViewModelFactory by lazy {
        MainActivityViewModelFactory(this.application)
    }
    private val myViewModel: MainActivityViewModel by viewModels { factory }

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_search.setOnClickListener {
            val searchCity = et_search_city.text.toString()
            myViewModel.getWeatherApi(
                searchCity,
                BuildConfig.ApiKey
            )
        }


        myViewModel.weatherProperty.observe(this, Observer { weather ->
            tv_desc.text = weather.weather[0].description.capitalize()
            tv_status.text = weather.weather[0].main
            tv_loc.text = weather.name

            val res = resources
            //FEELS LIKE
            val feel = weather.main.feelsLike - KELVIN_CONVERSION
            val feelText = String.format(res.getString(R.string.celcius),feel)
            tv_feel_like.text = feelText

            // wind speed
            val speedWind = weather.wind.speed
            val windSpeedText = String.format(res.getString(R.string.wind_speed),speedWind)
            tv_speed_wind.text = windSpeedText

            // temp in celcius
            val kelvin = weather.main.temp
            val celsius = kelvin - KELVIN_CONVERSION
            val celText = String.format(res.getString(R.string.celcius), celsius)
            tv_temp.text = celText

            // get location
            myViewModel.getLocation(weather.coord.lon, weather.coord.lat)


            // show location by flag
            Glide.with(this).run {
                load(image(weather.sys.country))
                    .placeholder(R.drawable.ic_baseline_broken_image_24)
                    .into(iv_country)
            }


        })


        myViewModel.weatherLocation.observe(this, Observer {
            tv_location.text = it
        })

    }

}