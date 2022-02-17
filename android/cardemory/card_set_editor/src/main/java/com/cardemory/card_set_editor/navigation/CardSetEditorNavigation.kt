package com.cardemory.card_set_editor.navigation

interface CardSetEditorNavigation {

    fun closeScreen()

    fun showSelectFileScreen(requestCode: Int, mimeTypeFilter: String? = null)
}