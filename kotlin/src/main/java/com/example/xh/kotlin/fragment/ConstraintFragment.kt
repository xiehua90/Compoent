package com.example.xh.kotlin.fragment

import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_constraint_layout.*
import kotlinx.android.synthetic.main.recycler_constraint_item.*

class ConstraintFragment : BaseFragement() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_constraint_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView.adapter = MyAdapter(getData())
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(0, 10, 0, 0)
            }
        })

    }

    fun getData(): List<Bean> {

        var list = mutableListOf<Bean>();
        for (i in 0 until 50) {

            var title = ""
            var content = ""
            var note = ""

            if (i < 20) {
                for (j in 0 until i) {
                    title += "title:$i"
                    content += "content:${i % 6}"
                    note += "note:${i % 4}"
                }
            }


            list.add(Bean(title, content, note))
        }
        return list

    }


    class MyAdapter(var data: List<Bean>) : RecyclerView.Adapter<MyHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_constraint_item, parent, false))
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.bind(data.get(position), position)
        }

    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleView: TextView
        var contentView: TextView
        var noteView: TextView
        var btn: Button

        init {
            titleView = view.findViewById(R.id.tv_title)
            contentView = view.findViewById(R.id.tv_content)
            noteView = view.findViewById(R.id.tv_note)
            btn = view.findViewById(R.id.btn)
        }

        fun bind(bean: Bean, pos: Int) {
            setText(titleView, bean.title)
            setText(contentView, bean.content)
            setText(noteView, bean.note)
            btn.setText("$pos")

            btn.setOnClickListener {
                if (pos % 2 == 0) {
                    bean.title = "title"
                    setText(titleView, bean.title)
                }

                if (pos % 3 == 0) {
                    bean.content = "content"
                    setText(contentView, bean.content)

                }

                if (pos % 4 == 0) {
                    bean.note = "note"
                    setText(noteView, bean.note)
                }
            }

        }

        fun setText(view: TextView, text: String) {
            if (TextUtils.isEmpty(text)) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                view.text = text
            }

        }
    }

    data class Bean(var title: String, var content: String, var note: String)
}