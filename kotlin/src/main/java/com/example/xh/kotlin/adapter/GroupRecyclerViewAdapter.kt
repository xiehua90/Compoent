package com.example.xh.kotlin.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
/**
 * @author A18060谢荣华
 * */

abstract class GroupRecyclerViewAdapter<S : RecyclerView.ViewHolder, C : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _index = arrayListOf<IndexPath>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) onCreateSectionViewHolder(parent) else onCreateChildViewHolder(parent)
    }

    override fun getItemCount(): Int {
        var count = 0
        val sectionCount = getSectionCount()

        count += getSectionCount()

        _index.clear()
        for (i in 0 until sectionCount) {
            _index.add(IndexPath(i, -1))
            val rowCount = getChildCount(i)
            count += rowCount

            for (j in 0 until rowCount) {
                _index.add(IndexPath(i, j))
            }
        }

        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val indexPath = _index[position]

        if (indexPath.row == -1) onBindSectionViewHolder(holder as S, indexPath.section)
        else onBindChildViewHolder(holder as C, indexPath)
    }

    abstract fun getSectionCount(): Int

    abstract fun getChildCount(section: Int): Int

    abstract fun onCreateSectionViewHolder(parent: ViewGroup): S

    abstract fun onCreateChildViewHolder(parent: ViewGroup): C

    abstract fun onBindSectionViewHolder(holder: S, section: Int)

    abstract fun onBindChildViewHolder(holder: C, indexPath: IndexPath)

    override fun getItemViewType(position: Int): Int {
        return if (_index[position].row == -1) 0 else 1
    }

    data class IndexPath(var section: Int = 0, var row: Int = 0)
}
