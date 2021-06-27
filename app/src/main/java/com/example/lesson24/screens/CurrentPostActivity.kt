package com.example.lesson24.screens

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lesson24.App
import com.example.lesson24.R
import com.example.lesson24.SelectBuilder

class CurrentPostActivity : AppCompatActivity() {

    private lateinit var currentTitle: TextView
    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var body: TextView
    private lateinit var goToCommentsBtn: Button
    var postId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_post)

        initAll()
    }

    private fun initAll(){
        currentTitle = findViewById(R.id.current_title_text)
        fullName = findViewById(R.id.full_name_user_of_post)
        email = findViewById(R.id.email_user_of_post)
        body = findViewById(R.id.current_body)
        goToCommentsBtn = findViewById(R.id.go_to_comment_btn)
    }

    override fun onStart() {
        super.onStart()
        createList()
        goToScreenBtnListener()
    }

    private fun createList(){
        val currentPostId = intent?.getIntExtra("ID", 0)
        val cursor = createRequestToDb(currentPostId)
        if (cursor.moveToFirst()){
            val idIdColumn = cursor.getColumnIndexOrThrow("_id")
            val titleIdColumn = cursor.getColumnIndexOrThrow("title")
            val bodyIdColumn = cursor.getColumnIndexOrThrow("body")
            val fullNameIdColumn = cursor.getColumnIndexOrThrow("fullName")
            val userEmailIdColumn = cursor.getColumnIndexOrThrow("email")
            do {
                postId = cursor.getInt(idIdColumn)
                currentTitle.text = cursor.getString(titleIdColumn)
                fullName.text =  cursor.getString(fullNameIdColumn)
                body.text = cursor.getString(bodyIdColumn)
                email.text = cursor.getString(userEmailIdColumn)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun createRequestToDb(id:Int?): Cursor{
        return SelectBuilder().selectParams("post._id")
            .selectParams("post.title")
            .selectParams("post.body")
            .selectParams("post.userId")
            .selectParams("user.email")
            .selectParams("user.firstName|| ' '||user.lastName as fullName")
            .nameOfTable("post")
            .nameOfTable("user")
            .where("post._id = $id")
            .select(App().getDb())
    }

    private fun goToScreenBtnListener(){
        goToCommentsBtn.setOnClickListener {
            val intent = Intent(this, CommentsActivity::class.java)
                .putExtra("POST_ID", postId)
            startActivity(intent)
        }
    }
}