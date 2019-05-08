package com.cardemory.common.interactor

import android.app.Activity
import com.cardemory.infrastructure.BasePublisherInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.sendBlocking
import timber.log.Timber

typealias PermissionEither = Either<Failure, PermissionResult>

@ObsoleteCoroutinesApi
class RequestPermissionsInteractor
constructor(
    private val activity: Activity
) : BasePublisherInteractor<PermissionResult, List<String>>() {

    override suspend fun run(params: List<String>) =
        Channel<PermissionEither>(Channel.UNLIMITED).apply {
            Dexter.withActivity(activity)
                .withPermissions(params)
                .withListener(object : MultiplePermissionsListener {

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        val deniedPermissions = report?.deniedPermissionResponses?.map {
                            PermissionResponse(it.permissionName, true, it.isPermanentlyDenied)
                        } ?: emptyList()
                        val grantedPermissions = report?.grantedPermissionResponses?.map {
                            PermissionResponse(it.permissionName, false, false)
                        } ?: emptyList()
                        val checkedPermissions = deniedPermissions + grantedPermissions
                        onRequestPermissionResult(CheckedPermissionResponse(checkedPermissions))
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        val permissionsNames = permissions?.map {
                            it.name
                        } ?: emptyList()
                        onRequestPermissionResult(ShowRationalePermissionResponse(permissionsNames))
                    }

                })
                .withErrorListener { onRequestPermissionError(it) }
                .check()
        }

    private fun SendChannel<PermissionEither>.onRequestPermissionResult(
        permissionResult: PermissionResult
    ) {
        Timber.d("onRequestPermissionResult: $permissionResult")
        sendBlocking(
            Either.Right(
                permissionResult
            )
        )
    }

    private fun SendChannel<PermissionEither>.onRequestPermissionError(error: DexterError) {
        Timber.e("onRequestPermissionError: $error")
        sendBlocking(
            Either.Left(
                RequestPermissionFailure()
            )
        )
    }
}