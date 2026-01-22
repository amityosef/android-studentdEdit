package com.idz.assigment2.dao

import com.idz.assigment2.models.Student
import java.util.concurrent.ConcurrentHashMap

class InMemoryStudentDao : StudentDao {

    private val students = ConcurrentHashMap<String, Student>()

    override fun getAllStudents(): List<Student> = students.values.toList()

    override fun getStudentById(id: String): Student? = students[id]

    override fun insertStudents(vararg students: Student) {
        students.forEach { student ->
            this.students[student.id] = student
        }
    }

    override fun updateStudent(student: Student) {
        students[student.id] = student
    }

    override fun deleteStudent(student: Student) {
        students.remove(student.id)
    }

    override fun deleteStudentById(id: String) {
        students.remove(id)
    }
}

