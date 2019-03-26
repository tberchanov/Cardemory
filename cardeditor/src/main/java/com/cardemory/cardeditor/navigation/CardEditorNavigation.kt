package com.cardemory.cardeditor.navigation

import com.cardemory.carddata.entity.Card

interface CardEditorNavigation {

    fun closeScreen(savedCard: Card)
}