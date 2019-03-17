package com.cardemory.cardeditor

import com.cardemory.carddata.entity.Card
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardEditorScreen(
    private val card: Card? = null
) : SupportAppScreen() {

    override fun getFragment() = card?.let {
        CardEditorFragment.newInstance(it)
    } ?: CardEditorFragment.newInstance()
}