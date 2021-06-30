package com.example.lesson24.Builders

import android.database.sqlite.SQLiteDatabase

class TBuilder {
    private var name: String = ""
    private var pkField: MutableMap<String, String> = mutableMapOf()
    private var fields: MutableMap<String, String> = mutableMapOf()

    fun setName(table: String): TBuilder {
        this.name = table
        return this
    }

    fun addPKField(id: String, condition: String): TBuilder {
        this.pkField[id] = condition
        return this
    }

    fun addField(title: String, condition: String): TBuilder {
        this.fields[title] = condition
        return this
    }

    fun createNewColumn(db: SQLiteDatabase) {
        var fieldsText = ""
        var i = 0
        fields.forEach {
            val separator = if (i > 0) {
                ","
            } else {
                ""
            }

            fieldsText = "$fieldsText$separator ${it.key} ${it.value}"
            i++
        }
        db.execSQL("ALTER TABLE $name ADD COLUMN $fieldsText")
    }

    fun create(db: SQLiteDatabase) {
        var fieldsText = ""
        var i = 0
        fields.forEach {
            val separator = if (i > 0) {
                ","
            } else {
                ""
            }

            fieldsText = "$fieldsText$separator ${it.key} ${it.value}"
            i++
        }

        var pkFieldSting = ""
        pkField.forEach {
            pkFieldSting = "${it.key} ${it.value},"
        }
        db.execSQL("CREATE TABLE $name ($pkFieldSting $fieldsText)")
    }
}