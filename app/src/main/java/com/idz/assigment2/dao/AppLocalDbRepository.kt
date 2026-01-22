package com.idz.assigment2.dao

class AppLocalDbRepository(
    val studentDao: StudentDao = InMemoryStudentDao()
)
