package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.repository.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.Card
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class SaveCardInteractor
@Inject constructor(
    @DbData private val cardRepository: CardRepository
) : BaseSingleInteractor<Card, Card>() {

    override suspend fun run(params: Card): Either<Failure, Card> {
        return Either.Right(cardRepository.saveCard(params))
    }
}