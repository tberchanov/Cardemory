package com.cardemory.cardeditor.navigation

import com.cardemory.carddata.entity.Card
import java.io.File

interface CardEditorNavigation {

    fun closeScreen(savedCard: Card)

    fun showTakePhotoScreen(photoFile: File, requestCode: Int)
}