package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            viewModel.addStudent(
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
