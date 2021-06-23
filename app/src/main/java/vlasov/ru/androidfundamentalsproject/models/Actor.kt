package vlasov.ru.androidfundamentalsproject.models

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "actors",)
data class Actor(
    val id: Int,
    val name: String,
    val imageUrl: String?
) : Serializable
