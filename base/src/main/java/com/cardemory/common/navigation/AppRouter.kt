package com.cardemory.common.navigation

import com.cardemory.common.navigation.command.*
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import java.io.File

class AppRouter : Router() {

    fun navigateForResult(screen: Screen, requestCode: Int) {
        executeCommands(ForwardForResult(screen, requestCode))
    }

    fun exitWithResult(resultCode: Int, data: Any?) {
        executeCommands(BackWithResult(resultCode, data))
    }

    fun takePhotoForResult(photoFile: File, requestCode: Int) {
        executeCommands(TakePhotoForResult(photoFile, requestCode))
    }

    fun chooseFileForResult(requestCode: Int, mimeTypeFilter: String?) {
        executeCommands(ChooseFileForResult(requestCode, mimeTypeFilter))
    }

    fun forwardToAppSettings() {
        executeCommands(ForwardToAppSettings())
    }
}