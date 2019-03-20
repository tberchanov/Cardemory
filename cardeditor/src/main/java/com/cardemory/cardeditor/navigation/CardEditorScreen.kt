package com.cardemory.cardeditor.navigation

import com.cardemory.carddata.entity.Card
import com.cardemory.cardeditor.mvp.CardEditorFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardEditorScreen(
    private val card: Card? = null
) : SupportAppScreen() {

    override fun getFragment() = card?.let {
        CardEditorFragment.newInstance(it)
    } ?: CardEditorFragment.newInstance()
}