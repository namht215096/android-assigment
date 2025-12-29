package com.example.dssv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dssv.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment(R.layout.fragment_student_list) {

    private lateinit var binding: FragmentStudentListBinding
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var adapter: StudentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStudentListBinding.bind(view)

        adapter = StudentAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.studentList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }

        viewModel.loadStudents()
    }
}
