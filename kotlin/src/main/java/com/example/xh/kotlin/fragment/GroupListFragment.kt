package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.xh.kotlin.R
import com.example.xh.kotlin.adapter.GroupRecyclerAdapter


class GroupListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_group_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        PagerSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    class Adapter: GroupRecyclerAdapter<Adapter.Holder, Adapter.Holder>(){
        override fun getSectionCount(): Int {
            return 10
        }

        override fun getChildCount(section: Int): Int {
            return section+1
        }

        override fun onCreateSectionViewHolder(parent: ViewGroup): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_main, parent, false))
        }

        override fun onCreateChildViewHolder(parent: ViewGroup): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyler_textview, parent, false))
        }

        override fun onBindSectionViewHolder(holder: Holder, section: Int) {
            if (holder.itemView is TextView){
                holder.itemView.setText("Section: $section")
            }
        }

        override fun onBindChildViewHolder(holder: Holder, indexPath: IndexPath) {
            if (holder.itemView is TextView){
                holder.itemView.setText("Section: ${indexPath.section}, row: ${indexPath.row}")
            }
        }

        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        }
    }
}