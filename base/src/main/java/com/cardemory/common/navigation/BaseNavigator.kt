package com.cardemory.common.navigation

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.IdRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.cardemory.common.navigation.command.BackWithResult
import com.cardemory.common.navigation.command.ChooseFileForResult
import com.cardemory.common.navigation.command.ForwardForResult
import com.cardemory.common.navigation.command.TakePhotoForResult
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
            else -> super.applyCommand(command)
        }
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
        takePictureIntent.resolveActivity(activity.packageManager)?.also {
            val photoUri: Uri = FileProvider.getUriForFile(
                activity,
                providerAuthority,
                command.photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            activity.startActivityForResult(takePictureIntent, command.requestCode)
        } ?: Timber.d("There is no activity for taking photo!")
    }

    private fun applyChooseFileForResult(command: ChooseFileForResult) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType(command.mimeTypeFilter)
        // TODO check for available activity
        activity.startActivityForResult(intent, command.requestCode)
    }

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

    companion object {
        private const val DIRECTORY_NAME = "Cardemory"
    }
}