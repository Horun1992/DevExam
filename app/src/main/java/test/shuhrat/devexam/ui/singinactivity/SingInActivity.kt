package test.shuhrat.devexam.ui.singinactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_sing_in.*
import test.shuhrat.devexam.R
import test.shuhrat.devexam.data.pojos.PhoneMaskModel
import test.shuhrat.devexam.ui.baseviews.BaseActivity
import test.shuhrat.devexam.ui.itemslistactivity.ItemsListActivity
import test.shuhrat.devexam.viewmodels.SingInViewModel
import javax.inject.Inject

class SingInActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: SingInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        //Inject this activity to Dagger
        getActivityComponent().inject(this)

        //fill phone and password if the last in was success
        checkAndFillPhoneAndPasswordText()

        //get and Observe phoneMask
        viewModel.getPhoneMask()
        viewModel.phoneMask().observe(this, Observer { phoneMask: PhoneMaskModel ->
            sing_in_activity_et_phone.hint = phoneMask.phoneMask
        })

        //Observe progressBar if progressbar status is true show bar
        viewModel.progressBarStatus().observe(this, Observer { progressStatus: Boolean ->
            if (progressStatus) showLoading() else hideLoading()
        })

        //Observe msg
        viewModel.getMsg().observe(this, Observer { msg: String ->
            showMessage(msg)
        })

        //Observe if we get from server that this pair of login and password are true the we open the new Activity
        viewModel.getAuthStatus().observe(this, Observer { isSuccess: Boolean ->
            if (isSuccess) {
                saveLoginAndPassword("lastStatus", "true")
                saveLoginAndPassword("phoneNumber", sing_in_activity_et_phone.text.toString())
                saveLoginAndPassword("password", sing_in_activity_et_password.text.toString())
                startActivity(Intent(this@SingInActivity, ItemsListActivity::class.java))
                finish()
            } else {
                saveLoginAndPassword("lastStatus", "false")
            }
        })

    }

    fun singIn(view: View) {
        val lastStatus: String = load("lastStatus", "false") as String
        val phoneNumber: String = sing_in_activity_et_phone.text.toString()
        val password: String = sing_in_activity_et_password.text.toString()

        viewModel.checkAuth(phoneNumber, password, lastStatus, sing_in_activity_et_phone.hint.toString())

    }

    private fun checkAndFillPhoneAndPasswordText() {
        val lastStatus: String? = load("lastStatus", "false")
        val phoneNumber: String? = load("phoneNumber", "")
        val password: String? = load("password", "false")
        if (lastStatus == "true") {
            sing_in_activity_et_phone.setText(phoneNumber)
            sing_in_activity_et_password.setText(password)
        }
    }

    private fun saveLoginAndPassword(key: String, text: String) {
        val sPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putString(key, text)
        editor.apply()
    }

    //Load text from shared prefer...
    private fun load(name: String, DefString: String): String? {
        val sPref = getPreferences(Context.MODE_PRIVATE)
        return sPref.getString(name, DefString)
    }
}
