package com.cardemory.common.navigation.command

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.Forward

class ForwardForResult(
    screen: Screen,
    val requestCode: Int
) : Forward(screen)
