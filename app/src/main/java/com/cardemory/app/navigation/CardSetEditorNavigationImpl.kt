package com.cardemory.app.navigation

import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import ru.terrakok.cicerone.Router

class CardSetEditorNavigationImpl(
    private val router: Router
) : CardSetEditorNavigation {

    override fun closeScreen() {
        router.exit()
    }
}