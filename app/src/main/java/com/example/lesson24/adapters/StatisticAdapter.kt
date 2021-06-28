package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.R
import com.example.lesson24.models.StatisticScreenModel

class StatisticAdapter(private val list: MutableList<StatisticScreenModel>): RecyclerView.Adapter<StatisticAdapter.ViewHolder>() {

    class ViewHolder(item:View): RecyclerView.ViewHolder(item) {
        val title = item.findViewById<TextView>(R.id.post_statistic)
        val countOfComments = item.findViewById<TextView>(R.id.count_of_comments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.statisctic_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = list.size
}