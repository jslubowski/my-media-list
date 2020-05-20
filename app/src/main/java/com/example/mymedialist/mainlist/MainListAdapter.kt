package com.example.mymedialist.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymedialist.R
import com.example.mymedialist.model.MovieEntity

class MainListAdapter(private val onLongClickListener: OnLongClickListener) : ListAdapter<MovieEntity, MainListAdapter.ViewHolder>(MovieEntityDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(item)
        }
        holder.bind(item)
    }

    class OnLongClickListener(val clickListener: (movieEntity: MovieEntity) -> Boolean) {
        fun onLongClick(movieEntity: MovieEntity) = clickListener(movieEntity)
    }

    class ViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val lastSeen: TextView = itemView.findViewById(R.id.date_text)
        private val description: TextView = itemView.findViewById(R.id.description_text)
        private val imageCover: ImageView = itemView.findViewById(R.id.movie_cover)

        fun bind(
            item: MovieEntity
        ) {
            val res = itemView.context.resources

            movieTitle.text = item.title
            lastSeen.text = item.seenOnDate
            description.text = item.description
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}