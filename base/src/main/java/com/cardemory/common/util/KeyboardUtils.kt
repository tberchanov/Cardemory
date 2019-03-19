package com.cardemory.common.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard() {
    requestFocus()
    val imm = getInputMethodManager()
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputManager = getInputMethodManager()
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun Activity.hideKeyboard() {
    currentFocus?.also {
        it.hideKeyboard()
    }
}

private fun View.getInputMethodManager(): InputMethodManager {
    return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}

