package com.idz.assigment2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idz.assigment2.databinding.StudentRowLayoutBinding
import com.idz.assigment2.models.Student

interface OnItemClickListener {
    fun onStudentItemClick(student: Student)
}

class StudentsAdapter(
    private var students: List<Student>,
): RecyclerView.Adapter<StudentRowViewHolder>() {

    var listener: OnItemClickListener? = null
    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentRowViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = StudentRowLayoutBinding.inflate(inflator, parent, false)
        return StudentRowViewHolder(
            binding = binding,
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: StudentRowViewHolder, position: Int) {
        holder.bind(students[position], position)
    }
}