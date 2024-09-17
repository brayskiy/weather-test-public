package feature.weather.card.base

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

import android.view.View
import feature.weather.card.CardType
import library.core.card.CardHandlerProvider

abstract class BaseCard<T1, T2, T3 : RecyclerView.ViewHolder>(
    @param:LayoutRes val layoutId: Int, data: T1,
    private val cardHandlerProvider: CardHandlerProvider<T2>
) {
    var cardData: T1 internal set

    var viewHolder: T3? = null

    abstract val cardType: CardType

    val cardHandler: T2 get() = cardHandlerProvider.cardHandler

    abstract fun onCreateViewHolder(view: View): T3

    abstract fun updateCardViews()

    abstract fun onViewRecycled(viewHolder: RecyclerView.ViewHolder)

    init {
        cardData = data
    }

    fun onBindViewHolder(holder: T3) {
        viewHolder = holder
        updateCardViews()
    }
}
