package com.doug.showcase.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.doug.showcase.R

/**
 * Dialog to display a error message on the screen
 */
class ErrorDialog : DialogFragment() {

    companion object {
        const val TAG = "ERROR_DIALOG"
        const val TITLE = "TITLE"
        const val MESSAGE = "MESSAGE"

        fun newInstance(title: String?, message: String): ErrorDialog {
            return ErrorDialog().apply {
                arguments = bundleOf(TITLE to title, MESSAGE to message)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let { activity ->
            val dialog = AlertDialog.Builder(activity, R.style.Alert)
                .setTitle(getTitle())
                .setMessage(getMessage())
                .setPositiveButton(getPositiveButtonLabel()) { _, _ -> dismiss() }
                .create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            return dialog

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getTitle(): String {
        return arguments?.getString(TITLE) ?: getString(R.string.dialog_error_title)
    }

    private fun getMessage(): String? {
        return arguments?.getString(MESSAGE)
    }

    private fun getPositiveButtonLabel(): String {
        return getString(R.string.dialog_positive_button_label)
    }

    fun show(fragmentManager: FragmentManager) {
        if (!fragmentManager.isStateSaved && !fragmentManager.isDestroyed) {
            show(fragmentManager, TAG)
        }
    }
}
