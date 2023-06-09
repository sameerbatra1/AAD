package com.example.exampractice

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.adapters.MyBlogAdapter
import com.example.exampractice.contract.Blog
import com.example.exampractice.contract.Response
import com.example.exampractice.databinding.ActivityViewMyBlogsBinding
import com.example.exampractice.network.IRequestContract
import com.example.exampractice.network.NetworkClient
import com.example.exampractice.utils.Constant
import com.example.exampractice.utils.DataProvider
import com.example.exampractice.utils.showToast
import retrofit2.Call
import retrofit2.Callback

class ViewMyBlogsActivity : AppCompatActivity() {
    private lateinit var adapter: MyBlogAdapter
    private lateinit var dataSource: MutableList<Blog>
    private lateinit var context: Context
    private lateinit var activity: Activity
    private lateinit var add: Button
    private lateinit var noBlogAvailable: TextView
    private lateinit var rvMyBlogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_my_blogs)

        context = this
        activity = this
        dataSource = DataProvider.response.myBlogs

//        add = findViewById(R.id.add)
        noBlogAvailable = findViewById(R.id.noBlogAvailable)
        rvMyBlogs = findViewById(R.id.rvMyBlogs)

        if (dataSource.size > 0) {
            adapter = MyBlogAdapter(activity, context, dataSource)

            noBlogAvailable.visibility = View.INVISIBLE
            rvMyBlogs.visibility = View.VISIBLE
            rvMyBlogs.adapter = adapter
        } else {
            noBlogAvailable.visibility = View.VISIBLE
            rvMyBlogs.visibility = View.INVISIBLE
        }

//        add.setOnClickListener {
////            Intent(this, AddOrUpdateBlogActivity::class.java).apply {
////                putExtra(Constant.KEY_REASON, 1) // 1 Means Add
////                startActivity(this)
//            showToast("Add button pressed")
//            }
        }
    }
