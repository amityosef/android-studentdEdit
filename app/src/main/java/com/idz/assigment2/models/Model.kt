package com.idz.assigment2.models

import android.os.Handler
import android.os.Looper
import com.idz.assigment2.base.Completion
import com.idz.assigment2.base.StudentCompletion
import com.idz.assigment2.base.StudentsCompletion
import com.idz.assigment2.dao.AppLocalDB
import com.idz.assigment2.dao.AppLocalDbRepository
import java.util.concurrent.Executors

class Model private constructor() {

    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler.createAsync(Looper.getMainLooper())

    private val database: AppLocalDbRepository = AppLocalDB.db

    companion object {
        val shared = Model()
    }

    fun getAllStudents(completion: StudentsCompletion) {
        executor.execute {
            val students = database.studentDao.getAllStudents()
            mainHandler.post {
                completion(students)
            }
        }
    }

    fun addStudent(student: Student, completion: Completion) {
        executor.execute {
            database.studentDao.insertStudents(student)
            mainHandler.post {
                completion()
            }
        }
    }

    fun updateStudent(student: Student, completion: Completion) {
        executor.execute {
            database.studentDao.updateStudent(student)
            mainHandler.post {
                completion()
            }
        }
    }

    fun deleteStudentById(id: String, completion: Completion) {
        executor.execute {
            database.studentDao.deleteStudentById(id)
            mainHandler.post {
                completion()
            }
        }
    }

    fun getStudentById(id: String, completion: StudentCompletion) {
        executor.execute {
            val student = database.studentDao.getStudentById(id)
            mainHandler.post {
                if (student != null) {
                    completion(student)
                }
            }
        }
    }
}
