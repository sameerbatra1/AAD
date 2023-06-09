package com.example.exampractice.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.R
import com.example.exampractice.contract.Blog

class AllBlogAdapter(private val dataSource: List<Blog>) : RecyclerView.Adapter<AllBlogAdapter.AllBlogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBlogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.it_all_blog, parent, false)
        return AllBlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: AllBlogViewHolder, position: Int) {
        val blog = dataSource[position]
        holder.bind(blog)
    }

    class AllBlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val blogger: TextView = itemView.findViewById(R.id.blogger)
        private val dateTime: TextView = itemView.findViewById(R.id.dateTime)

        fun bind(blog: Blog) {
            title.text = blog.title
            description.text = blog.description
            blogger.text = blog.bloggerName
            dateTime.text = blog.dateTime
        }
    }
}
