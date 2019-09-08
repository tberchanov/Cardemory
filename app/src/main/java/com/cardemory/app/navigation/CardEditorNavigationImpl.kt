package com.cardemory.app.navigation

import android.app.Activity
import com.cardemory.carddata.entity.Card
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.common.navigation.AppRouter
import java.io.File

class CardEditorNavigationImpl(
    private val router: AppRouter
) : CardEditorNavigation {

    override fun closeScreen(savedCard: Card) {
        router.exitWithResult(Activity.RESULT_OK, savedCard)
    }

    override fun showTakePhotoScreen(photoFile: File, requestCode: Int) {
        router.takePhotoForResult(photoFile, requestCode)
    }
}