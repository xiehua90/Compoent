package com.example.xh.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.xh.kotlin.R
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val context: Context) : ListAdapter<String, MainAdapter.MainViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_main, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.apply {
            bind(getItem(position))
            itemView.setOnClickListener {
                listener?.onItemClick(itemView, position)
            }
        }
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(text: String) {
            if (itemView is TextView) {
                itemView.text = text
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}

