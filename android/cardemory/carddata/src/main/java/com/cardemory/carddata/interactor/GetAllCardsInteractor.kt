package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.repository.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.Card
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class GetAllCardsInteractor
@Inject constructor(
    @DbData private val cardRepository: CardRepository
) : BaseSingleInteractor<List<Card>, Unit>() {

    override suspend fun run(params: Unit): Either<Failure, List<Card>> {
        val cards = cardRepository.getAllCards()
        return Either.Right(cards)
    }
}