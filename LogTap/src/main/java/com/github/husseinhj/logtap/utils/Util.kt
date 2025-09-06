package com.github.husseinhj.logtap.utils

import android.content.Context
import android.content.pm.ApplicationInfo

internal fun isDebuggable(context: Context): Boolean {
    return (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
}