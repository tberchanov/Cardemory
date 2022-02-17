package com.cardemory.carddata.interactor

import android.os.Environment
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.mapper.CardSetJsonToDomainMapper
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.google.gson.Gson
import java.io.File
import javax.inject.Inject

class SaveCardSetToFileInteractor
@Inject constructor(
    private val cardSetJsonMapper: CardSetJsonToDomainMapper
) : BaseSingleInteractor<File, CardSet>() {

    override suspend fun run(params: CardSet): Either<Failure, File> {
        val childDirectory = File(
            Environment.getExternalStorageDirectory(),
            DIRECTORY_NAME
        )
        if (!childDirectory.exists()) {
            childDirectory.mkdir()
        }
        val cardSetFile = File(childDirectory, params.name + CARD_SET_EXTENSION)
        cardSetJsonMapper.to(params).let {
            Gson().toJson(it)
        }.let {
            cardSetFile.writeText(it)
        }
        return Either.Right(cardSetFile)
    }

    companion object {
        private const val DIRECTORY_NAME = "Cardemory"
        private const val CARD_SET_EXTENSION = ".txt"
    }
}