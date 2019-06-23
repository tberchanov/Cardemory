package com.cardemory.common.util

import android.view.View

fun View.setVisible(visible: Boolean, gone: Boolean = false) {
    visibility = when {
        visible -> View.VISIBLE
        gone -> View.GONE
        else -> View.INVISIBLE
    }
}