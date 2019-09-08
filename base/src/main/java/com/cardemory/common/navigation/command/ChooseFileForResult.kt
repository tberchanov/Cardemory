package com.cardemory.common.navigation.command

import ru.terrakok.cicerone.commands.Command

data class ChooseFileForResult(
    val requestCode: Int,
    val mimeTypeFilter: String? = null,
    val chooserTitle: String? = null
) : Command