package vlasov.ru.androidfundamentalsproject.data.locale.room

import androidx.room.Entity
import vlasov.ru.androidfundamentalsproject.models.Actor
import vlasov.ru.androidfundamentalsproject.models.Genre

@Entity(
    tableName = "Movie",
    primaryKeys = ["id"]
)
data class MovieDB(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Double,
    val imageUrl: String?,
    val detailImageUrl: String?,
    val storyLine: String
)
