package com.fps.android.core.infoProviders

import android.os.Build

internal interface IOsBuildInfoProvider {
    fun getModelName(): String
}

internal class OsBuildInfoProvider : IOsBuildInfoProvider {
    override fun getModelName(): String {
        return Build.MODEL!!
    }
}