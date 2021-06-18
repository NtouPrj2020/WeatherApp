package ntou.cs.weatherapp.util

import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

object Utility {
    val drawableCrossFadeFactory: DrawableCrossFadeFactory
        get() = DrawableCrossFadeFactory.Builder(100)
            .setCrossFadeEnabled(true)
            .build()
}