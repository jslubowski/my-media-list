package com.example.mymedialist.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymedialist.R
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.viewholders.TextItemViewHolder

class MainListAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<MovieEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_text, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.title
    }
}