package com.example.selltez.storage

import com.google.gson.annotations.SerializedName

interface Communicator {
    fun passData(datetime: String,againstname: String,descriptionn: String,imageuurl : String)

}

interface Communicator2{
    fun passNum(number: String)
}