package com.example.dssv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.R
import com.example.dssv.databinding.FragmentEditStudentBinding
import com.example.dssv.StudentViewModel

class EditStudentFragment : Fragment(R.layout.fragment_edit_student) {

    private lateinit var binding: FragmentEditStudentBinding
    private val viewModel: StudentViewModel by activityViewModels()
    private var studentId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditStudentBinding.bind(view)

        studentId = arguments?.getInt("id") ?: 0
        val student = viewModel.getStudentById(studentId)

        student?.let {
            binding.edtName.setText(it.name)
            binding.edtMSSV.setText(it.mssv)
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.updateStudent(
                studentId,
                binding.edtName.text.toString(),
                binding.edtMSSV.text.toString()
            )
            findNavController().popBackStack()
        }
    }
}
