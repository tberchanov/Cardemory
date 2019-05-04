package com.cardemory.ocr

import android.content.Context
import android.net.Uri
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import javax.inject.Inject


class RecognizeTextInteractor
@Inject constructor(
    private val context: Context
) : BaseSingleInteractor<String, Uri>() {

    private val recognizer = FirebaseVision.getInstance().onDeviceTextRecognizer

    override suspend fun run(params: Uri) =
        Channel<Either<Failure, String>>().also { channel ->
            val image = FirebaseVisionImage.fromFilePath(context, params)
            recognizer.processImage(image)
                .addOnSuccessListener {
                    channel.sendBlocking(Either.Right(it.text))
                }
                .addOnFailureListener {
                    val failure = Either.Left(TextRecognitionFailure(it))
                    channel.sendBlocking(failure)
                }
        }.receive()
}