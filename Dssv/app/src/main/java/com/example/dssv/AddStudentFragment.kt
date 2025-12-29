package com.example.dssv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.R
import com.example.dssv.databinding.FragmentAddStudentBinding
import com.example.dssv.StudentViewModel

class AddStudentFragment : Fragment(R.layout.fragment_add_student) {

    private lateinit var binding: FragmentAddStudentBinding
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddStudentBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text.toString()
            val mssv = binding.edtMSSV.text.toString()
            viewModel.addStudent(name, mssv)
            findNavController().popBackStack()
        }
    }
}
