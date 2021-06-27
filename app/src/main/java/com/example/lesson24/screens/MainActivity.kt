package com.example.lesson24.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.models.MainSceenPost
import com.example.lesson24.adapters.PostRecyclerAdapter
import com.example.lesson24.R
import com.example.lesson24.SelectBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val list = mutableListOf<MainSceenPost>()
    private val anyList = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.post_recycler)
        App().getInstance()
    }

    override fun onStart() {
        super.onStart()
        createList()
        with(recyclerView){
            adapter = PostRecyclerAdapter(list){
                val intent = Intent(this@MainActivity, CurrentPostActivity::class.java)
                    .putExtra("ID", it.id)
                startActivity(intent)
            }
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        anyList.add("bala")
        anyList.add(82)
        anyList.add(false)
    }

    private fun createList(){
         val cursor = SelectBuilder().selectParams("post._id, post.title, post.body, post.userId, user.email")
            .nameOfTable("post, user")
            .where("user._id = post.userId")
             .select(App().getDb())

        if (cursor.moveToFirst()){
            val id = cursor.getColumnIndexOrThrow("_id")
            val title = cursor.getColumnIndexOrThrow("title")
            val body = cursor.getColumnIndexOrThrow("body")
            val userId = cursor.getColumnIndexOrThrow("userId")
            val userEmail = cursor.getColumnIndexOrThrow("email")
            do {
                val postId = cursor.getInt(id)
                val postTitle = cursor.getString(title)
                val postBody =  cursor.getString(body)
                val postUserId = cursor.getInt(userId)
                val postUserEmail = cursor.getString(userEmail)
                list.add(MainSceenPost(postId,postUserId,postTitle,postBody, postUserEmail))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
    }
}