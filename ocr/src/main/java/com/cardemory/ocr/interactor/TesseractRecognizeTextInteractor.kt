package com.cardemory.ocr.interactor

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.googlecode.tesseract.android.TessBaseAPI
import timber.log.Timber
import java.io.*
import javax.inject.Inject

class TesseractRecognizeTextInteractor
@Inject constructor(
    private val context: Context
) : BaseRecognizeTextInteractor() {

    private val tessBaseApi = TessBaseAPI()

    private val dataPath =
        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.path + "/OCR/"

    override suspend fun run(params: Uri): Either<Failure, String> {
        prepareTesseract()
        val photoFile = File(params.path)
        return try {
            Either.Right(extractText(photoFile))
        } catch (e: Exception) {
            Either.Left(TextRecognitionFailure(e))
        }
    }

    @Throws(Exception::class)
    private fun extractText(photoFile: File): String {
        tessBaseApi.init(dataPath, LANGUAGE)
        tessBaseApi.setImage(photoFile)
        val extractedText = tessBaseApi.utF8Text
        tessBaseApi.stop()
        tessBaseApi.end()
        return extractedText
    }

    private fun prepareTesseract() {
        prepareDirectory(dataPath + TESSDATA)
        try {
            copyTessDataFiles(TESSDATA)
        } catch (e: Exception) {
            Timber.e("Unable to copy files to tessdata: $e")
        }
    }

    private fun prepareDirectory(path: String) {
        File(path).takeUnless {
            it.exists()
        }?.mkdirs()?.also { dirsCreated ->
            when (dirsCreated) {
                true -> Timber.d("Created directory $path")
                false -> Timber.e("Error creation of directory $path failed.")
            }
        }
    }

    @Throws(IOException::class)
    private fun copyTessDataFiles(path: String) {
        val fileList = context.assets.list(path)

        for (fileName in fileList!!) {
            val pathToDataFile = "$dataPath$path/$fileName"
            val dataFile = File(pathToDataFile)
            if (dataFile.exists()) {
                Timber.d("${dataFile.path} already exists")
                continue
            }
            val assetInputStream = context.assets.open("$path/$fileName")
            val trainedDataOutputStream = FileOutputStream(pathToDataFile)
            copy(assetInputStream, trainedDataOutputStream)
            Timber.d("Copied $fileName to tessdata")
        }
    }

    @Throws(IOException::class)
    private fun copy(src: InputStream, dst: OutputStream) {
        val buf = ByteArray(BYTE_ARRAY_SIZE)
        do {
            val len = src.read(buf)
            dst.write(buf, 0, len)
        } while (len > 0)
        src.close()
        dst.close()
    }

    override fun cancel() {
        super.cancel()
        tessBaseApi.stop()
    }

    companion object {
        private const val TESSDATA = "tessdata"
        private const val LANGUAGE = "eng"
        private const val BYTE_ARRAY_SIZE = 1024
    }
}
