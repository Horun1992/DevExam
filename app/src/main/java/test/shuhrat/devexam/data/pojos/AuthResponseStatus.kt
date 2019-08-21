package test.shuhrat.devexam.data.pojos

import com.squareup.moshi.Json

data class AuthResponseStatus(
    @Json(name = "success") val success: Boolean)
