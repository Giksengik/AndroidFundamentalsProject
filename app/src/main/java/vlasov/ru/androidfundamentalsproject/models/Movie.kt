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
        val rating: Int,
        val imageUrl: String,
        val detailImageUrl: String,
        val storyLine: String,
        val actors: List<Actor>,
) : Serializable {
    fun getGenres(): String {
        val str = StringBuilder()
        for (item in genres) {
            str.append(item.name)
            str.append(", ")
        }
        return str.toString()
    }
}

