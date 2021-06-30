package com.example.lesson24.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.models.MainSceenPost
import com.example.lesson24.adapters.PostRecyclerAdapter
import com.example.lesson24.R
import com.example.lesson24.Builders.SelectBuilder

class PostsActivity : AppCompatActivity() {

    private lateinit var statisticBtn: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAll()
    }

    private fun initAll() {
        recyclerView = findViewById(R.id.post_recycler)
        statisticBtn = findViewById(R.id.statistic_btn)
    }

    override fun onStart() {
        super.onStart()
        val listOfMainScreenPost = createList()
        with(recyclerView) {
            adapter = PostRecyclerAdapter(listOfMainScreenPost) {
                val intent = Intent(this@PostsActivity, CurrentPostActivity::class.java)
                    .putExtra("ID", it.id)
                startActivity(intent)
            }
            layoutManager =
                LinearLayoutManager(this@PostsActivity, LinearLayoutManager.VERTICAL, false)
        }
        statisticBtnListener()
    }

    private fun createList(): List<MainSceenPost> {
        val list = mutableListOf<MainSceenPost>()
        val cursor =
            SelectBuilder().selectParams("post._id, post.title, post.body, post.userId, user.email")
                .nameOfTable("post, user")
                .addOrderByArgs("post.title")
                .where("user._id = post.userId")
                .select(App.INSTANCE.getDb())
        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndexOrThrow("_id")
            val title = cursor.getColumnIndexOrThrow("title")
            val body = cursor.getColumnIndexOrThrow("body")
            val userId = cursor.getColumnIndexOrThrow("userId")
            val userEmail = cursor.getColumnIndexOrThrow("email")
            do {
                val postId = cursor.getInt(id)
                val postTitle = cursor.getString(title)
                val postBody = cursor.getString(body)
                val postUserId = cursor.getInt(userId)
                val postUserEmail = cursor.getString(userEmail)
                list.add(MainSceenPost(postId, postUserId, postTitle, postBody, postUserEmail))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    private fun statisticBtnListener() {
        statisticBtn.setOnClickListener {
            goToStatisticScreen()
        }
    }

    private fun goToStatisticScreen() {
        startActivity(Intent(this, StatisticScreenActivity::class.java))
    }
}