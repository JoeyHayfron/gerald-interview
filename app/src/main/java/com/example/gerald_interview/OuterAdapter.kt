package com.example.gerald_interview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OuterAdapter(val data: ApiResponse, private val showClickListener: (Show) -> Unit) : RecyclerView.Adapter<OuterAdapter.OuterViewHolder>(){

    private val viewPool = RecyclerView.RecycledViewPool()

    private val onItemClickListener: (Show) -> Unit = { item ->
        showClickListener(item)
    }


    class OuterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.innerRecyclerView)
        val category: TextView = itemView.findViewById(R.id.sectionHeader)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent,false)
        return OuterViewHolder(v)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val items = data.groupShows()


        holder.category.text = items.keys.toTypedArray()[position]
        val childManagerLayout = LinearLayoutManager(holder.recyclerView.context, LinearLayout.HORIZONTAL, false)

        childManagerLayout.initialPrefetchItemCount = items.count()

        holder.recyclerView.apply{
            layoutManager = childManagerLayout
            adapter = InnerAdapter(items.values.toTypedArray()[position], onItemClickListener)
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return data.groupShows().count()
    }

    interface onShowListener{
        fun onShowClicked(show: Show)
    }
}