package com.example.selltez.storage

import com.google.gson.annotations.SerializedName

data class ComplaintDetails(

    @SerializedName("complaintid") val complaintid: String,
    @SerializedName("against") val against: String,
    @SerializedName("raiseddate") val raiseddate: String,
    @SerializedName("description") val description: String,
    @SerializedName("file") val file: String,
    @SerializedName("empid") val empid: String

)
