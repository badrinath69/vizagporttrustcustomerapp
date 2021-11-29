package com.example.selltez.model

import com.example.selltez.model.NotificationDetails
import com.google.gson.annotations.SerializedName

data class NotificatonResponse(
    @SerializedName("Details") val Details : List<NotificationDetails>,
    @SerializedName("message") val message : String
)
