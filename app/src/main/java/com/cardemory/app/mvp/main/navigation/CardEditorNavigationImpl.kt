package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardeditor.navigation.CardEditorNavigation
import ru.terrakok.cicerone.Router

class CardEditorNavigationImpl(
    private val router: Router
) : CardEditorNavigation {

    override fun closeScreen() {
        router.exit()
    }
}