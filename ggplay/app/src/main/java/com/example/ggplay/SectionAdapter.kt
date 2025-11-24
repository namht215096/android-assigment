package com.example.ggplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ggplay.R

class SectionAdapter(private val sections: List<Section>)
    : RecyclerView.Adapter<SectionAdapter.SectionVH>() {

    class SectionVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvSectionTitle)
        val rv: RecyclerView = view.findViewById(R.id.rvItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionVH(v)
    }

    override fun onBindViewHolder(holder: SectionVH, position: Int) {
        val section = sections[position]
        holder.title.text = section.title

        holder.rv.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.rv.adapter = HorizontalAdapter(section.items)
    }

    override fun getItemCount() = sections.size
}
