package vlasov.ru.androidfundamentalsproject

import android.graphics.drawable.Drawable
import java.io.Serializable

data class MovieDetails(val description : String, val name: String, val ageLimit: String, val numOfReviews : String,
                        val categories : String, val cast : List<MovieCastItem> , val duration : String,
                        val movieIconSrc : Drawable) : Serializable{

}