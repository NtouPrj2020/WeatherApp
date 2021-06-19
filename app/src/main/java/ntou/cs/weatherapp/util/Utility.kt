package ntou.cs.weatherapp.util

import android.view.Gravity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.snackbar.Snackbar

object Utility {
    val drawableCrossFadeFactory: DrawableCrossFadeFactory
        get() = DrawableCrossFadeFactory.Builder(100)
            .setCrossFadeEnabled(true)
            .build()

    fun makeAnchorSnackbar(coorView: CoordinatorLayout, text: String, anchorID: Int) {
        val snackbar = Snackbar.make(coorView, text, Snackbar.LENGTH_SHORT)
        val layoutParams = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.anchorId = anchorID
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }
}