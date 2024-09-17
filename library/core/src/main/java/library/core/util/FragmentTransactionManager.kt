package library.core.util

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import javax.inject.Inject

class FragmentTransactionManager @Inject constructor() {
    private lateinit var fragmentManager: FragmentManager

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment, tag: String?, addToBackStack: Boolean, backStackName: String?) {
        @SuppressLint("CommitTransaction") val transaction: FragmentTransaction =
            fragmentManager
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
}
