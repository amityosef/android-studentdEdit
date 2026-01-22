package com.idz.assigment2.dao

import com.idz.assigment2.models.Student

interface StudentDao {

    fun getAllStudents(): List<Student>

    fun getStudentById(id: String): Student?

    fun insertStudents(vararg students: Student)

    fun updateStudent(student: Student)

    fun deleteStudent(student: Student)

    fun deleteStudentById(id: String)
}