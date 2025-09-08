package com.github.husseinhj.logtap.utils

import android.os.Build
import android.util.Base64
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import com.github.husseinhj.logtap.dto.DeviceAppInfo
import android.graphics.drawable.AdaptiveIconDrawable


internal fun Context?.buildInfo(): DeviceAppInfo? {
    val ctx = this ?: return null

    val pm = ctx.packageManager
    val pkg = ctx.packageName

    // Package info (API 33+ uses flags, older uses int)
    val pInfo = try {
        if (Build.VERSION.SDK_INT >= 33) {
            pm.getPackageInfo(pkg, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            pm.getPackageInfo(pkg, 0)
        }
    } catch (e: Exception) {
        null
    }

    val appInfo = try { pm.getApplicationInfo(pkg, 0) } catch (_: Exception) { null }
    val appLabel = try { pm.getApplicationLabel(appInfo ?: ctx.applicationInfo)?.toString() ?: "App" } catch (_: Exception) { "App" }

    // Version name & code
    val versionName = pInfo?.versionName ?: "0.0.0"
    val versionCode = if (pInfo != null) {
        if (Build.VERSION.SDK_INT >= 28) pInfo.longVersionCode else @Suppress("DEPRECATION") pInfo.versionCode.toLong()
    } else 0L

    val iconBase64 = drawableToPngBase64(try { pm.getApplicationIcon(pkg) } catch (_: Exception) { null })

    return DeviceAppInfo(
        appName = appLabel,
        appBundle = pkg,
        versionName = versionName,
        versionCode = versionCode,
        osType = "Android",
        osVersion = Build.VERSION.RELEASE ?: Build.VERSION.CODENAME ?: "Unknown",
        apiLevel = Build.VERSION.SDK_INT,
        deviceManufacturer = Build.MANUFACTURER ?: "Unknown",
        deviceModel = Build.MODEL ?: Build.DEVICE ?: "Unknown",
        appIconBase64 = iconBase64
    )
}

private fun drawableToPngBase64(drawable: Drawable?): String? {
    if (drawable == null) return null
    val bmp: Bitmap = when (drawable) {
        is BitmapDrawable -> drawable.bitmap
        is AdaptiveIconDrawable -> {
            // Render Adaptive icon into a bitmap
            val size = 128
            val b = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            drawable.setBounds(0, 0, size, size)
            drawable.draw(c)
            b
        }
        else -> {
            // Render any drawable into a bitmap
            val w = maxOf(96, drawable.intrinsicWidth.takeIf { it > 0 } ?: 96)
            val h = maxOf(96, drawable.intrinsicHeight.takeIf { it > 0 } ?: 96)
            val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            drawable.setBounds(0, 0, w, h)
            drawable.draw(c)
            b
        }
    }
    return try {
        val baos = java.io.ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos)
        Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
    } catch (_: Exception) {
        null
    }
}