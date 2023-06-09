package com.example.exampractice.adapters

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.R
import com.example.exampractice.contract.Blog
import com.example.exampractice.contract.Request
import com.example.exampractice.contract.Response
import com.example.exampractice.databinding.ItMyBlogBinding
import com.example.exampractice.network.IRequestContract
import com.example.exampractice.network.NetworkClient
import com.example.exampractice.utils.Constant
import com.example.exampractice.utils.DataProvider
import com.example.exampractice.utils.showToast
import retrofit2.Call
import retrofit2.Callback

class MyBlogAdapter(
    private val activity: Activity,
    private val context: Context,
    private val dataSource: MutableList<Blog>
) : RecyclerView.Adapter<MyBlogAdapter.MyBlogViewHolder>(), Callback<Response> {

    private val progressDialog: ProgressDialog = ProgressDialog(context)
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequestContract::class.java)
    private lateinit var deletedBlog: Blog
    private var deletedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBlogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItMyBlogBinding.inflate(inflater, parent, false)
        return MyBlogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: MyBlogViewHolder, position: Int) {
        val blog = dataSource[position]
        holder.bind(blog)

//        holder.binding.btnEdit.setOnClickListener {
//            // Intent(context, AddOrUpdateBlogActivity::class.java).apply {
//            //     DataProvider.blog = blog
//            //     putExtra(Constant.KEY_REASON, 2)  //2 Mean Edit
//            //     activity.startActivity(this)
//            // }
//        }

//        holder.binding.btnDelete.setOnClickListener {
//            AlertDialog.Builder(context)
//                .setTitle("Blog App Alert")
//                .setMessage("Are you sure? You want to delete this Blog")
//                .setPositiveButton("Yes") { dialog, which ->
//                    progressDialog.setMessage("Please wait...")
//                    progressDialog.setCancelable(false)
//                    deletedBlog = blog
//                    deletedPosition = position
//                    val request = Request(
//                        action = Constant.DELETE_BLOG,
//                        userId = DataProvider.userId,
//                        blogId = blog.blogId
//                    )
//                    progressDialog.show()
//                    val callResponse = requestContract.makeApiCall(request)
//                    callResponse.enqueue(this)
//                }
//                .setNegativeButton("No") { dialog, which ->
//                    dialog?.dismiss()
//                }
//                .create()
//                .show()
//        }
    }

    inner class MyBlogViewHolder(val binding: ItMyBlogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Blog) {
            binding.title.text = blog.title
            binding.description.text = blog.description
            binding.dateTime.text = blog.dateTime
        }
    }

    override fun onFailure(call: Call<Response>, t: Throwable) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
        context.showToast("Server is not responding. Please contact your system administrator")
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
        if (response.body() != null) {
            val serverResponse = response.body()
            if (serverResponse!!.status) {
                dataSource.remove(deletedBlog)
                notifyItemRemoved(deletedPosition)
                notifyItemRangeChanged(deletedPosition, dataSource.size)
                context.showToast(serverResponse.message)
            } else {
                context.showToast(serverResponse.message)
            }
        } else {
            context.showToast("Server is not responding. Please contact your system administrator")
        }
    }
}
