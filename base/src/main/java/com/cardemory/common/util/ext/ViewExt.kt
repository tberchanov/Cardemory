package com.cardemory.common.util.ext

import android.view.View

fun View.setVisible(visible: Boolean, gone: Boolean = false) {
    visibility = when {
        visible -> View.VISIBLE
        gone -> View.GONE
        else -> View.INVISIBLE
    }
}