package com.example.lesson24.Builders

import android.database.sqlite.SQLiteDatabase

class InsertBuilder {

    private var tableName: String = ""
    private var selectedTableName: String = ""
    private val selectedFieldsInTable = mutableListOf<String>()
    private val insertedValues = mutableListOf<Any>()

    fun setTableName(name: String): InsertBuilder {
        this.tableName = name
        return this
    }

    fun takeASelectedTableName(selectedTableName: String): InsertBuilder {
        this.selectedTableName = selectedTableName
        return this

    }

    fun addSelectFieldsForInsert(nameOfField: String): InsertBuilder {
        selectedFieldsInTable.add(nameOfField)
        return this
    }

    fun addInsertValues(insertingValue: Any): InsertBuilder {
        insertedValues.add(insertingValue)
        return this
    }


    fun insertTheValues(db: SQLiteDatabase) {
        var selectedFields = ""
        var count = 0
        selectedFieldsInTable.forEach {
            val separator = if (count > 0) {
                ","
            } else {
                ""
            }

            selectedFields = "$selectedFields$separator$it"
            count++
        }
        var insertedValuesText = ""
        var i = 0
        insertedValues.forEach {
            val separator = if (i > 0) {
                ","
            } else {
                ""
            }
            insertedValuesText = "$insertedValuesText$separator'$it'"
            i++
        }
        db.compileStatement("INSERT INTO $tableName ($selectedFields) VALUES ($insertedValuesText)")
            ?.execute()
    }

    fun insertTheValuesFromSelectedTable(db: SQLiteDatabase) {
        var selectedFields = ""
        var count = 0
        selectedFieldsInTable.forEach {
            val separator = if (count > 0) {
                ","
            } else {
                ""
            }

            selectedFields = "$selectedFields$separator$it"
            count++
        }
        var insertedValuesText = ""
        var i = 0
        insertedValues.forEach {
            val separator = if (i > 0) {
                ","
            } else {
                ""
            }
            insertedValuesText = "$insertedValuesText$separator$it"
            i++
        }
        db.compileStatement("INSERT INTO $tableName ($selectedFields) SELECT $insertedValuesText FROM $selectedTableName")
            ?.execute()
    }

    fun drop(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE $tableName")
    }

    fun rename(db: SQLiteDatabase) {
        db.execSQL("ALTER TABLE $tableName RENAME TO $selectedTableName")
    }
}