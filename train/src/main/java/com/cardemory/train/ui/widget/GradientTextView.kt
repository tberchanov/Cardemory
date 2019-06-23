package com.cardemory.train.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.TextView
import com.cardemory.train.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var textGradient: LinearGradient? = null

    var gradientEnabled = false

    init {
        if (attrs != null) {
            setupAttributes(attrs)
        }
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GradientTextView,
            defStyleAttr, 0
        )
        with(typedArray) {
            gradientEnabled = getBoolean(R.styleable.GradientTextView_gtvEnableGradient, false)
            val startColor = getColor(R.styleable.GradientTextView_gtvStartColor, Color.BLACK)
            val endColor = getColor(R.styleable.GradientTextView_gtvEndColor, Color.BLACK)
            val gradientHeight = getDimension(R.styleable.GradientTextView_gtvGradientHeight, 0f)
            recycle()
            textGradient = LinearGradient(
                0f, 0f, 0f, gradientHeight,
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && gradientEnabled) {
            paint.shader = textGradient
        }
    }
}
