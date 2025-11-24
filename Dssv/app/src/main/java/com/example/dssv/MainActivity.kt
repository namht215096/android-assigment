package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dssv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val students = ArrayList<Student>()
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener { addStudent() }
        binding.btnUpdate.setOnClickListener { updateStudent() }
    }

    private fun addStudent() {
        val name = binding.edtName.text.toString()
        val mssv = binding.edtMSSV.text.toString()

        if (name.isBlank() || mssv.isBlank()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(name, mssv)
        students.add(student)
        refreshList()

        binding.edtName.setText("")
        binding.edtMSSV.setText("")
    }

    private fun refreshList() {
        binding.listContainer.removeAllViews()

        for ((index, student) in students.withIndex()) {
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_student, null)

            val txtName = itemView.findViewById<TextView>(R.id.txtName)
            val txtMSSV = itemView.findViewById<TextView>(R.id.txtMSSV)
            val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)

            txtName.text = student.name
            txtMSSV.text = student.mssv


            itemView.setOnClickListener {
                selectedIndex = index
                binding.edtName.setText(student.name)
                binding.edtMSSV.setText(student.mssv)
            }

            btnDelete.setOnClickListener {
                students.removeAt(index)
                refreshList()
            }

            binding.listContainer.addView(itemView)
        }
    }

    private fun updateStudent() {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Chọn sinh viên để cập nhật!", Toast.LENGTH_SHORT).show()
            return
        }

        val name = binding.edtName.text.toString()
        val mssv = binding.edtMSSV.text.toString()

        if (name.isBlank() || mssv.isBlank()) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        students[selectedIndex].name = name
        students[selectedIndex].mssv = mssv

        refreshList()

        binding.edtName.setText("")
        binding.edtMSSV.setText("")
        selectedIndex = -1
    }
}
