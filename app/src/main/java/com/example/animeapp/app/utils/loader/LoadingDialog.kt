package com.example.animeapp.app.utils.loader

import android.app.ProgressDialog
import android.content.Context

@Suppress("DEPRECATION")
class LoadingDialog(private val context: Context) {

    private val dialog: ProgressDialog by lazy(LazyThreadSafetyMode.NONE) { dialog() }

    private fun dialog(): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading, please wait")
        progressDialog.setCancelable(false)
        return progressDialog
    }

    fun show() = dialog.show()

    fun dismiss() = dialog.dismiss()

}

