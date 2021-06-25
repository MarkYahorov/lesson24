package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.R
import com.example.lesson24.models.CommentsScreenModel

class CommentsAdapter(private val list:MutableList<CommentsScreenModel>): RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(item:View): RecyclerView.ViewHolder(item) {
        val commentText = item.findViewById<TextView>(R.id.current_comment)
        val thisEmail = item.findViewById<TextView>(R.id.email_user_of_comments)

        fun bind(commentsScreenModel: CommentsScreenModel){
            commentText.text = commentsScreenModel.textComments
            thisEmail.text =  commentsScreenModel.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_comments_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}