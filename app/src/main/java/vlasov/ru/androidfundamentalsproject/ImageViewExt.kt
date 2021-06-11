package vlasov.ru.androidfundamentalsproject

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(src : String) {
    Glide
        .with(this.context)
        .load(src)
        .into(this)
}