package id.taufiq.wheatertaufiq.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * Created By Taufiq on 12/28/2020.
 *
 */
class MainActivityViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(application) as T


        throw IllegalArgumentException("uknown viewmodel factory")
    }


}