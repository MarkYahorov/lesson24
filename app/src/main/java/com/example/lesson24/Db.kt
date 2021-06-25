package com.example.lesson24

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "SQLite_DB"
const val VERSION = 2

class Db(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
//        db?.let {
//            TBuilder().setName("post")
//                .addField("title", "TEXT NOT NULL")
//                .addField("body", "TEXT NOT NULL")
//                .addField("userId", "INTEGER NOT NULL")
//                .create(db)
//
//            TBuilder().setName("app")
//                .addField("title", "TEXT NOT NULL")
//                .create(db)
//
//            val builder = TBuilder().setName("admin")
//                .addField("title", "TEXT NOT NULL")
//                .addField("body", "TEXT NOT NULL")
//                .addField("userId", "INTEGER NOT NULL")
//                .addField("userId2", "INTEGER NOT NULL")
//                .addField("userId3", "INTEGER NOT NULL")
//                .create(db)
//        }
        val createThePostField =
            "CREATE TABLE if not exists post (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, body TEXT NOT NULL, userId INTEGER NOT NULL)"
        db?.execSQL(createThePostField)
        val createTheUserField =
            "CREATE TABLE if not exists user (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, firstName TEXT NOT NULL, lastName TEXT NOT NULL, email TEXT NOT NULL)"
        db?.execSQL(createTheUserField)
        val createTheCommentField =
            "CREATE TABLE if not exists comments (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, postId INTEGER NOT NULL, userId INTEGER NOT NULL, text TEXT NOT NULL)"
        db?.execSQL(createTheCommentField)
        if (App.isFirst) {
            setUsersInDb(db)
            setPostInDb(db)
            setComments(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun setUsersInDb(db: SQLiteDatabase?) {
        db?.compileStatement("INSERT INTO user (firstName,lastName,email) VALUES('firstUserFirstName','firstUserSecName','mail@mail.ru')")
            ?.execute()
        db?.compileStatement("INSERT INTO user (firstName,lastName,email) VALUES('secondUserFirstName','secondUserSecName','mail2@mail.ru')")
            ?.execute()
        db?.compileStatement("INSERT INTO user (firstName,lastName,email) VALUES('thirdUserFirstName','thirdUserSecName','mail3@mail.ru')")
            ?.execute()
        db?.compileStatement("INSERT INTO user (firstName,lastName,email) VALUES('fourthUserFirstName','fourthUserSecName','mail4@mail.ru')")
            ?.execute()
        db?.compileStatement("INSERT INTO user (firstName,lastName,email) VALUES('fiveUserFirstName','fiveUserSecName','mail5@mail.ru')")
            ?.execute()
    }

    fun setPostInDb(db: SQLiteDatabase?) {
        db?.compileStatement("INSERT INTO post (title,body,userId) VALUES('firstTitle','firstBody',1)")
            ?.execute()
        db?.compileStatement("INSERT INTO post (title,body,userId) VALUES('secondTitle','secondBody',4)")
            ?.execute()
        db?.compileStatement("INSERT INTO post (title,body,userId) VALUES('thirdTitle','thirdBody',5)")
            ?.execute()
        db?.compileStatement("INSERT INTO post (title,body,userId) VALUES('fourthTitle','fourthBody',1)")
            ?.execute()
        db?.compileStatement("INSERT INTO post (title,body,userId) VALUES('fiveTitle','fiveBody',2)")
            ?.execute()
    }

    fun setComments(db: SQLiteDatabase?) {
        db?.compileStatement("INSERT INTO comments (postId,userId,text) VALUES(1,1,'firstComment')")
            ?.execute()
        db?.compileStatement("INSERT INTO comments (postId,userId,text) VALUES(2,2,'secComment')")
            ?.execute()
        db?.compileStatement("INSERT INTO comments (postId,userId,text) VALUES(1,3,'thirdComment')")
            ?.execute()
        db?.compileStatement("INSERT INTO comments (postId,userId,text) VALUES(4,4,'fourthComment')")
            ?.execute()
        db?.compileStatement("INSERT INTO comments (postId,userId,text) VALUES(3,2,'fiveComment')")
            ?.execute()
    }
}