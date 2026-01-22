package com.idz.assigment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import com.idz.assigment2.databinding.ActivityAddStudentBinding
import com.idz.assigment2.models.Model
import com.idz.assigment2.models.Student

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupView() {

        binding.loadingIndicator.visibility = View.GONE

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveStudentButton.setOnClickListener {

            binding.loadingIndicator.visibility = View.VISIBLE

            val studentName: String = binding.nameEditText.text.toString().trim()
            val studentId: String = binding.idEditText.text.toString().trim()
            val studentPhone: String = binding.phoneEditText.text.toString().trim()
            val studentAddress: String = binding.addressEditText.text.toString().trim()
            val isChecked: Boolean = binding.checkedCheckbox.isChecked

            val student = Student(
                name = studentName,
                id = studentId,
                isPresent = isChecked,
                avatarUrlString = null,
                phone = studentPhone.ifEmpty { null },
                address = studentAddress.ifEmpty { null }
            )

            Model.shared.addStudent(student) {
                dismiss()
            }
        }
    }


    private fun dismiss() {
        finish()
    }
}