package com.example.lesson24

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "SQLite_DB"
const val VERSION = 2

class Db(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    private val USER = "user"
    private val POST = "post"
    private val COMMENTS = "comments"
    private val ALLCOLUMSINUSERTABLE = "firstName, lastName, email"
    private val ALLCOLOUMNSINPOSTTABLE ="title,body,userId"
    private val ALLCOLOUMNSINCOMMENTTABLE = "postId,userId,text"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            TBuilder().setName("post")
                .addPKField("_id", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT")
                .addField("title", "TEXT NOT NULL")
                .addField("body", "TEXT NOT NULL")
                .addField("userId", "INTEGER NOT NULL")
                .create(db)

            TBuilder().setName("user")
                .addPKField("_id", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT")
                .addField("firstName", "TEXT NOT NULL")
                .addField("lastName", "TEXT NOT NULL")
                .addField("email", "TEXT NOT NULL")
                .create(db)

            TBuilder().setName("comments")
                .addPKField("_id", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT")
                .addField("postId", "INTEGER NOT NULL")
                .addField("userId", "INTEGER NOT NULL")
                .addField("text", "TEXT NOT NULL")
                .create(db)
        }
        if (App.isFirst) {
            db?.let {
                setAllUsersInDb(db,USER, ALLCOLUMSINUSERTABLE)
                setAllPostsInDB(db, POST, ALLCOLOUMNSINPOSTTABLE)
                setAllComments(db, COMMENTS, ALLCOLOUMNSINCOMMENTTABLE)
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun setAllUsersInDb(db: SQLiteDatabase?, user:String, allColumnsInUserTable:String){
        setAbstractValiueInColumn(db, user, allColumnsInUserTable, "Piter", "Pedigrue", "rat@mail.ru")
        setAbstractValiueInColumn(db, user, allColumnsInUserTable,"Tom", "Raddle", "killer@mail.ru")
        setAbstractValiueInColumn(db, user, allColumnsInUserTable,"Harry", "Potter", "cryBaby@mail.ru")
        setAbstractValiueInColumn(db, user, allColumnsInUserTable,"Hagrid", "unknowing", "lovePets@mail.ru")
        setAbstractValiueInColumn(db, user, allColumnsInUserTable,"Naruto", "Uzumaki", "helpReturnSaske@mail.ru")
    }

    fun setAllPostsInDB(db: SQLiteDatabase?, post:String, allColumnsInPostTable:String){
        setAbstractValiueInColumn(db, post, allColumnsInPostTable, "How I kill my friends?", "sdfgsdfgsdfgsdfgsdfgsdg", 1)
        setAbstractValiueInColumn(db, post, allColumnsInPostTable,"I love pets))", "sdfgsdfgsdfgsdfg", 4)
        setAbstractValiueInColumn(db, post, allColumnsInPostTable,"I will be a HOKAGE!", "dfshfhfhgdfghdfghfg", 5)
        setAbstractValiueInColumn(db, post, allColumnsInPostTable,"I love my Lord", "dfghdfghdfghdfgh", 1)
        setAbstractValiueInColumn(db, post, allColumnsInPostTable,"I am the best witcher", "dfghdsgjdhjghjghj", 2)
    }

    fun setAllComments(db: SQLiteDatabase?, comment:String, allColumnsInCommentsTable:String){
        setAbstractValiueInColumn(db, comment, allColumnsInCommentsTable, 1,1, "Yep")
        setAbstractValiueInColumn(db, comment, allColumnsInCommentsTable,2,2, "the coolest - snake")
        setAbstractValiueInColumn(db, comment, allColumnsInCommentsTable,1,3,"you are bitch")
        setAbstractValiueInColumn(db, comment, allColumnsInCommentsTable,4,4, "He looser")
        setAbstractValiueInColumn(db, comment, allColumnsInCommentsTable,3,2, "dont cry if not")
    }
    fun setAbstractValiueInColumn(db: SQLiteDatabase?,
                                  tableName:String,
                                  allNameColumn:String,
                                  firstValue:Any,
                                  secondValue:Any,
                                  lastValue:Any
    ) {
        db?.let {
            InsertBuilder()
                .setTableName(tableName)
                .addSelectFieldsForInsert(allNameColumn)
                .addInsertValues(firstValue)
                .addInsertValues(secondValue)
                .addInsertValues(lastValue)
                .insertTheValues(db)
        }
    }

}