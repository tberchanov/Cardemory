package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.repository.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.failure.CardSetNotFoundFailure
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import javax.inject.Inject

class GetCardSetInteractor @Inject constructor(
    @DbData private val cardSetRepository: CardRepository
) : BaseSingleInteractor<CardSet, Long>() {

    override suspend fun run(params: Long) =
        cardSetRepository.getCardSet(params)?.let {
            Either.Right(it)
        } ?: Either.Left(CardSetNotFoundFailure())
}