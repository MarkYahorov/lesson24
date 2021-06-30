package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.R
import com.example.lesson24.models.MainSceenPost

class PostRecyclerAdapter(
    private val list: List<MainSceenPost>,
    private val goToCurrentPostActivity: (MainSceenPost) -> Unit
) :
    RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        item: View,
        private val goToCurrentPostActivity: (MainSceenPost) -> Unit
    ) : RecyclerView.ViewHolder(item) {
        private val title = item.findViewById<TextView>(R.id.title_text_recycler)
        private val email = item.findViewById<TextView>(R.id.email_recycler)
        private val body = item.findViewById<TextView>(R.id.body_recycler)
        private val itemRecyclerView = item.findViewById<View>(R.id.item_recycler)

        fun bind(post: MainSceenPost) {
            title.text = post.title
            body.text = post.body
            email.text = post.email
            itemRecyclerView.setOnClickListener {
                goToCurrentPostActivity(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_post_item, parent, false)
        return ViewHolder(view, goToCurrentPostActivity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}