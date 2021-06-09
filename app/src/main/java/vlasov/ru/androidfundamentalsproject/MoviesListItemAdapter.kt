package vlasov.ru.androidfundamentalsproject

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class MoviesListItemAdapter(private var onClickListener : MovieInListOnClickListener? = null) : RecyclerView.Adapter<MoviesListItemAdapter.ViewHolder>() {
    private val movies : MutableList<MovieDetails> = mutableListOf()
    interface MovieInListOnClickListener{
        fun onMovieClick(movie: MovieDetails)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_in_list,parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.movieDuration?.text = item.duration
        holder.movieName?.text = item.name
        holder.ageLimit?.text = item.ageLimit
        holder.categories?.text = item.categories
        holder.numOfReviews?.text = item.numOfReviews
        holder.movieImage?.background = item.movieIconSrc
        holder.itemView.setOnClickListener{
            onClickListener?.onMovieClick(item)
        }
    }
    fun addMovie(movie : MovieDetails) {
        movies.add(movie)
        notifyItemChanged(movies.size)
    }
    fun addTestMovie(context : Context){
        addMovie(
                context.let { it1 ->
            AppCompatResources.getDrawable(it1,R.drawable.avengers_poster)?.let {
                MovieDetails(context.getString(R.string.avengers_storyline),
                        context.getString(R.string.avengers_name),
                        context.getString(R.string.avengers_age_limit),
                        context.getString(R.string.avengers_num_of_reviews),
                        context.getString(R.string.avengers_categories),
                        mutableListOf(), context.getString(R.string.avengers_duration),
                        it
                )
            }
        }!!)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var categories : TextView? = null
        var ageLimit : TextView? = null
        var numOfReviews : TextView? = null
        var movieName : TextView? = null
        var movieDuration :  TextView? = null
        var movieImage : ImageView? = null
        init {
            categories = itemView.findViewById(R.id.movieInListCategories)
            ageLimit = itemView.findViewById(R.id.movieInListAgeLimit)
            numOfReviews = itemView.findViewById(R.id.movieInListNumOfReviews)
            movieName = itemView.findViewById(R.id.movieInListName)
            movieDuration = itemView.findViewById(R.id.movieInListDuration)
            movieImage = itemView.findViewById(R.id.movieIconInList)
        }

    }

}