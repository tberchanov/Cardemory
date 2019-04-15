package com.cardemory.cardsetlist.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.cardemory.cardsetlist.R


class CardSetView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val cardSetCardsMargins = resources.getDimension(CARDSET_CARDS_MARGINS_RES).toInt()
    private val cardSetTextStartMargin =
        resources.getDimension(CARDSET_TEXT_START_MARGIN_RES).toInt()
    private val cardSetCardsElevation = resources.getDimension(CARDSET_CARDS_ELEVATION_RES)
    private val cardSetCardsCornerRadius = resources.getDimension(CARDSET_CARDS_CORNER_RADIUS_RES)
    private val cardSetTextSize = resources.getDimension(CARDSET_TEXT_SIZE_RES)
    private val whiteColor = ContextCompat.getColor(context, R.color.white)
    private val blackColor = ContextCompat.getColor(context, R.color.black)

    private var textView: TextView? = null

    fun setCardsCount(cardsCount: Int) {
        val sizeDelta = (cardSetCardsMargins * 1.5).toInt() * cardsCount
        val cardWidth = width - sizeDelta
        val cardHeight = height - sizeDelta
        repeat(cardsCount) { counter ->
            val card = CardView(context)
            setupCardView(
                card,
                counter,
                cardWidth,
                cardHeight,
                counter
            )
            if (counter == cardsCount - 1) {
                setupLastCardView(card)
            }
            addView(card)
        }
    }

    fun setText(text: String) {
        textView?.text = text
    }

    private fun setupLastCardView(card: CardView) {
        textView = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, cardSetTextSize)
            setTextColor(blackColor)
        }
        textView?.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.START or Gravity.CENTER_VERTICAL
        ).apply { marginStart = cardSetTextStartMargin }
        card.addView(textView)
    }

    private fun setupCardView(
        card: CardView,
        cardZ: Int,
        cardWidth: Int,
        cardHeight: Int,
        number: Int
    ) {
        val cardLayoutParams = LayoutParams(
            cardWidth, cardHeight,
            Gravity.BOTTOM or Gravity.END
        ).also {
            val margin = cardSetCardsMargins * (number + 1)
            it.setMargins(0, 0, margin, margin)
        }
        card.apply {
            setCardBackgroundColor(whiteColor)
            z = cardZ.toFloat()
            cardElevation = cardSetCardsElevation
            radius = cardSetCardsCornerRadius
            layoutParams = cardLayoutParams
        }
    }

    companion object {
        @DimenRes
        private val CARDSET_CARDS_MARGINS_RES = R.dimen.cardset_cards_margins
        @DimenRes
        private val CARDSET_CARDS_ELEVATION_RES = R.dimen.cardset_cards_elevation
        @DimenRes
        private val CARDSET_CARDS_CORNER_RADIUS_RES = R.dimen.cardset_cards_corner_radius
        @DimenRes
        private val CARDSET_TEXT_START_MARGIN_RES = R.dimen.cardset_text_start_margin
        @DimenRes
        private val CARDSET_TEXT_SIZE_RES = R.dimen.cardset_text_size
    }
}