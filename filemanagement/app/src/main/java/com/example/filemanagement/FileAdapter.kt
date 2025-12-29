package com.example.filemanagement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.io.File

class FileAdapter(
    private val context: Context,
    private val files: List<File>
) : BaseAdapter() {

    override fun getCount(): Int = files.size

    override fun getItem(position: Int): Any = files[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_file, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val file = files[position]

        if (file.isDirectory) {
            tvName.text = "[DIR] ${file.name}"
        } else {
            tvName.text = file.name
        }
        return view
    }
}
