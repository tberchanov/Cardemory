package com.cardemory.common.util.ext

import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

fun Fragment.getDimen(@DimenRes dimenRes: Int) = requireContext().getDimen(dimenRes)
