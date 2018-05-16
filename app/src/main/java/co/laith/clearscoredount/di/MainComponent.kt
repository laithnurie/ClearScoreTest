package co.laith.clearscoredount.di

import co.laith.clearscoredount.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ServiceModule::class)])
interface MainComponent {
    fun inject(activity: MainActivity)
}