package library.core.dagger

import dagger.Component
import library.core.dagger.qualifiers.PerActivity

@PerActivity
@Component(modules = [ActivityModule::class, AppModule::class])
interface ActivityComponent
