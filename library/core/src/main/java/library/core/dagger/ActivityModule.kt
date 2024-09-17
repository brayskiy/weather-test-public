package library.core.dagger

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import library.core.dagger.qualifiers.PerActivity
import library.core.util.FragmentTransactionManager

@Module
class ActivityModule {

    @Provides
    @PerActivity
    fun getContext(application: Application): Context {
        return application
    }

    @Provides
    @PerActivity
    fun provideActivity(activity: AppCompatActivity): Activity {
        return activity
    }

    @Provides
    @PerActivity
    fun provideFragmentManager(activity: AppCompatActivity): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    @PerActivity
    fun provideFragmentUtil(fragmentManager: FragmentManager): FragmentTransactionManager {
        return FragmentTransactionManager().apply {
            setFragmentManager(fragmentManager)
        }
    }
}
