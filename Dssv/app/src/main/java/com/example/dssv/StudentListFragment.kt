package com.example.dssv

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment(R.layout.fragment_student_list) {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val container = view.findViewById<LinearLayout>(R.id.listContainer)

        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            findNavController().navigate(R.id.toAdd)
        }

        viewModel.students.observe(viewLifecycleOwner) {
            container.removeAllViews()

            it.forEachIndexed { index, student ->
                val item = layoutInflater.inflate(R.layout.item_student, null)

                item.findViewById<TextView>(R.id.txtName).text = student.name
                item.findViewById<TextView>(R.id.txtMSSV).text = student.mssv

                item.setOnClickListener {
                    viewModel.selectedIndex = index
                    findNavController().navigate(R.id.toEdit)
                }

                item.findViewById<ImageView>(R.id.btnDelete).setOnClickListener {
                    viewModel.deleteStudent(index)
                }

                container.addView(item)
            }
        }
    }
}
