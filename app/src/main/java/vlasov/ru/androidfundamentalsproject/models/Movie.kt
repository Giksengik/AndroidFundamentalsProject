package vlasov.ru.androidfundamentalsproject.models

import java.io.Serializable
import java.lang.StringBuilder

data class Movie(
        val id: Int,
        val pgAge: Int,
        val title: String,
        @JvmField val genres: List<Genre>,
        val runningTime: Int,
        val reviewCount: Int,
        val isLiked: Boolean,
        val rating: Double,
        val imageUrl: String?,
        val detailImageUrl: String?,
        val storyLine: String,
        val actors: List<Actor>
) : Serializable {
    fun getGenresString(): String {
        val str = StringBuilder()
        for (item in genres) {
            if(str.isNotEmpty())
                str.append(", ")
            str.append(item.name)
        }
        return str.toString()
    }
    fun getRunningTimeString(): String {
        return "${runningTime / 60}:${runningTime % 60}h"
    }
}

