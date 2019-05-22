package com.cardemory.train.ui.model

import com.cardemory.carddata.entity.Card

data class TrainCard(
    val card: Card,
    var isBackVisible: Boolean = false
)