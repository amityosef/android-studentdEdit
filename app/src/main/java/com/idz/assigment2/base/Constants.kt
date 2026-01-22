package com.idz.assigment2.base

import com.idz.assigment2.models.Student

typealias StudentsCompletion = (List<Student>) -> Unit
typealias StudentCompletion = (Student) -> Unit
typealias Completion = () -> Unit