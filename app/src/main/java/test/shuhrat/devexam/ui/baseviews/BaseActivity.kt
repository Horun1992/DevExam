package test.shuhrat.devexam.ui.baseviews

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import test.shuhrat.devexam.App
import test.shuhrat.devexam.R
import test.shuhrat.devexam.di.component.ActivityComponent
import test.shuhrat.devexam.di.component.AppComponent
import test.shuhrat.devexam.di.component.DaggerActivityComponent
import test.shuhrat.devexam.di.modul.ActivityModule
import test.shuhrat.devexam.utils.CommonUtils
import test.shuhrat.devexam.utils.NetworkUtils

abstract class BaseActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null
    private var backPressedTime: Long = 0
    private lateinit var activityComponent: ActivityComponent


    fun getAppComponent() = (application as App).getApplicationAppComponent()

    fun getActivityComponent() = activityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build()
    }

    protected fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    protected fun hideLoading() {
            if (progressDialog!=null && progressDialog!!.isShowing) {
                progressDialog!!.cancel()
            }
    }

    protected fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    protected fun showSnackBar(message: String) {
        val snackbar: Snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        val sbView: View = snackbar.view
        val textView: TextView = sbView
            .findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackbar.show()
    }

    protected fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.some_error))
        }
    }

    protected fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show()
        }
    }

    protected fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            showMessage(this.getString(R.string.type_twice_to_exit))
        }
        backPressedTime = System.currentTimeMillis()
    }
}