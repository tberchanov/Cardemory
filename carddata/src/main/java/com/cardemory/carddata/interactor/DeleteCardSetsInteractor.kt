package com.cardemory.carddata.interactor

import com.cardemory.carddata.data.repository.CardRepository
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.CardSet
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class DeleteCardSetsInteractor
@Inject constructor(
    @DbData private val cardSetRepository: CardRepository
) : BaseSingleInteractor<List<CardSet>, List<CardSet>>() {

    override suspend fun run(params: List<CardSet>): Either<Failure, List<CardSet>> {
        cardSetRepository.deleteCardSets(params)
        return Either.Right(params)
    }
}