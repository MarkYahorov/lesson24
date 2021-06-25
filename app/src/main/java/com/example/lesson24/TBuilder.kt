package com.example.lesson24

import android.database.sqlite.SQLiteDatabase

class TBuilder {
    var name: String = ""
    var pkField : MutableMap<String, String> = mutableMapOf()
    var fields: MutableMap<String, String> = mutableMapOf()

    fun setName(table: String): TBuilder {
        this.name = table
        return this
    }

    fun addPKField(id:String, condition: String):TBuilder{
        this.pkField[id] = condition
        return this
    }

    fun addField(title: String, condition: String): TBuilder {
        this.fields[title] = condition
        return this
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