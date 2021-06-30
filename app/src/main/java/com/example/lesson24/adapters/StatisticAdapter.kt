package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.R
import com.example.lesson24.models.StatisticScreenModel

class StatisticAdapter(private val list: List<StatisticScreenModel>): RecyclerView.Adapter<StatisticAdapter.ViewHolder>() {

    class ViewHolder(item:View): RecyclerView.ViewHolder(item) {
        val title = item.findViewById<TextView>(R.id.post_statistic)
        val countOfComments = item.findViewById<TextView>(R.id.count_of_comments)
        val rateOfComments = item.findViewById<TextView>(R.id.rate_of_comments)

        fun bind(statisticScreenModel: StatisticScreenModel){
            title.text = statisticScreenModel.title
            countOfComments.append(" ${statisticScreenModel.countOfPosts}")
            rateOfComments.append(" ${statisticScreenModel.rateOfComments}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.statisctic_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}