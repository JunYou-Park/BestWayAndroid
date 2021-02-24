package com.yawtseb.bestway.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yawtseb.bestway.R
import com.yawtseb.bestway.databinding.LayoutTodayDietItemBinding
import com.yawtseb.bestway.listener.ItemClickListener
import com.yawtseb.bestway.model.MenuVo
import com.yawtseb.bestway.util.ConstData.IMAGE_URL
import java.text.DecimalFormat

class HomeTodayDietAdapter: ListAdapter<MenuVo, HomeTodayDietAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var listener: ItemClickListener

    fun setListener(listener: ItemClickListener){
        this.listener = listener
    }

    inner class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_today_diet_item, parent, false)) {

        private val binding: LayoutTodayDietItemBinding = LayoutTodayDietItemBinding.bind(itemView)

        fun onBind(item: MenuVo, position: Int){
            Glide.with(itemView).load("$IMAGE_URL${item.menu_thumb}").into(binding.ivHomeTodayDietThumb)
            binding.tvHomeTodayDietName.text = item.menu_name

            val price = "${DecimalFormat("#,###").format(item.menu_price.toInt())} 원"
            binding.tvHomeTodayDietPrice.text = price

            binding.constHomeTodayDietParent.setOnClickListener {
                listener.onItemClick(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: HomeTodayDietAdapter.ViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }


    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuVo>(){

            override fun areItemsTheSame(oldItem: MenuVo, newItem: MenuVo) = oldItem.menu_id == newItem.menu_id

            override fun areContentsTheSame(oldItem: MenuVo, newItem: MenuVo) = oldItem == newItem

        }
    }
}