package com.github.husseinhj.logtap.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class DeviceAppInfo(
    val appName: String,
    val appBundle: String,
    val versionName: String,
    val versionCode: Long,
    val osType: String,
    val osVersion: String,
    val apiLevel: Int,
    val deviceManufacturer: String,
    val deviceModel: String,
    val appIconBase64: String?
)