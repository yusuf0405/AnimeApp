package com.example.animeapp.screen_home.domain.models

import org.ocpsoft.prettytime.PrettyTime
import java.io.Serializable
import java.util.*

data class Anime(
    var animeId: String,
    var attributes: Attributes?,
    var createdAt: Date? = null,
    var objectId: String? = null,
    var type: String? = null,
    var updatedAt: String? = null,
) : Serializable {

    fun getCreatedAt(): String? {
        val prettyTime = PrettyTime(Locale("en"))
        return prettyTime.format(createdAt)
    }
}

