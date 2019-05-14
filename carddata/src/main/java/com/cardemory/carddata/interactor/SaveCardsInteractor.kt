package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.Card
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class SaveCardsInteractor
@Inject constructor(
    @DbData private val cardRepository: CardRepository
) : BaseSingleInteractor<Unit, List<Card>>() {

    override suspend fun run(params: List<Card>): Either<Failure, Unit> {
        cardRepository.saveCards(params)
        return Either.Right(Unit)
    }
}