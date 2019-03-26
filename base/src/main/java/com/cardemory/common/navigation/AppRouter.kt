package com.cardemory.common.navigation

import com.cardemory.common.navigation.command.BackWithResult
import com.cardemory.common.navigation.command.ForwardForResult
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class AppRouter : Router() {

    fun navigateForResult(screen: Screen, requestCode: Int) {
        executeCommands(
            ForwardForResult(
                screen,
                requestCode
            )
        )
    }

    fun exitWithResult(resultCode: Int, data: Any?) {
        executeCommands(BackWithResult(resultCode, data))
    }
}