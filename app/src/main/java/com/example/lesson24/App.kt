package com.example.lesson24

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast

class App : Application() {

    companion object {
        private lateinit var INSTANCE: App
        var isFirst = true
        private lateinit var db: Db
        private lateinit var dbSql:SQLiteDatabase
    }

    @SuppressLint("Recycle")
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        isFirst = getSharedPreferences("SHARED", Context.MODE_PRIVATE).getBoolean(
            "THIS_IS_FIRST", true
        )
        db = Db(this)
        dbSql = db.writableDatabase
        setInShared()
    }

    fun getInstance(): App {
        return INSTANCE
    }

    fun getDb(): SQLiteDatabase {
        return dbSql
    }

    private fun setInShared(){
        getSharedPreferences("SHARED", Context.MODE_PRIVATE)
            .edit()
            .apply {
                putBoolean("THIS_IS_FIRST", false)
            }.apply()
    }
}