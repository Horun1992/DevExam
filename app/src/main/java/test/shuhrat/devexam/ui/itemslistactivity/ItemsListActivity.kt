package test.shuhrat.devexam.ui.itemslistactivity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_items_list.*
import test.shuhrat.devexam.R
import test.shuhrat.devexam.adapters.ItemsListAdapter
import test.shuhrat.devexam.data.pojos.ItemModel
import test.shuhrat.devexam.ui.baseviews.BaseActivity
import test.shuhrat.devexam.utils.Constants.AUTO_UPDATE_TIMER_SEC
import test.shuhrat.devexam.utils.ItemsDiffUtil
import test.shuhrat.devexam.viewmodels.ItemsListViewModel
import javax.inject.Inject
import java.util.*


class ItemsListActivity : BaseActivity() {


    @Inject lateinit var adapter : ItemsListAdapter

    @Inject lateinit var viewModel : ItemsListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)

        //set actionBar
        setSupportActionBar(items_list_activity_toolbar)
        items_list_activity_toolbar.title = "Dev Exam"


        //Inject this activity to Dagger
        getActivityComponent().inject(this)

        //observerTimer
        viewModel.timer.observe(this, Observer {
                count : String -> items_list_activity_toolbar.title = "Dev Exam $count"
        })

        //Set RecycleView
        items_list_activity_rv.layoutManager = LinearLayoutManager(this)
        items_list_activity_rv.adapter = adapter

        //Initial Posts
        viewModel.updatedPosts()

        //Observe progressBar if progressbar status is true show bar
        viewModel.progressBarStatus().observe(this, Observer {
                progressStatus : Boolean -> if (progressStatus) showLoading() else {hideLoading();
            items_list_activity_swipe_refresh.isRefreshing = false}
        })

        //Observe msg
        viewModel.getMsg().observe(this, Observer {
                msg : String -> showMessage(msg)
        })

        //listen the post and send it to adapter
        viewModel.getPosts().observe(this, Observer {
            posts : List<ItemModel> ->
            //Create dif util for checking is the content same or not
            val diffUtil = ItemsDiffUtil(adapter.getData(), posts)
            //get changes result of new data
            val itemDiffUtil : DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)
            //send data to adapter
            adapter.sendData(posts)
            //work only with new data
            itemDiffUtil.dispatchUpdatesTo(adapter)
        })


        //when we swipe down update recycleView
        items_list_activity_swipe_refresh.setOnRefreshListener { viewModel.updatedPosts()}

    }




}
