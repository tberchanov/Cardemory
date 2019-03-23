package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.CardSet
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class SaveCardSetInteractor
@Inject constructor(
    @DbData private val cardRepository: CardRepository
) : BaseSingleInteractor<CardSet, CardSet>() {

    override suspend fun run(params: CardSet): Either<Failure, CardSet> {
        return Either.Right(cardRepository.saveCardSet(params))
    }
}