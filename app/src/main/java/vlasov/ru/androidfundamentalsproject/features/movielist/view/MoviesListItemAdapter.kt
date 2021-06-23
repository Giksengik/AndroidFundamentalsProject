package vlasov.ru.androidfundamentalsproject.features.movielist.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.models.Movie

class MoviesListItemAdapter(
    private var onClickListener : MovieInListOnClickListener? = null
) : ListAdapter<Movie, MoviesListItemAdapter.ViewHolder>(DiffCallback()) {

    interface MovieInListOnClickListener{
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_in_list,parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var categories : TextView? = null
        var ageLimit : TextView? = null
        var numOfReviews : TextView? = null
        var movieName : TextView? = null
        var movieDuration :  TextView? = null
        var movieImage : ImageView? = null
        private var starsImages: List<ImageView>? =  null
        init {
            categories = itemView.findViewById(R.id.movieInListCategories)
            ageLimit = itemView.findViewById(R.id.movieInListAgeLimit)
            numOfReviews = itemView.findViewById(R.id.movieInListNumOfReviews)
            movieName = itemView.findViewById(R.id.movieInListName)
            movieDuration = itemView.findViewById(R.id.movieInListDuration)
            movieImage = itemView.findViewById(R.id.movieIconInList)
            starsImages = listOf(
                    itemView.findViewById(R.id.star),
                    itemView.findViewById(R.id.star2),
                    itemView.findViewById(R.id.star3),
                    itemView.findViewById(R.id.star4),
                    itemView.findViewById(R.id.star5)
            )
        }
        @SuppressLint("SetTextI18n")
        fun bind(movie : Movie, onClickListener : MovieInListOnClickListener?) {
            movieDuration?.text = movie.getRunningTimeString()
            movieName?.text = movie.title
            ageLimit?.text = movie.pgAge.toString() + "+"
            categories?.text = movie.getGenresString()
            numOfReviews?.text = movie.reviewCount.toString() + " " + itemView.context.getString(R.string.reviews)
            movieImage?.load(movie.imageUrl)
            itemView.setOnClickListener{
                onClickListener?.onMovieClick(movie)
            }
            //set stars tint
            starsImages?.forEachIndexed { index, imageView ->
                val colorId = if (movie.rating / 2 > index) R.color.pink_light else R.color.gray_dark
                ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                        ContextCompat.getColor(imageView.context, colorId)
                )
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}