package com.cardemory.common.interactor

sealed class PermissionResult

data class ShowRationalePermissionResponse(
    val permissionNames: List<String>
) : PermissionResult()

data class CheckedPermissionResponse(
    private val permissionsResponses: List<PermissionResponse>
) : PermissionResult() {

    fun areAllPermissionsGranted() = permissionsResponses.all { !it.denied }

    fun isAnyPermissionPermanentlyDenied() = permissionsResponses.any { it.permanentlyDenied }
}

data class PermissionResponse(
    val permissionName: String,
    val denied: Boolean,
    val permanentlyDenied: Boolean
)

