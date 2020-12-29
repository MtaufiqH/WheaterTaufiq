package id.taufiq.wheatertaufiq.vm

import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.taufiq.wheatertaufiq.BuildConfig
import id.taufiq.wheatertaufiq.api.myWeather
import id.taufiq.wheatertaufiq.model.WeatherApi
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created By Taufiq on 12/28/2020.
 *
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getWeatherApi("Makassar", BuildConfig.ApiKey)
    }

    private val _weatherProperty = MutableLiveData<WeatherApi>()
    val weatherProperty: LiveData<WeatherApi>
        get() = _weatherProperty


    private val _weatherLocation = MutableLiveData<String>()
    val weatherLocation: LiveData<String>
        get() = _weatherLocation


    fun getWeatherApi(cityName: String, apiKey: String) {
        viewModelScope.launch {
            val response = myWeather.weatherService.getCurrentWeather(cityName, apiKey)
            if (response.isSuccessful) {
                _weatherProperty.value = response.body()
            } else {
                Log.d("TAG", "getWeatherApi: ${response.message()}")
            }

        }
    }

    fun getLocation(longi: Double, lati: Double) {
        val geoCoder = Geocoder(getApplication(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lati, longi, 1)
        val city = address.get(0).locality
        _weatherLocation.value = city

    }

}