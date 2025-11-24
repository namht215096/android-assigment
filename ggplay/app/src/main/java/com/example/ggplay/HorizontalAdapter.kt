package com.example.ggplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ggplay.R

class HorizontalAdapter(private val items: List<Item>)
    : RecyclerView.Adapter<HorizontalAdapter.ItemVH>() {

    class ItemVH(view: View) : RecyclerView.ViewHolder(view) {
        val thumb: TextView = view.findViewById(R.id.thumb)
        val name: TextView = view.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horizontal_card, parent, false)
        return ItemVH(v)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.thumb.text = item.name.firstOrNull()?.toString() ?: "?"
    }

    override fun getItemCount() = items.size
}
