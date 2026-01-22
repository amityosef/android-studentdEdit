package com.idz.assigment2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.idz.assigment2.databinding.ActivityStudentsListBinding
import com.idz.assigment2.models.Model
import com.idz.assigment2.models.Student

class StudentsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        binding.addFab.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layout
        binding.recyclerView.setHasFixedSize(true)
        loadStudents()
    }

    private fun loadStudents() {
        binding.progressBar.visibility = View.VISIBLE
        Model.shared.getAllStudents { students ->

            binding.progressBar.visibility = View.GONE
            val adapter = StudentsAdapter(students)
            adapter.listener = object : OnItemClickListener {

                override fun onStudentItemClick(student: Student) {
                    navigateToStudentProfile(student)
                }
            }
            binding.recyclerView.adapter = adapter
        }

    }

    private fun navigateToStudentProfile(student: Student) {
        val intent = Intent(this, StudentProfileActivity::class.java)
        intent.putExtra(StudentProfileActivity.STUDENT_ID_KEY, student.id)
        startActivity(intent)
    }
}