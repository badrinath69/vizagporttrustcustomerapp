package com.example.selltez.model

import com.example.selltez.storage.AccountDetails
import com.google.gson.annotations.SerializedName

data class AccountResponse(

    @SerializedName("Details") val Details : List<AccountDetails>,
    @SerializedName("message") val message : String)