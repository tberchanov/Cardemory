package com.cardemory.app.navigation

import com.cardemory.train.navigation.TrainNavigation
import ru.terrakok.cicerone.Router

class TrainNavigationImpl(
    private val router: Router
) : TrainNavigation {

    override fun back() {
        router.exit()
    }
}