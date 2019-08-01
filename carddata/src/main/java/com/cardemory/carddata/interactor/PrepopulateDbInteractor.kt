package com.cardemory.carddata.interactor

import android.content.Context
import com.cardemory.carddata.R
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.data.db.card.CardDbEntity
import com.cardemory.carddata.data.db.cardset.CardSetDbEntity
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class PrepopulateDbInteractor
@Inject constructor(
    private val context: Context,
    private val appDatabase: AppDatabase
) : BaseSingleInteractor<Unit, Unit>() {

    override suspend fun run(params: Unit): Either<Failure, Unit> {
        appDatabase.cardSetDao().save(createCardSet())
        appDatabase.cardDao().saveAll(createCards())
        return Either.Right(params)
    }

    private fun createCardSet() =
        CardSetDbEntity(CARD_SET_ID, context.getString(R.string.capitals))

    private fun createCards(): List<CardDbEntity> {
        val capitalsTitles =
            context.resources.getStringArray(R.array.capitals_titles)
        val capitalsDescriptions =
            context.resources.getStringArray(R.array.capitals_descriptions)
        val cards = mutableListOf<CardDbEntity>()
        for (i in 0 until capitalsTitles.size) {
            cards.add(
                CardDbEntity(
                    i + 1L,
                    CARD_SET_ID,
                    capitalsTitles[i],
                    capitalsDescriptions[i],
                    0.0,
                    -1
                )
            )
        }
        return cards
    }

    companion object {
        private const val CARD_SET_ID = 1L
    }
}