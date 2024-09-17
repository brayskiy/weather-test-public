package library.core.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import library.core.common.DialogData
import library.core.dagger.ActivityComponent

abstract class BaseFragment<V : ViewModel, C : ActivityComponent> : Fragment(), BaseContract.IAdapter {

    @get:LayoutRes
    abstract val layoutId: Int

    lateinit var viewModel: V

    protected abstract fun setupFragmentView(view: View)
    protected abstract fun injectFragment()

    var snackbar: Snackbar? = null

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        sharedPref = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(layoutId, container, false)
        setupFragmentView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectFragment()
    }

    fun <F : ViewModelProvider.Factory> setViewModel(factory: F, viewModelClass: Class<V>) {
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
    }

    override fun displayDialog(dialogData: DialogData) {
        (activity as BaseActivity<*, *>).displayDialog(dialogData)
    }

    override fun hideDialog() {
        (activity as BaseActivity<*, *>).hideDialog()
    }

    override fun showProgress(message: String?) {
        (activity as BaseActivity<*, *>).showProgress(message)
    }

    override fun hideProgress() {
        hideDialog()
    }

    override fun hideSoftKeyboard() {
        (activity as BaseActivity<*, *>).hideSoftKeyboard()
    }

    override fun underConstruction(message: String?) {
        (activity as BaseActivity<*, *>).underConstruction(message)
    }

    override fun finishActivity(resultCode: Int, resultData: Bundle) {
        (activity as BaseActivity<*, *>).finishActivity(resultCode, resultData)
    }

    fun onBackPressed() {
        (activity as BaseActivity<*, *>).onBackPressed()
    }
}
