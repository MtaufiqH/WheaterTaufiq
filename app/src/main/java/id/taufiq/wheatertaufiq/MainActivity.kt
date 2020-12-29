package id.taufiq.wheatertaufiq

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import id.taufiq.wheatertaufiq.vm.MainActivityViewModel
import id.taufiq.wheatertaufiq.vm.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // view models
    private val factory: MainActivityViewModelFactory by lazy {
        MainActivityViewModelFactory(this.application)
    }
    private val myViewModel: MainActivityViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_search.setOnClickListener {
            val searchCity = et_search_city.text.toString()
            myViewModel.getWeatherApi(searchCity, BuildConfig.ApiKey)
        }


        myViewModel.weatherProperty.observe(this, Observer { weather ->
            tv_desc.text = weather.weather[0].description
            tv_status.text = weather.weather[0].main
            val temp = weather.main.temp
            tv_temp.text = "$temp °K"
            tv_loc.text = weather.name

            myViewModel.getLocation(weather.coord.lon, weather.coord.lat)

            Glide.with(this).run {
                load(image(weather.sys.country)).into(iv_country)
            }


        })



        myViewModel.weatherLocation.observe(this, Observer {
            tv_location.text = it
        })

    }


    override fun onResume() {
        super.onResume()
        myViewModel.getWeatherApi("Makassar", "cbef379ddf4a2dcb5f352ad82a730e8c")
    }
}