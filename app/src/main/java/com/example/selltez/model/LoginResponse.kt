package com.example.selltez.model

import com.example.selltez.storage.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("userdata") val userdata : List<User>,
                         @SerializedName("message") val message : String)
