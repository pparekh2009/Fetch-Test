package com.priyanshparekh.fetchtest;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.bindData(itemList[position].listId, itemList[position].name!!)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvListId = view.findViewById<TextView>(R.id.tv_list_id)!!
        private val tvName = view.findViewById<TextView>(R.id.tv_name)!!

        fun bindData(listId: Int, name: String) {
            tvListId.text = listId.toString()
            tvName.text = name
        }
    }
}