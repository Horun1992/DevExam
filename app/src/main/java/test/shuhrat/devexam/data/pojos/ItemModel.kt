package test.shuhrat.devexam.data.pojos


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class ItemModel(
    @Json(name = "id") val id: String? = "", // 0cc57f3b-c456-45ba-a71d-220b777bad07
    @Json(name = "date") val date: String? = "", // 2019-02-08T15:27:39Z
    @Json(name = "image") val image: String? = "", // /uploads/post/image/0cc57f3b-c456-45ba-a71d-220b777bad07/thumb_7_image_9.jpg
    @Json(name = "sort") val sort: Int = 0, // 7
    @Json(name = "text") val text: String? = "", // Whether a gesture’s charming or alarming, depends on how it’s received.
    @Json(name = "title") val title: String? = "" // Wait For it
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(date)
        parcel.writeString(image)
        parcel.writeInt(sort)
        parcel.writeString(text)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}