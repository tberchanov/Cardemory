package com.cardemory.app.mvp.main.navigation

import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import ru.terrakok.cicerone.Router

class CardSetEditorNavigationImpl(
    private val router: Router
) : CardSetEditorNavigation {

    override fun back() {
        router.exit()
    }
}