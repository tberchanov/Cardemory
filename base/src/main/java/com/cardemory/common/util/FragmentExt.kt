package com.cardemory.common.util

import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

fun Fragment.getDimen(@DimenRes dimenRes: Int) = resources.getDimension(dimenRes)