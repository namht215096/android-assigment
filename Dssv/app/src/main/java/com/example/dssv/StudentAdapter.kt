package com.example.dssv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dssv.databinding.ItemStudentBinding
import com.example.dssv.Student

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var list = mutableListOf<Student>()

    fun submitList(newList: MutableList<Student>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = list[position]
        holder.binding.tvName.text = student.name
        holder.binding.tvMssv.text = student.mssv
    }

    override fun getItemCount(): Int = list.size
}
