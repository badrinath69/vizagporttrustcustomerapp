package com.example.selltez.model

import com.example.selltez.storage.ComplaintDetails
import com.google.gson.annotations.SerializedName

data class ComplaintInfoResponse(

    @SerializedName("Details") val Details : List<ComplaintDetails>,
    @SerializedName("message") val message : String)