package com.example.lesson24

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QueryBuilder {

    private var tables: String? = null
    private var pkField: Int? = null
    private val field: ArrayList<String>? = null
    private var condition: ArrayList<String>? = null

    fun nameOfTable(table: String): QueryBuilder{
        this.tables = table
        return this
    }

    fun nameOfPrimaryKey(pkField:Int, condition: String): QueryBuilder{
        this.pkField = pkField
        this.condition?.add(condition)
        return this
    }

    fun addFields(field:String, condition: String):QueryBuilder{
        this.field?.add(field)
        this.condition?.add(condition)

        return this
    }
}