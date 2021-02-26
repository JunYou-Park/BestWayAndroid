package com.yawtseb.bestway.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yawtseb.bestway.R
import com.yawtseb.bestway.databinding.LayoutAccountMenuChidItemBinding
import com.yawtseb.bestway.databinding.LayoutAccountMenuItemBinding
import com.yawtseb.bestway.util.Resolution.Companion.getDimension
import com.yawtseb.bestway.util.Resolution.Companion.toPx

class AccountMenuAdapter(val onClick: (item: String) -> Unit): RecyclerView.Adapter<AccountMenuAdapter.ViewHolder>() {

    private final val HEADER = 0
    private final val CHILD = 1

    private val newItems = arrayListOf<AccountMenuItem>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun onHeaderBind(item: AccountMenuItem, position: Int){
            val binding: LayoutAccountMenuItemBinding by lazy { LayoutAccountMenuItemBinding.bind(itemView) }
            binding.tvAccountMenuHeaderName.text = item.text

            binding.constAccountMenuHeaderParent.setOnClickListener {
                onClick(item.text)
                if(item.inVisibleChildren==null || item.inVisibleChildren.isNullOrEmpty()){
                    item.inVisibleChildren = arrayListOf()
                    var count = 0
                    while (position + 1 < newItems.size && newItems[position+1].type == 1){
                        item.inVisibleChildren!!.add(newItems.removeAt(position + 1))
                        count++
                    }
                    notifyItemRangeRemoved(position + 1, count)
                    binding.constAccountMenuHeaderParent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                }
                else{
                    var index = position + 1
                    for(obj in item.inVisibleChildren!!){
                        newItems.add(index++, obj)
                    }
                    notifyItemRangeInserted(position + 1, index - position - 1)
                    item.inVisibleChildren = null
                    binding.constAccountMenuHeaderParent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.more_light_gray))
                }
            }
        }

        fun onChildBind(item: AccountMenuItem){
            val binding: LayoutAccountMenuChidItemBinding by lazy { LayoutAccountMenuChidItemBinding.bind(itemView) }
            binding.btnAccountMenuChildName.text = item.text
            binding.btnAccountMenuChildName.setOnClickListener { onClick(item.text) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            HEADER ->{
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_account_menu_item, parent, false))
            }
            CHILD ->{
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_account_menu_chid_item, parent, false))
            }
        }
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_account_menu_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(newItems[position].type){
            HEADER ->{ holder.onHeaderBind(newItems[position], position) }
            CHILD->{ holder.onChildBind(newItems[position]) }
        }
    }

    override fun getItemCount() = newItems.size

    override fun getItemViewType(position: Int): Int {
        return newItems[position].type
    }

    fun update(items: List<AccountMenuItem>){
        newItems.addAll(items)
        notifyDataSetChanged()
    }

    data class AccountMenuItem(
            val type: Int,
            val text: String,
            var inVisibleChildren: ArrayList<AccountMenuItem>? = null
    )
}