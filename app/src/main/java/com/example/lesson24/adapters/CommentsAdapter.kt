package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.App
import com.example.lesson24.Builders.SelectBuilder
import com.example.lesson24.R
import com.example.lesson24.models.CommentsScreenModel

class CommentsAdapter(
    private val list: MutableList<CommentsScreenModel>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(
        item: View
    ) : RecyclerView.ViewHolder(item) {
        private val commentText = item.findViewById<TextView>(R.id.current_comment)
        private val thisEmail = item.findViewById<TextView>(R.id.email_user_of_comments)
        private val addCommentBtn = item.findViewById<ImageButton>(R.id.add_rate_btn)
        private val downCommentBtn = item.findViewById<ImageButton>(R.id.down_rate_btn)
        private val rateText = item.findViewById<TextView>(R.id.rate_text)

        fun bind(commentsScreenModel: CommentsScreenModel) {
            commentText.text = commentsScreenModel.textComments
            thisEmail.text = commentsScreenModel.email
            rateText.text = commentsScreenModel.rate.toString()
            addCommentBtn.setOnClickListener {
                App().getDb().compileStatement(
                    "UPDATE comments SET rate = rate+1 WHERE _id =${commentsScreenModel.id}"
                ).execute()
                getRateCount(rateText, commentsScreenModel.id)
            }
            downCommentBtn.setOnClickListener {
                App().getDb().compileStatement(
                    "UPDATE comments SET rate = rate-1 WHERE _id =${commentsScreenModel.id}"
                ).execute()
                getRateCount(rateText, commentsScreenModel.id)
            }
        }

        private fun getRateCount(textView: TextView, id: Int) {
            val b = SelectBuilder().nameOfTable("comments")
                .selectParams("rate")
                .where("_id =${id}")
                .select(App.INSTANCE.getDb())
            if (b.moveToFirst()) {
                val index = b.getColumnIndexOrThrow("rate")
                do {
                    val rate = b.getInt(index)
                    textView.text = rate.toString()
                } while (b.moveToNext())
            }
            b.close()
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