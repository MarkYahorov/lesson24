package com.example.lesson24.Builders

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class SelectBuilder {

    private var tables: MutableList<String> = mutableListOf()
    private var allParams: MutableList<String> = mutableListOf()
    private var whereArgs: MutableList<String> = mutableListOf()
    private var orderByArgs: MutableList<String> = mutableListOf()

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
        val orderByText = createTheStringForRequest(orderByArgs, ",")
        val whereArgsText = createTheStringForRequest(whereArgs, " AND ")
        return if (whereArgs.isNotEmpty() && orderByArgs.isNotEmpty()){
            db.rawQuery("SELECT $allParamsText FROM $tableText WHERE $whereArgsText ORDER BY $orderByText", null)
        } else if (orderByArgs.isNotEmpty() && whereArgs.isEmpty()){
            db.rawQuery("SELECT $allParamsText FROM $tableText ORDER BY $orderByText", null)
        } else if(orderByArgs.isEmpty() && whereArgs.isNotEmpty()) {
            db.rawQuery("SELECT $allParamsText FROM $tableText WHERE $whereArgsText", null)
        }else {
            db.rawQuery("SELECT $allParamsText FROM $tableText",null)
        }
    }

    private fun createTheStringForRequest(mutableList: MutableList<String>, separ: String): String{
        return mutableList.joinToString(separ)
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

    fun addOrderByArgs(argsForOrderBy:String):SelectBuilder{
        this.orderByArgs.add(argsForOrderBy)
        return this
    }
}