package com.idz.assigment2.dao

object AppLocalDB {

    val db: AppLocalDbRepository by lazy {
        AppLocalDbRepository()
    }
}