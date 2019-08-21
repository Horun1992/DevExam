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
import test.shuhrat.devexam.data.pojos.ItemModel
import test.shuhrat.devexam.utils.Constants
import test.shuhrat.devexam.utils.Constants.AUTO_UPDATE_TIMER_SEC
import test.shuhrat.devexam.utils.NetworkUtils
import java.util.*

class ItemsListViewModel(application: Application) : AndroidViewModel(application) {

    private var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var postsLive: MutableLiveData<List<ItemModel>> = MutableLiveData()
    private var toastMsg: MutableLiveData<String> = MutableLiveData()
    var timer : MutableLiveData<String> = MutableLiveData()
    var count = AUTO_UPDATE_TIMER_SEC

    init {
        //TurnOnTimer for auto updated
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(count<=0){
                    count= AUTO_UPDATE_TIMER_SEC
                    updatedPosts()
                }
                timer.postValue((count--).toString())
            }
        }, 0, 1000)//put here time 1000 milliseconds=1 second
    }


    fun updatedPosts() {
        val app : App = getApplication()
        if(NetworkUtils.isNetworkConnected(app)){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    progressBar.postValue(true)
                    val response = app.getApplicationAppComponent().getRetrofit().getPosts()
                    if (response.isSuccessful) {
                        postsLive.postValue(response.body())
                        toastMsg.postValue(app.getString(R.string.updated))
                    }
                    progressBar.postValue(false)
                } catch (e: Exception) {
                    progressBar.postValue(false)
                    toastMsg.postValue(e.message)
                }
            }
        }else{
            toastMsg.postValue(app.getString(R.string.connection_error))
        }

    }

    fun getPosts(): LiveData<List<ItemModel>> = postsLive

    fun getMsg(): LiveData<String> = toastMsg

    fun progressBarStatus(): LiveData<Boolean> = progressBar
}