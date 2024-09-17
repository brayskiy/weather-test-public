package library.core.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.net.URL
import javax.inject.Inject


class ImagesLoader @Inject constructor() {

    fun loadImageCircle(context: Context, url: URL, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}
