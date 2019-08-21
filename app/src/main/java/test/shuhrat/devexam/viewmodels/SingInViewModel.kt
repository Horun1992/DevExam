package test.shuhrat.devexam.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.shuhrat.devexam.App
import test.shuhrat.devexam.R
import test.shuhrat.devexam.data.pojos.PhoneMaskModel
import test.shuhrat.devexam.utils.NetworkUtils
import java.util.regex.Pattern


class SingInViewModel(application: Application) : AndroidViewModel(application) {

    private var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var authStatus: MutableLiveData<Boolean> = MutableLiveData()
    private var toastMsg: MutableLiveData<String> = MutableLiveData()
    private var phoneMask: MutableLiveData<PhoneMaskModel> = MutableLiveData()

    fun checkAuth(phone: String, password: String, lastStatus: String, phoneMask: String) {
        when {
            lastStatus == "true" -> getData(phone, password)
            getIntegerAsText(phoneMask) == phone.substring(0, getIntegerAsText(phoneMask).length) -> getData(phone, password)
            else -> toastMsg.postValue(getApplication<App>().getString(R.string.wrong_phone_mask) + phoneMask)
        }
    }

    private fun getData(phone: String, password: String) {
        val app: App = getApplication()
        if (NetworkUtils.isNetworkConnected(app)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    progressBar.postValue(true)
                    val response =
                        app.getApplicationAppComponent().getRetrofit().checkAuth(phone, password)
                    if (response.isSuccessful) {
                        authStatus.postValue(response.body()!!.success)
                        toastMsg.postValue(app.getString(R.string.ready))
                    } else {
                        toastMsg.postValue(app.getString(R.string.check_data))
                    }
                    progressBar.postValue(false)
                } catch (e: Exception) {
                    progressBar.postValue(false)
                    toastMsg.postValue(e.message)
                }
            }
        } else {
            toastMsg.postValue(app.getString(R.string.connection_error))
        }
    }

    private fun getIntegerAsText(text: String): String {
        val p = Pattern.compile("\\d")
        val m = p.matcher(text)
        val number: StringBuilder = java.lang.StringBuilder()
        while (m.find()) {
            number.append(m.group())
        }
        return number.toString()
    }

    fun getPhoneMask() {
        val app: App = getApplication()
        if (NetworkUtils.isNetworkConnected(app)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response =
                        app.getApplicationAppComponent().getRetrofit().getPhoneMask()
                    if (response.isSuccessful) {
                        phoneMask.postValue(response.body())
                    }
                } catch (e: Exception) {
                    toastMsg.postValue(e.message)
                }
            }
        }
    }

    fun getAuthStatus(): LiveData<Boolean> = authStatus

    fun phoneMask(): LiveData<PhoneMaskModel> = phoneMask

    fun getMsg(): LiveData<String> = toastMsg

    fun progressBarStatus(): LiveData<Boolean> = progressBar
}