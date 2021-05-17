package com.example.gerald_interview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class InnerAdapter(private val data: List<Show>) : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

    private var onShowListener: OnShowListener? = null

    fun setOnShowClickedListener(onShowListener: OnShowListener){
        this.onShowListener = onShowListener
    }

    interface OnShowListener{
        fun onShowClicked(show: Show)
    }

    class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val thumbnail: ImageView = itemView.findViewById(R.id.movieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return InnerViewHolder(v)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val item = data[position]
        holder.thumbnail.setOnClickListener{
            onShowListener?.onShowClicked(item)
        }
        Picasso.get().load(item.content.thumbnail).into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}