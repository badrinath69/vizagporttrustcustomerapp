package com.example.selltez.storage

import com.google.gson.annotations.SerializedName

data class AccountDetails(

    @SerializedName("empid") val empid: String,
    @SerializedName("name") val name: String,
    @SerializedName("adhar") val adhar: String,
    @SerializedName("proof") val proof: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("password") val password: String,
    @SerializedName("status") val status: String

)
