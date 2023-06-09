package com.example.exampractice

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.exampractice.contract.Request
import com.example.exampractice.contract.Response
import com.example.exampractice.network.IRequestContract
import com.example.exampractice.network.NetworkClient
import com.example.exampractice.utils.Constant
import com.example.exampractice.utils.showToast
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), Callback<Response> {
    private lateinit var progressDialog: ProgressDialog
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequestContract::class.java)
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: String

    private lateinit var btnSignUp: Button
    private lateinit var edUserName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait...")
        progressDialog.setCancelable(true)

        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        checkIfUserAlreadyRegistered()

        btnSignUp = findViewById(R.id.btnSignUp)
        edUserName = findViewById(R.id.edUserName)

        btnSignUp.setOnClickListener {
            userName = edUserName.text.toString().trim().toUpperCase()
            if (userName.isNullOrEmpty()) {
                showToast("Please enter your name")
            } else {
                progressDialog.show()
                val request = Request(
                    action = Constant.REGISTER_USER,
                    userName = userName
                )
                val callResponse = requestContract.makeApiCall(request)
                callResponse.enqueue(this)
            }
        }
    }

    override fun onFailure(call: Call<Response>, t: Throwable) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
        showToast("Server is not responding. Please contact your system administrator")
        edUserName.setText("")
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()

        if (response.body() != null) {
            val serverResponse = response.body()
            if (serverResponse!!.status) {
                saveUserToPref(serverResponse.userId, userName)
                Intent(this, HomeActivity::class.java).apply {
                    //putExtra(Constant.KEY_USER_ID,serverResponse.userId)
                    //putExtra(Constant.KEY_USER_NAME,userName)
                    startActivity(this)
                    finish()
                }
            } else {
                showToast(serverResponse.message)
                edUserName.setText("")
            }
        } else {
            showToast("Server is not responding. Please contact your system administrator")
            edUserName.setText("")
        }
    }

    private fun saveUserToPref(userId: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constant.KEY_USER_ID, userId)
        editor.putString(Constant.KEY_USER_NAME, userName)
        editor.commit()
    }

    private fun checkIfUserAlreadyRegistered() {
        val userId = sharedPreferences.getString(Constant.KEY_USER_ID, "invalid user id")
        val userName = sharedPreferences.getString(Constant.KEY_USER_NAME, "invalid user name")

        if (!userId.contentEquals("invalid user id")
            && !userName.contentEquals("invalid user name")
        ) {
            Intent(this, HomeActivity::class.java).apply {
                putExtra(Constant.KEY_USER_ID, userId)
                putExtra(Constant.KEY_USER_NAME, userName)
                startActivity(this)
                finish()
            }
        }
    }
}
