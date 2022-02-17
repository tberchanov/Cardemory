package com.cardemory.card_set_editor.navigation

import com.cardemory.card_set_editor.mvp.CardSetEditorFragment
import com.cardemory.carddata.entity.CardSet
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardSetEditorScreen(
    private val cardSet: CardSet? = null
) : SupportAppScreen() {

    override fun getFragment() = CardSetEditorFragment.newInstance(cardSet)
}