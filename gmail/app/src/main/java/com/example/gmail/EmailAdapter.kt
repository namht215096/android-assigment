package com.example.gmail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmail.R

class EmailAdapter(private val items: List<Email>)
    : RecyclerView.Adapter<EmailAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.avatar)
        val name: TextView = view.findViewById(R.id.name)
        val subject: TextView = view.findViewById(R.id.subject)
        val snippet: TextView = view.findViewById(R.id.snippet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val e = items[position]
        holder.avatar.text = e.name.first().toString()
        holder.name.text = e.name
        holder.subject.text = e.subject
        holder.snippet.text = e.snippet
    }

    override fun getItemCount() = items.size
}
