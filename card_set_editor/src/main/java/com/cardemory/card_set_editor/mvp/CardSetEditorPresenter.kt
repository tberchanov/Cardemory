package com.cardemory.card_set_editor.mvp

import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.common.mvp.BasePresenter

class CardSetEditorPresenter(
    private val cardSetEditorNavigation: CardSetEditorNavigation
) : BasePresenter<CardSetEditorContract.View>(),
    CardSetEditorContract.Presenter