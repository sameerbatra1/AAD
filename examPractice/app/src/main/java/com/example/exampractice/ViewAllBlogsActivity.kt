package com.example.exampractice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.R
import com.example.exampractice.adapters.AllBlogAdapter
//import com.example.exampractice.adapters.AllBlogAdapter
import com.example.exampractice.contract.Blog
import com.example.exampractice.utils.DataProvider

class ViewAllBlogsActivity : AppCompatActivity() {

    private lateinit var adapter: AllBlogAdapter
    private lateinit var dataSource: MutableList<Blog>
    private lateinit var context: Context
    private lateinit var rvAllBlogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_blogs)

        context = this
        dataSource = DataProvider.response.allBlogs
        adapter = AllBlogAdapter(dataSource)
        rvAllBlogs = findViewById(R.id.rvAllBlogs)
        rvAllBlogs.adapter = adapter
    }
}
