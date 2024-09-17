package library.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import library.core.CoreController

import library.core.R
import library.core.common.DialogData
import library.core.common.ToastMessage
import library.core.dagger.ActivityComponent
import java.lang.IllegalStateException

abstract class BaseActivity<V : ViewModel, C : ActivityComponent> : AppCompatActivity(), BaseContract.IAdapter {

    @get:LayoutRes
    abstract val layoutId: Int

    val appComponent = CoreController.instance.appComponent

    lateinit var viewModel: V

    @JvmField
    var dialog: AlertDialog? = null

    @JvmField
    var snackbar: Snackbar? = null

    private lateinit var currentTheme: String

    abstract fun setupActivity(savedInstanceState: Bundle?)

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WeatherChallenge)

        super.onCreate(savedInstanceState)

        setContentView(layoutId)
        setupActivity(savedInstanceState)
    }

    fun <F : ViewModelProvider.Factory> setViewModel(factory: F, viewModelClass: Class<V>) {
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
    }

    fun replaceFragment(fragment: Fragment, frameId: Int, frameTag: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(frameId, fragment, frameTag)
            .commit()
    }

    fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment, tag: String?, addToBackStack: Boolean, backStackName: String?) {
        @SuppressLint("CommitTransaction") val transaction: FragmentTransaction =
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        if (tag != null) {
            transaction.replace(layoutId, fragment, tag)
        } else {
            transaction.replace(layoutId, fragment)
        }

        if (addToBackStack) {
            transaction.addToBackStack(backStackName)
        }

        transaction.commit()
    }

    fun getVisibleFragment(): Fragment? {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment.isVisible) return fragment
        }
        return null
    }

    override fun displayDialog(dialogData: DialogData) {
        try {
            if (!isFinishing) {
                hideDialog()
                val message = DialogData.getMessage(this, dialogData)
                val title = DialogData.getTitle(this, dialogData)
                var positiveButtonText = DialogData.getPositiveButtonText(this, dialogData)
                val negativeButtonText = DialogData.getNegativeButtonText(this, dialogData)
                val contextThemeWrapper = ContextThemeWrapper(this, R.style.AppThemeAlertDialog)

                when (dialogData.type) {
                    DialogData.Type.ALERT -> {
                        if (positiveButtonText.isEmpty()) {
                            positiveButtonText = getString(R.string.ok)
                        }
                        val builder = AlertDialog.Builder(contextThemeWrapper)
                            .setCancelable(dialogData.isDismissible)
                            .setMessage(message)
                            .setPositiveButton(positiveButtonText) { _, _ -> dialogData.positiveCallback?.invoke() }
                            .setOnDismissListener { dialogData.dismissCallback?.invoke() }
                        if (title.isNotEmpty()) {
                            builder.setTitle(title)
                        }
                        if (negativeButtonText.isNotEmpty()) {
                            builder.setNegativeButton(negativeButtonText) { _, _ -> dialogData.negativeCallback?.invoke() }
                        }

                        if (dialogData.dialogView != null) {
                            builder.setView(dialogData.dialogView)
                        }

                        dialog = builder.create()
                        if (!this.isFinishing) {
                            dialog?.show()
                        }
                    }

                    DialogData.Type.ERROR -> {
                        if (positiveButtonText.isEmpty()) {
                            positiveButtonText = getString(R.string.ok)
                        }
                        val builder = AlertDialog.Builder(contextThemeWrapper)
                            .setCancelable(dialogData.isDismissible)
                            .setMessage(message)
                            .setPositiveButton(positiveButtonText) { _, _ -> dialogData.positiveCallback?.invoke() }
                        if (negativeButtonText.isNotEmpty()) {
                            builder.setNegativeButton(negativeButtonText) { _, _ -> dialogData.negativeCallback?.invoke() }
                        }
                        dialog = builder.create()
                        if (!this.isFinishing) {
                            dialog?.show()
                        }
                    }

                    DialogData.Type.PROGRESS -> {
                        if (!isFinishing) {
                            val progressView = LayoutInflater.from(this).inflate(
                                R.layout.alert_progress,
                                null, false
                            )
                            val messageView = progressView.findViewById<TextView>(R.id.alertProgressText)
                            if (message.isNotEmpty()) {
                                messageView.text = message
                            }
                            val progressThemeWrapper = ContextThemeWrapper(this, R.style.AppThemeProgressAlertDialog)
                            val builder = AlertDialog.Builder(progressThemeWrapper)
                                .setCancelable(dialogData.isDismissible)
                                .setView(progressView)
                                .setOnDismissListener { dialogData.dismissCallback?.invoke() }
                            dialog = builder.create()
                            if (!this.isFinishing) {
                                dialog?.show()
                            }
                        }
                    }

                    DialogData.Type.TOAST -> {
                        if (!isFinishing) {
                            ToastMessage.show(this, message, R.drawable.toast_background) { dialogData.positiveCallback?.invoke() }
                        }
                    }

                    DialogData.Type.SNACKBAR -> Unit
                }
            }
        } catch (e: Throwable) {
            throw IllegalStateException(e)
        }
    }

    override fun hideDialog() {
        dialog?.dismiss()
        dialog = null
        snackbar?.dismiss()
        snackbar = null
    }

    override fun showProgress(message: String?) {
        val dialogData = DialogData(DialogData.Type.PROGRESS)
        if (message != null) {
            dialogData.message = message
        }
        displayDialog(dialogData)
    }

    override fun hideProgress() {
        hideDialog()
    }

    override fun hideSoftKeyboard() {
        val focusView = currentFocus
        if (focusView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }

    override fun underConstruction(message: String?) {
        val dialogData = DialogData(DialogData.Type.ERROR)
        dialogData.titleResId = R.string.dialog_under_construction_title
        if (message != null) {
            dialogData.messageResId = R.string.dialog_under_construction_message
        } else {
            dialogData.message = "This feature  '$message' is not implemented yet"
        }
        displayDialog(dialogData)
    }
}
