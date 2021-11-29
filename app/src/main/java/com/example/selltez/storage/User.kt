package com.example.selltez.storage

import com.google.gson.annotations.SerializedName





data class User(

    @SerializedName("empid") val empid: String,
    @SerializedName("name") val name: String,
    @SerializedName("adhar") val adhar: String,
    @SerializedName("proof") val proof: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("password") val password: String,
    @SerializedName("status") val status: String

//    @SerializedName("empid") val empid: Int,
//@SerializedName("name") val name: String,
//@SerializedName("adhar") val adhar: Int,
//@SerializedName("proof") val proof: String,
//@SerializedName("mobile") val mobile: Int,
//@SerializedName("password") val password: String,
//@SerializedName("status") val status: Int
)

//private class TestCase {
//    @SerializedName("test")
//    private val field: String? = null
//}