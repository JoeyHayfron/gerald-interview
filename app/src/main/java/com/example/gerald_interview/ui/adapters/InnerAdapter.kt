package com.example.gerald_interview.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gerald_interview.R
import com.example.gerald_interview.data.models.Show
import com.squareup.picasso.Picasso

class InnerAdapter(private val data: List<Show>, private val itemClickListener: (Show) -> Unit) : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

    class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val thumbnail: ImageView = itemView.findViewById(R.id.movieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return InnerViewHolder(v)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val item = data[position]
        holder.thumbnail.setOnClickListener{ itemClickListener(item)}
        Picasso.get().load(item.content.thumbnail).into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}