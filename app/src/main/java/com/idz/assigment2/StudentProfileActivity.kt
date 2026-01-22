package com.idz.assigment2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.idz.assigment2.databinding.ActivityStudentProfileBinding
import com.idz.assigment2.models.Model
import com.idz.assigment2.models.Student

class StudentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentProfileBinding
    private var studentId: String? = null
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance = this

        supportActionBar?.title = "Student Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        studentId = intent.getStringExtra(STUDENT_ID_KEY)

        loadStudentDetails()

        binding.editButton.setOnClickListener {
            currentStudent?.let { student ->
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra(STUDENT_ID_KEY, student.id)
                intent.putExtra(STUDENT_NAME_KEY, student.name)
                intent.putExtra(STUDENT_CHECKED_KEY, student.isPresent)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        if (instance === this) {
            instance = null
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        loadStudentDetails()
    }

    private fun loadStudentDetails() {
        studentId?.let { id ->
            Model.shared.getStudentById(id) { student ->
                student.let {
                    currentStudent = it
                    displayStudentDetails(it)
                }
            }
        }
    }

    private fun displayStudentDetails(student: Student) {
        binding.nameTextView.text = student.name
        binding.idTextView.text = student.id
        binding.phoneTextView.text = student.phone ?: "N/A"
        binding.addressTextView.text = student.address ?: "N/A"
        binding.checkedCheckBox.isChecked = student.isPresent
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        @Volatile
        var instance: StudentProfileActivity? = null
            private set

        const val STUDENT_ID_KEY = "STUDENT_ID_KEY"
        const val STUDENT_NAME_KEY = "STUDENT_NAME_KEY"
        const val STUDENT_CHECKED_KEY = "STUDENT_CHECKED_KEY"
    }
}