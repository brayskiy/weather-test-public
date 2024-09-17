package library.core.dagger

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {
    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideResources(): Resources = application.resources
}
