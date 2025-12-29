package com.example.dssv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dssv.StudentDatabaseHelper
import com.example.dssv.Student

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val db = StudentDatabaseHelper(application)
    val studentList = MutableLiveData<MutableList<Student>>()

    fun loadStudents() {
        studentList.value = db.getAllStudents()
    }

    fun addStudent(name: String, mssv: String) {
        db.insertStudent(Student(name = name, mssv = mssv))
        loadStudents()
    }

    fun updateStudent(id: Int, name: String, mssv: String) {
        db.updateStudent(Student(id = id, name = name, mssv = mssv))
        loadStudents()
    }

    fun deleteStudent(id: Int) {
        db.deleteStudent(id)
        loadStudents()
    }

    fun getStudentById(id: Int): Student? {
        return db.getStudentById(id)
    }
}
