package vlasov.ru.androidfundamentalsproject.features.moviedetails


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.models.Actor


class MovieCastAdapter() : ListAdapter<Actor, MovieCastAdapter.ViewHolder >(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_cast_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView? = null
        var name : TextView? = null
        init{
            image = itemView.findViewById(R.id.actorIcon)
            name = itemView.findViewById(R.id.actorName)
        }

        fun bind(actor : Actor) {
            image?.load(actor.imageUrl)
            name?.text = actor.name
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Actor>(){
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean = oldItem.name == newItem.name
    }


}