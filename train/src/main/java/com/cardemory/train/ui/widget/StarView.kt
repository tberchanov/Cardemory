package com.cardemory.train.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.cardemory.train.R

class StarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        repeat(STARS_COUNT) {
            this.addView(ImageView(context))
        }
    }

    fun setStarState(starPosition: Int, starState: StarState) {
        val starImageView = getChildAt(starPosition)
        if (starImageView is ImageView) {
            starImageView.setImageResource(getStarImageRes(starState))
        }
    }

    @DrawableRes
    private fun getStarImageRes(starState: StarState) =
        when (starState) {
            StarState.OUTLINE -> R.drawable.star_outline
            StarState.HALF -> R.drawable.star_half
            StarState.FULL -> R.drawable.star
        }

    companion object {
        const val STARS_COUNT = 3
    }
}