package com.example.mymedialist.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mymedialist.R
import com.example.mymedialist.model.MovieEntity

class MainListAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    var data = listOf<MovieEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (movieEntity: MovieEntity) -> Unit) {
        fun onClick(movieEntity: MovieEntity) = clickListener(movieEntity)
    }

    class ViewHolder(val textView: View) : RecyclerView.ViewHolder(textView) {
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val lastSeen: TextView = itemView.findViewById(R.id.date_text)
        private val description: TextView = itemView.findViewById(R.id.description_text)
        private val imageCover: ImageView = itemView.findViewById(R.id.movie_cover)

        val detailsDialog = DetailsDialogFragment()

        fun bind(
            item: MovieEntity
        ) {
            val res = itemView.context.resources

            movieTitle.text = item.title
            lastSeen.text = item.seenOnDate
            description.text = item.description
        }
    }
}