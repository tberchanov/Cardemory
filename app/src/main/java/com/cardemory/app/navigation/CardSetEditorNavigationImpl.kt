package com.cardemory.app.navigation

import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.common.navigation.AppRouter

class CardSetEditorNavigationImpl(
    private val router: AppRouter
) : CardSetEditorNavigation {

    override fun closeScreen() {
        router.exit()
    }

    override fun showSelectFileScreen(requestCode: Int, mimeTypeFilter: String?) {
        router.chooseFileForResult(requestCode, mimeTypeFilter)
    }
}
