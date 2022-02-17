package com.cardemory.common.navigation.command

import ru.terrakok.cicerone.commands.Command
import java.io.File

class TakePhotoForResult(
    val photoFile: File,
    val requestCode: Int,
    val chooserTitle: String? = null
) : Command