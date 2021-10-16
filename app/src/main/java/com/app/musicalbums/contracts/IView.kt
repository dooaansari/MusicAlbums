package com.app.musicalbums.contracts

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract

interface IView {
    fun <I,O>registerForActivityResult(contract: ActivityResultContract<I,O> , callback: ActivityResultCallback<O>) : ActivityResultLauncher<I>
    fun onResult()
}