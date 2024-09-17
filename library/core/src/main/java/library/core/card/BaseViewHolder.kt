package library.core.card

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

import android.view.View

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val resources: Resources
        get() = itemView.resources
}
