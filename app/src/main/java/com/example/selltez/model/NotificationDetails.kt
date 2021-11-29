package com.example.selltez.model

import com.google.gson.annotations.SerializedName

data class NotificationDetails(
    @SerializedName("statusid") val statusid: String,
    @SerializedName("assignedto") val assignedto: String,
    @SerializedName("file") val file: String,
    @SerializedName("currentstatus") val currentstatus: String,
    @SerializedName("statusdesc") val statusdesc: String,
    @SerializedName("modifieddate") val modifieddate: String,
    @SerializedName("complaintid") val complaintid: String,
    @SerializedName("against") val against: String,
    @SerializedName("raiseddate") val raiseddate: String,


)
