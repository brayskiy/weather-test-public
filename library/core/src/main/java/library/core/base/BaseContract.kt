package library.core.base

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import library.core.common.DialogData

interface BaseContract {
    interface IDataSource

    interface IRepository

    interface IAdapter {
        fun hideDialog()
        fun displayDialog(dialogData: DialogData)
        fun showProgress(message: String? = null)
        fun hideProgress()
        fun hideSoftKeyboard()
        fun underConstruction(message: String? = null)
        @Deprecated(message = "Deprecated. Should be removed.")
        fun finishActivity(resultCode: Int = Activity.RESULT_CANCELED, resultData: Bundle = Bundle())
    }
}
