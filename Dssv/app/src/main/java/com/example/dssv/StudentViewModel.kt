package com.example.dssv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    val students = MutableLiveData<MutableList<Student>>(mutableListOf())
    var selectedIndex = -1

    fun addStudent(student: Student) {
        students.value?.add(student)
        students.value = students.value
    }

    fun updateStudent(index: Int, student: Student) {
        students.value?.set(index, student)
        students.value = students.value
    }

    fun deleteStudent(index: Int) {
        students.value?.removeAt(index)
        students.value = students.value
    }
}
