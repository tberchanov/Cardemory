package com.cardemory.cardeditor.interactor

import android.content.Context
import android.os.Environment
import com.cardemory.infrastructure.BaseBlockingInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetPhotoFileInteractor
@Inject constructor(
    private val context: Context
) : BaseBlockingInteractor<File, Unit>() {

    override fun run(params: Unit): Either<Failure, File> {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return if (storageDir == null) {
            Either.Left(UnavailableStorageDirectoryFailure())
        } else {
            try {
                File.createTempFile(PHOTO_FILE_PREFIX, PHOTO_FILE_SUFFIX, storageDir)
                    .let { Either.Right(it) }
            } catch (e: IOException) {
                Either.Left(PhotoFileCreationFailure(e))
            }
        }
    }

    companion object {
        private const val PHOTO_FILE_PREFIX = "ocr_photo"
        private const val PHOTO_FILE_SUFFIX = ".jpg"
    }
}
