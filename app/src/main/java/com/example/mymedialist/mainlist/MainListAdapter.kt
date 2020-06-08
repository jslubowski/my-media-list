package com.example.mymedialist.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymedialist.databinding.ListItemBinding
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

    class ViewHolder private constructor(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageCover: ImageView = binding.movieCover

        fun bind(
            item: MovieEntity
        ) {
            val res = itemView.context.resources

            binding.movieTitle.text = item.title
            binding.seenOnText.text = item.seenOnDate
            binding.descriptionText.text = item.description
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}