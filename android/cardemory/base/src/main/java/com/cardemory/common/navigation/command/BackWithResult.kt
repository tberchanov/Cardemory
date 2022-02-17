package com.cardemory.common.navigation.command

import ru.terrakok.cicerone.commands.Back

class BackWithResult(val resultCode: Int, val data: Any?) : Back()
