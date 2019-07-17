package com.cardemory.common.util.ext

import android.content.Context
import androidx.annotation.DimenRes

fun Context.getDimen(@DimenRes dimenRes: Int) = resources.getDimension(dimenRes)
