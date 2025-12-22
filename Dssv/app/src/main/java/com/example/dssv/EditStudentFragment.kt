package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.databinding.FragmentEditStudentBinding

class EditStudentFragment : Fragment() {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding = FragmentEditStudentBinding.inflate(inflater, container, false)

        val index = viewModel.selectedIndex
        val student = viewModel.students.value!![index]

        binding.edtName.setText(student.name)
        binding.edtMSSV.setText(student.mssv)

        binding.btnUpdate.setOnClickListener {
            viewModel.updateStudent(
                index,
                Student(
                    binding.edtName.text.toString(),
                    binding.edtMSSV.text.toString()
                )
            )
            findNavController().popBackStack()
        }

        return binding.root
    }
}
