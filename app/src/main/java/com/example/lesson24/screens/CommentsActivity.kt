package com.example.lesson24.screens

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.R
import com.example.lesson24.Builders.SelectBuilder
import com.example.lesson24.adapters.CommentsAdapter
import com.example.lesson24.models.CommentsScreenModel

class CommentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val list = mutableListOf<CommentsScreenModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        recyclerView = findViewById(R.id.comments_recycler)
    }

    override fun onStart() {
        super.onStart()
        createList()
        with(recyclerView){
            adapter = CommentsAdapter(list)
            layoutManager = LinearLayoutManager(this@CommentsActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun createList(){
        val currentPostId = intent.getIntExtra("POST_ID", 0)
        val cursor = createRequestToDb(currentPostId)
        if (cursor.moveToFirst()){
            val id = cursor.getColumnIndexOrThrow("_id")
            val commentsTextIdColumn = cursor.getColumnIndexOrThrow("text")
            val userEmailIdColumn = cursor.getColumnIndexOrThrow("email")
            val rateIdColumn = cursor.getColumnIndexOrThrow("rate")
            do {
                val commentId = cursor.getInt(id)
                val commentsText = cursor.getString(commentsTextIdColumn)
                val userEmail = cursor.getString(userEmailIdColumn)
                val rate = cursor.getInt(rateIdColumn)
                list.add(CommentsScreenModel(commentId, commentsText, userEmail,rate))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun createRequestToDb(id:Int?):Cursor{
        return SelectBuilder()
            .selectParams("comments._id")
            .selectParams("comments.text")
            .selectParams("user.email")
            .selectParams("comments.rate")
            .nameOfTable("comments")
            .nameOfTable("post")
            .nameOfTable("user")
            .where("post._id = comments.postId")
            .where("user._id = comments.userId")
            .where("comments.postId = $id")
            .select(App().getDb())
    }
}