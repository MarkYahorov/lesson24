package com.example.lesson24.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.Builders.SelectBuilder
import com.example.lesson24.R
import com.example.lesson24.adapters.StatisticAdapter
import com.example.lesson24.models.StatisticScreenModel

class StatisticScreenActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic_screen)

        recyclerView = findViewById(R.id.statistic_recycler)
    }

    override fun onStart() {
        super.onStart()
        createRecycler()
    }

    private fun createList():List<StatisticScreenModel>{
        val list = mutableListOf<StatisticScreenModel>()
        val cursor =
            SelectBuilder().selectParams("post.title")
                .selectParams("(SELECT count(*) FROM comments WHERE comments.postId = post._id) as commentCount")
                .selectParams("(SELECT avg(comments.rate)FROM comments WHERE comments.postId = post._id) as rateOfComm")
                .nameOfTable("post")
                .addOrderByArgs("(SELECT count(*) FROM comments WHERE comments.postId = post._id) DESC")
                .select(App.INSTANCE.getDb())
        if (cursor.moveToFirst()) {
            val title = cursor.getColumnIndexOrThrow("title")
            val commentCountIdColumn = cursor.getColumnIndexOrThrow("commentCount")
            val rateOfCommentIdColumn = cursor.getColumnIndexOrThrow("rateOfComm")
            do {
                val postTitle = cursor.getString(title)
                val commentCount = cursor.getInt(commentCountIdColumn)
                val rateOfComment = cursor.getInt(rateOfCommentIdColumn)
                list.add(StatisticScreenModel(postTitle, commentCount, rateOfComment))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    private fun createRecycler(){
        val listOfStatisticScreenModel = createList()
        with(recyclerView){
            adapter = StatisticAdapter(listOfStatisticScreenModel)
            layoutManager = LinearLayoutManager(this@StatisticScreenActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}