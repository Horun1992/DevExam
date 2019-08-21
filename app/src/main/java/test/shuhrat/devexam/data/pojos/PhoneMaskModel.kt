package test.shuhrat.devexam.data.pojos


import com.squareup.moshi.Json


data class PhoneMaskModel(
    @Json(name = "phoneMask") val phoneMask: String = "" // +7 (ХХХ) ХХХ-ХХ-ХХ
)