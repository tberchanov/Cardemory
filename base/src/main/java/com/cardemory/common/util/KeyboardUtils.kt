package com.cardemory.common.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.closeKeyboard() {
    val inputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showKeyboard() {
    requestFocus()
    val inputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
