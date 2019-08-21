package test.shuhrat.devexam.ui.detailactivity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import test.shuhrat.devexam.data.pojos.ItemModel
import test.shuhrat.devexam.databinding.ActivityDetailBinding
import test.shuhrat.devexam.ui.baseviews.BaseActivity
import android.view.MenuItem
import test.shuhrat.devexam.R


class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val item : ItemModel = intent.getParcelableExtra("item")
        binding.item = item

        //set actionBar
        setSupportActionBar(binding.activityDetailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = item.title

    }


    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
