package test.shuhrat.devexam.data.repository.network

import retrofit2.Response
import retrofit2.http.*
import test.shuhrat.devexam.data.pojos.AuthResponseStatus
import test.shuhrat.devexam.data.pojos.ItemModel
import test.shuhrat.devexam.data.pojos.PhoneMaskModel

interface DevExamInternetService {
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/api/v1/auth")
    suspend fun checkAuth(@Field("phone") phone : String, @Field("password") password : String): Response<AuthResponseStatus>

    @GET("/api/v1/posts")
    suspend fun getPosts(): Response<List<ItemModel>>

    @GET("/api/v1/phone_masks")
    suspend fun getPhoneMask(): Response<PhoneMaskModel>

}