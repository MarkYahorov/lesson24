package com.example.lesson24

import android.database.sqlite.SQLiteDatabase

class TBuilder {
    var name: String = ""
    var where:String = ""
    var fields: MutableMap<String, String> = mutableMapOf()

    fun setName(table: String): TBuilder {
        this.name = table
        return this
    }

    fun addField(title: String, condition: String): TBuilder {
        this.fields[title] = condition
        return this
    }

    fun create(db: SQLiteDatabase) {
        var fieldsText: String = ""
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

    }
}