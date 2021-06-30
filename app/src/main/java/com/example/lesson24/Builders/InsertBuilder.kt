package com.example.lesson24.Builders

import android.database.sqlite.SQLiteDatabase

class InsertBuilder {

    private var tableName: String = ""
    private var selectedTableName: String = ""
    private val selectedFieldsInTable = mutableListOf<Any>()
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
        val selectedFields = createTheStringForRequest(selectedFieldsInTable, ", ")
        val insertedValuesText = createTheStringForRequest(insertedValues, ", ")
        if (insertedValues.isEmpty()) {
            db.compileStatement("INSERT INTO $tableName ($selectedFields) VALUES ($insertedValuesText)")
                ?.execute()
        } else {
            db.compileStatement("INSERT INTO $tableName ($selectedFields) SELECT $insertedValuesText FROM $selectedTableName")
                ?.execute()
        }
    }

    fun drop(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE $tableName")
    }

    fun rename(db: SQLiteDatabase) {
        db.execSQL("ALTER TABLE $tableName RENAME TO $selectedTableName")
    }

    private fun createTheStringForRequest(mutableList: MutableList<Any>, separ: String): String {
        return mutableList.joinToString(separ)
    }
}