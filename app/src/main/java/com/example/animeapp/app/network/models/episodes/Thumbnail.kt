import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("original") var original: String? = null,
)