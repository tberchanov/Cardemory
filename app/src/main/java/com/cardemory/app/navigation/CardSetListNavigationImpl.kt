package com.cardemory.app.navigation

import com.cardemory.card_set_editor.navigation.CardSetEditorScreen
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.navigation.CardListScreen
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.cardsetlist.navigation.PrivacyPolicyScreen
import ru.terrakok.cicerone.Router

class CardSetListNavigationImpl(
    private val router: Router
) : CardSetListNavigation {

    override fun showPrivacyPolicyScreen() {
        router.navigateTo(PrivacyPolicyScreen())
    }

    override fun showCreateCardSetScreen() {
        router.navigateTo(CardSetEditorScreen())
    }

    override fun showEditCardSetScreen(cardSet: CardSet) {
        router.navigateTo(CardSetEditorScreen(cardSet))
    }

    override fun showCardsScreen(cardSet: CardSet) {
        router.navigateTo(CardListScreen(cardSet))
    }
}