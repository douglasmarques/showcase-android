package com.doug.showcase.photo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.doug.showcase.photo.ui.dialogs.ErrorDialog

/**
 * Base fragment that holds some common logic to display errors
 */
abstract class BaseFragment : Fragment() {

    private fun createErrorDialog(title: String?, message: String): ErrorDialog? {
        return activity?.let { activity ->
            if (isReadyToShowDialog(activity)) {
                ErrorDialog.newInstance(title, message)
            } else {
                null
            }
        }
    }

    protected fun showErrorDialog(title: String? = null, message: String) {
        val errorDialog = createErrorDialog(title, message)
        errorDialog?.show(childFragmentManager)
    }

    protected fun setActionBarTitle(title: String) {
        val actionBar = (activity as? MainActivity)?.supportActionBar
        actionBar?.title = title
    }

    private fun isReadyToShowDialog(activity: FragmentActivity) =
        !activity.isFinishing && !activity.isChangingConfigurations
}
