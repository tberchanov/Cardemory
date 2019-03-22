package com.cardemory.app.mvp.main.navigation

import com.cardemory.card_set_editor.navigation.CardSetEditorScreen
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import ru.terrakok.cicerone.Router

class CardSetListNavigationImpl(
    private val router: Router
) : CardSetListNavigation {

    override fun showCreateCardSetScreen() {
        router.navigateTo(CardSetEditorScreen())
    }

    override fun showEditCardSetScreen(cardSet: CardSet) {
        router.navigateTo(CardSetEditorScreen(cardSet))
    }
}