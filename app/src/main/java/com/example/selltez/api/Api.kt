package com.example.selltez.api

import com.example.selltez.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api{

    @Multipart
    @POST("signup")
    fun createUser(
        @Part("name") name:RequestBody,
        @Part("adhar") adhar: RequestBody,
        @Part proof: MultipartBody.Part,
        @Part("mobile") mobile: RequestBody,
        @Part("password") password: RequestBody

//        @Field("name") name:String,
//        @Field("adhar") adhar: String,
//        @Part proof: MultipartBody.Part,
//        @Field("mobile") mobile: String,
//        @Field("password") password: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("signin")
    fun userLogin(
        @Field("mobile") mobile:String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("complaintinfodisplay")
    fun complaintposts(
        @Field("empid") empid:String
    ):Call<ComplaintInfoResponse>

    @FormUrlEncoded
    @POST("empinfodisplay")
    fun accountDetails(
        @Field("empid") empid:String
    ):Call<AccountResponse>


    @FormUrlEncoded
    @POST("updatepassword")
    fun changePassword(
        @Field("empid") empid:String,
        @Field("password") password: String
    ):Call<ChangePasswordResponse>


    @FormUrlEncoded
    @POST("complaintstatus")
    fun notificationStatus(
        @Field("empid") empid:String
    ):Call<NotificatonResponse>

    @Multipart
    @POST("complaint")
    fun userComplaint(
        @Part("against") against:RequestBody,
        @Part("description") description:RequestBody,
        @Part file: MultipartBody.Part,
        @Part("empid") empid:RequestBody,

    ):Call<ComplaintResponse>

    @FormUrlEncoded
    @POST("forgotpass")
    fun forgotPassword(
        @Field("mobile") mobile:String,
        @Field("password") password:String
    ):Call<Passwordresponse>





}