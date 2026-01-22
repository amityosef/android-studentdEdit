package com.idz.assigment2.models

data class Student(
    var id: String,
    var name: String,
    var isPresent: Boolean,
    var avatarUrlString: String?,
    var phone: String?,
    var address: String?
)
