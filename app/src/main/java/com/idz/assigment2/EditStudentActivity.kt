package com.idz.assigment2

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.idz.assigment2.databinding.ActivityEditStudentBinding
import com.idz.assigment2.models.Model
import com.idz.assigment2.models.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var originalStudentId: String? = null
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Student"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        originalStudentId = intent.getStringExtra(StudentProfileActivity.STUDENT_ID_KEY)

        loadStudentData()
        setupButtons()
    }

    private fun loadStudentData() {
        originalStudentId?.let { id ->
            binding.loadingIndicator.visibility = View.VISIBLE
            Model.shared.getStudentById(id) { student ->
                binding.loadingIndicator.visibility = View.GONE
                student.let {
                    currentStudent = it
                    displayStudentData(it)
                }
            }
        }
    }

    private fun displayStudentData(student: Student) {
        binding.nameEditText.setText(student.name)
        binding.idEditText.setText(student.id)
        binding.phoneEditText.setText(student.phone ?: "")
        binding.addressEditText.setText(student.address ?: "")
        binding.checkedCheckbox.isChecked = student.isPresent
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            saveStudent()
        }

        binding.deleteButton.setOnClickListener {
            showDeleteConfirmation()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun finishBackToList() {
        // If we came here from the details screen, close it too.
        StudentProfileActivity.instance?.finish()
        finish()
    }

    private fun saveStudent() {
        val newName = binding.nameEditText.text.toString().trim()
        val newId = binding.idEditText.text.toString().trim()
        val newPhone = binding.phoneEditText.text.toString().trim()
        val newAddress = binding.addressEditText.text.toString().trim()
        val isChecked = binding.checkedCheckbox.isChecked

        if (newName.isEmpty()) {
            binding.nameEditText.error = "Name is required"
            return
        }

        if (newId.isEmpty()) {
            binding.idEditText.error = "ID is required"
            return
        }

        binding.loadingIndicator.visibility = View.VISIBLE

        if (originalStudentId != newId) {
            originalStudentId?.let { oldId ->
                Model.shared.deleteStudentById(oldId) {
                    val newStudent = Student(
                        id = newId,
                        name = newName,
                        isPresent = isChecked,
                        avatarUrlString = null,
                        phone = newPhone.ifEmpty { null },
                        address = newAddress.ifEmpty { null }
                    )
                    Model.shared.addStudent(newStudent) {
                        binding.loadingIndicator.visibility = View.GONE
                        finishBackToList()
                    }
                }
            }
        } else {
            val updatedStudent = Student(
                id = newId,
                name = newName,
                isPresent = isChecked,
                avatarUrlString = currentStudent?.avatarUrlString,
                phone = newPhone.ifEmpty { null },
                address = newAddress.ifEmpty { null }
            )
            Model.shared.updateStudent(updatedStudent) {
                binding.loadingIndicator.visibility = View.GONE
                finishBackToList()
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteStudent() {
        binding.loadingIndicator.visibility = View.VISIBLE
        originalStudentId?.let { id ->
            Model.shared.deleteStudentById(id) {
                binding.loadingIndicator.visibility = View.GONE
                finishBackToList()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
