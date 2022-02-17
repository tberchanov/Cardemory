package com.cardemory.carddata.interactor

import android.content.ContentResolver
import android.net.Uri
import com.cardemory.carddata.data.json.CardSetJsonEntity
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.failure.JsonSyntaxFailure
import com.cardemory.carddata.interactor.failure.ReadCardSetFromFileFailure
import com.cardemory.carddata.mapper.CardSetJsonToDomainMapper
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class GetCardSetFromFileInteractor
@Inject constructor(
    private val cardSetJsonMapper: CardSetJsonToDomainMapper,
    private val contentResolver: ContentResolver
) : BaseSingleInteractor<CardSet, Uri>() {
    override suspend fun run(params: Uri): Either<Failure, CardSet> {
        return try {
            val fileText = contentResolver.openInputStream(params)?.let { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            } ?: ""
            val cardSet = Gson().fromJson(fileText, CardSetJsonEntity::class.java)
            Either.Right(cardSetJsonMapper.from(cardSet))
        } catch (e: JsonSyntaxException) {
            Either.Left(JsonSyntaxFailure())
        } catch (e: Exception) {
            Either.Left(ReadCardSetFromFileFailure(e))
        }
    }
}