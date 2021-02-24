package com.yawtseb.bestway.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yawtseb.bestway.R
import com.yawtseb.bestway.databinding.LayoutMealTicketItemBinding
import com.yawtseb.bestway.listener.ItemClickListener
import java.text.DecimalFormat

class MealTicketAdapter: RecyclerView.Adapter<MealTicketAdapter.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(listener: ItemClickListener){
        itemClickListener = listener
    }

    private val ticketPriceList = arrayListOf<String>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val binding: LayoutMealTicketItemBinding by lazy { LayoutMealTicketItemBinding.bind(itemView) }

        fun onBind(item: String){
            val priceText = "${DecimalFormat("#,###").format(item.toInt())} ${itemView.context.getString(R.string.k_won)}"
            binding.tvMealTicketPrice.text = priceText

            binding.ibMealTicketMinus.setOnClickListener(this)
            binding.ibMealTicketPlus.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var amount = binding.tvMealTicketAmount.text.toString().toInt()

            when(v?.id){
                // 더하기 버튼
                R.id.ib_meal_ticket_plus->{
                    if(amount++<9){
                        binding.tvMealTicketAmount.text = (amount).toString()
                        itemClickListener.onItemClick(amount, adapterPosition)
                    }
                }
                // 빼기 버튼
                R.id.ib_meal_ticket_minus->{
                    if(amount-->0){
                        binding.tvMealTicketAmount.text = (amount).toString()
                        itemClickListener.onItemClick(amount, adapterPosition)
                    }
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_meal_ticket_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(ticketPriceList[position])
    }

    override fun getItemCount() = ticketPriceList.size

    fun update(list: ArrayList<String>){
        ticketPriceList.clear()
        ticketPriceList.addAll(list)
        notifyDataSetChanged()
    }

    data class TicketPriceItem(
        val ticketPrice: Int,
        val ticketAmount: Int
    )
}