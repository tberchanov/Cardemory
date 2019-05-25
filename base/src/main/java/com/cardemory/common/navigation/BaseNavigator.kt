package com.cardemory.common.navigation

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.annotation.IdRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.cardemory.common.navigation.command.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import timber.log.Timber


abstract class BaseNavigator(
    private val activity: FragmentActivity,
    @param:IdRes @field:IdRes private val containerId: Int
) : SupportAppNavigator(activity, containerId) {

    private val providerAuthority = activity.packageName + ".provider"

    val topFragment: Fragment?
        get() = activity.supportFragmentManager.findFragmentById(containerId)

    override fun applyCommand(command: Command?) {
        when (command) {
            is ForwardForResult -> applyFragmentForwardForResult(command)
            is BackWithResult -> applyFragmentBackWithResult(command)
            is TakePhotoForResult -> applyTakePhotoForResult(command)
            is ChooseFileForResult -> applyChooseFileForResult(command)
            is ForwardToAppSettings -> applyForwardToAppSettings(command)
            else -> super.applyCommand(command)
        }
    }

    private fun applyForwardToAppSettings(command: ForwardToAppSettings) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    private fun applyFragmentForwardForResult(command: ForwardForResult) {
        val fragment = (command.screen as SupportAppScreen).fragment
        fragment.setTargetFragment(topFragment, command.requestCode)
        fragmentForward(command)
    }

    private fun applyFragmentBackWithResult(command: BackWithResult) {
        val onResultListener = topFragment?.targetFragment as? OnResultListener
        fragmentBack()
        activity.supportFragmentManager.executePendingTransactions()
        onResultListener?.onResult(command.resultCode, command.data)
    }

    private fun applyTakePhotoForResult(command: TakePhotoForResult) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (isIntentSafe(takePictureIntent)) {
            val photoUri: Uri = FileProvider.getUriForFile(
                activity,
                providerAuthority,
                command.photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            activity.startActivityForResult(
                Intent.createChooser(
                    takePictureIntent,
                    command.chooserTitle
                ), command.requestCode
            )
        } else {
            Timber.e("There is no activity for taking photo!")
        }
    }

    private fun applyChooseFileForResult(command: ChooseFileForResult) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType(command.mimeTypeFilter)
        if (isIntentSafe(intent)) {
            activity.startActivityForResult(
                Intent.createChooser(intent, command.chooserTitle),
                command.requestCode
            )
        } else {
            Timber.e("No found activity for intent: $intent")
        }
    }

    private fun isIntentSafe(intent: Intent) =
        intent.resolveActivity(activity.packageManager) != null

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        fragmentTransaction: FragmentTransaction
    ) {
        fragmentTransaction.setCustomAnimations(
            android.R.anim.fade_in, android.R.anim.fade_out,
            android.R.anim.fade_in, android.R.anim.fade_out
        )
    }
}