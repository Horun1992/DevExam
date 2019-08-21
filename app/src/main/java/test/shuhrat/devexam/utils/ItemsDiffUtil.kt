package test.shuhrat.devexam.utils

import androidx.recyclerview.widget.DiffUtil
import test.shuhrat.devexam.data.pojos.ItemModel

class ItemsDiffUtil(var oldList: List<ItemModel>, var newList : List<ItemModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date == newList[newItemPosition].date &&
                oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}