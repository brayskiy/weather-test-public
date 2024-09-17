package com.example.android_challenge.dagger

import androidx.appcompat.app.AppCompatActivity
import com.example.android_challenge.activity.MainActivity
import dagger.BindsInstance
import dagger.Component
import library.core.dagger.ActivityComponent
import library.core.dagger.AppComponent
import library.core.dagger.qualifiers.PerActivity

@PerActivity
@Component(dependencies = [AppComponent::class],
    modules = []
)

interface MainComponent: ActivityComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance activity: AppCompatActivity, appComponent: AppComponent): MainComponent
    }
}
