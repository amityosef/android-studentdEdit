package com.idz.assigment2

import androidx.recyclerview.widget.RecyclerView
import com.idz.assigment2.databinding.StudentRowLayoutBinding
import com.idz.assigment2.models.Model
import com.idz.assigment2.models.Student

class StudentRowViewHolder(
    private val binding: StudentRowLayoutBinding,
    private val listener: OnItemClickListener?
): RecyclerView.ViewHolder(binding.root) {

    private var student: Student? = null

    init {
        binding.checkbox.setOnClickListener {
            student?.let { currentStudent ->
                currentStudent.isPresent = binding.checkbox.isChecked
                Model.shared.updateStudent(currentStudent) {
                }
            }
        }

        itemView.setOnClickListener {
            student?.let { student ->
                listener?.onStudentItemClick(student)
            }
        }
    }

    fun bind(student: Student, position: Int) {
        this.student = student
        binding.nameTextView.text = student.name
        binding.idTextView.text = student.id
        binding.checkbox.apply {
            isChecked = student.isPresent
            tag = position
        }
        binding.imageView.setImageResource(R.drawable.avatar)
    }
}