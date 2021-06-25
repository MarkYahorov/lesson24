package com.example.lesson24.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.R
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
        val cursor = App().getDb().rawQuery("SELECT comments._id, comments.text, user.email FROM comments LEFT JOIN post on post._id = comments.postId LEFT JOIN user on user._id = comments.userId WHERE comments.postId = $currentPostId", null)
        if (cursor!= null){
            if (cursor.moveToFirst()){
                val id = cursor.getColumnIndexOrThrow("_id")
                val commentsTextIdColumn = cursor.getColumnIndexOrThrow("text")
                val userEmailIdColumn = cursor.getColumnIndexOrThrow("email")
                do {
                    val commentId = cursor.getInt(id)
                    val commentsText = cursor.getString(commentsTextIdColumn)
                    val userEmail = cursor.getString(userEmailIdColumn)
                    list.add(CommentsScreenModel(commentId, commentsText, userEmail))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
    }
}