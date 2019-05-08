package com.cardemory.ocr.interactor

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.googlecode.tesseract.android.TessBaseAPI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import java.io.File
import javax.inject.Inject


class MlKitRecognizeTextInteractor
@Inject constructor(
    private val context: Context
) : BaseRecognizeTextInteractor() {

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

    @Throws(Exception::class)
    private fun extractText(photoFile: File): String {
        val tessBaseApi = TessBaseAPI()
        val DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/OCR/"
        tessBaseApi.init(DATA_PATH, "eng")
        tessBaseApi.setImage(photoFile)
        val extractedText = tessBaseApi.utF8Text
        tessBaseApi.end()
        return extractedText
    }
}