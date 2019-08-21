package test.shuhrat.devexam.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.shuhrat.devexam.data.pojos.ItemModel
import test.shuhrat.devexam.databinding.SingeItemBinding
import test.shuhrat.devexam.ui.detailactivity.DetailActivity
import test.shuhrat.devexam.utils.Constants
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class ItemsListAdapter : RecyclerView.Adapter<ItemsListAdapter.MyViewHolder>() {

    var posts: List<ItemModel> = emptyList()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding = SingeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.adapter = this
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (posts.isNullOrEmpty()) 0 else posts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ItemModel = posts[position]
        holder.binding.item = item
    }

    fun sendData(posts: List<ItemModel>) {
        this.posts = posts
     }

    fun getData() : List<ItemModel> = posts

    fun cardClicked(item: ItemModel){
        context.startActivity(Intent(context, DetailActivity::class.java).putExtra("item", item))
    }

    companion object {
        //custom method for setting image into ImageView
        @BindingAdapter("setImageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String) {
            Glide.with(imageView.context).load(Constants.BASE_URL+url).into(imageView)
        }

        @BindingAdapter("setDate")
        @JvmStatic
        fun setDate(imageView: TextView, fullDate: String) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.FRANCE)
            val date = dateFormat.parse(fullDate)//You will get date object relative to server/client timezone wherever it is parsed
            val formatter =  SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.FRANCE) //If you need time just put specific format for time like 'HH:mm:ss'
            val dateStr = formatter.format(date as Date)
            imageView.text = dateStr
        }

    }

    class MyViewHolder(val binding: SingeItemBinding) : RecyclerView.ViewHolder(binding.root)


}