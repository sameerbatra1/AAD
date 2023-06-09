package com.example.exampractice.utils

import com.example.exampractice.contract.Blog
import com.example.exampractice.contract.Response

object DataProvider {
    var response:Response = Response()
    var blog: Blog = Blog()
    lateinit var userId:String
    lateinit var userName:String
}
