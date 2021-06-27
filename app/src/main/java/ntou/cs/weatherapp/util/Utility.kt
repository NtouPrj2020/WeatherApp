package ntou.cs.weatherapp.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.Gravity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.snackbar.Snackbar
import ntou.cs.weatherapp.database.AppPreference
import java.util.*

const val SELE_TIME = "sele_time"
const val SELE_CITY = "sele_city"

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
    fun setPendingIntent(context: Context): PendingIntent {
        var intent: Intent = Intent()
        Log.v("setPendingIntent","setPendingIntent")
        intent.setClass(context, NotificationReceiverReceiver::class.java)
        val pendingintent = PendingIntent.getBroadcast(
            context, 0, intent, 0);
        return pendingintent
    }
    fun setNextAlarm(context: Context, am: AlarmManager?,
                     pi: PendingIntent?) {
        val c: Calendar = Calendar.getInstance()
        val preferencesc: Calendar = Calendar.getInstance()
        val preferences: SharedPreferences? = context.getSharedPreferences(
            SELE_TIME,
            Context.MODE_PRIVATE
        )
        preferencesc.timeInMillis = preferences?.getLong(SELE_TIME,0)!!
        c.timeInMillis = System.currentTimeMillis();
        c.set(Calendar.HOUR_OF_DAY, preferencesc.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, preferencesc.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Log.v("setNextAlarm", c.time.toString())
        if (c.timeInMillis < System.currentTimeMillis()){
            c.add(Calendar.HOUR_OF_DAY,24)
            Log.v("setNextAlarm", "nextday")
        }

        // setWindow
        am?.setExact(
            AlarmManager.RTC_WAKEUP,
            c.timeInMillis, pi)
    }
}