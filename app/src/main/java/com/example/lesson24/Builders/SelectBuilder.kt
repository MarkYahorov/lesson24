package com.example.lesson24.Builders

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class SelectBuilder {

    private var tables: MutableList<String> = mutableListOf()
    private var allParams: MutableList<String> = mutableListOf()
    private var whereArgs: MutableList<String> = mutableListOf()

    fun nameOfTable(table: String): SelectBuilder {
        this.tables.add(table)
        return this
    }

    fun selectParams(allParams:String): SelectBuilder {
        this.allParams.add(allParams)
        return this
    }

    fun where(whereArgs:String): SelectBuilder {
        this.whereArgs.add(whereArgs)
        return this
    }

    fun select(db:SQLiteDatabase): Cursor{
        val tableText = createTheStringForRequest(tables, ",")
        val allParamsText = createTheStringForRequest(allParams, ",")
        return if (whereArgs[0] == ""){
            db.rawQuery("SELECT $allParamsText FROM $tableText", null)
        } else {
            val whereArgsText = createTheStringForRequest(whereArgs, " AND")
            db.rawQuery("SELECT $allParamsText FROM $tableText WHERE $whereArgsText", null)
        }
    }

    private fun createTheStringForRequest(mutableList: MutableList<String>, separ: String): String{
        var text = ""
        var i = 0
        mutableList.forEach {
            val separator = if (i > 0) {
                separ
            } else {
                ""
            }

            text = "$text$separator $it"
            i++
        }
        return text
    }

    fun update(db: SQLiteDatabase){
        val tableText = createTheStringForRequest(tables, ",")
        val allParamsText = createTheStringForRequest(allParams, ",")
        if (whereArgs.isEmpty()) {
            db.compileStatement("UPDATE $tableText SET $allParamsText").execute()
        } else {
            val whereArgsText = createTheStringForRequest(whereArgs, " AND")
            db.compileStatement("UPDATE $tableText SET $allParamsText WHERE $whereArgsText").execute()
        }
    }
}