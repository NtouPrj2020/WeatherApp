package ntou.cs.weatherapp.util

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import ntou.cs.weatherapp.MainActivity
import ntou.cs.weatherapp.R
import ntou.cs.weatherapp.database.AppPreference.getSelecteCity
import ntou.cs.weatherapp.database.AppPreference.getWeekDay
import ntou.cs.weatherapp.database.AppPreference.setCity
import ntou.cs.weatherapp.util.Utility.setNextAlarm
import ntou.cs.weatherapp.util.Utility.setPendingIntent
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiverReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v("onReceive","onReceive")
        val c: Calendar = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val weekday = SimpleDateFormat("EE", Locale.getDefault()).format(c.time)

        if (getWeekDay(context,weekday)){
            sendNotification(context)
            setCity(context,getSelecteCity(context))}
        val am = context.getSystemService(Context.ALARM_SERVICE) as?
                AlarmManager
        val pi = setPendingIntent(context)
        setNextAlarm(context, am, pi)
    }
    companion object {
        val CHANNEL_ID = "ntou.cs.weatherapp.notification"
    }

    private fun sendNotification(context: Context) {
        val intent = Intent()
        intent.setClass(context, MainActivity::class.java!!)

        val pi = PendingIntent.getActivity(context,
            0, intent, 0)

        var notification: Notification? = null
        try {
            notification = getNotification(context, pi,
                context.getString(R.string.app_name), "來看看今天的天氣吧~")
        } catch (e: Exception) {
        }
        if (notification != null) {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.notify(1, notification)
        }
    }

    private fun getNotification(context: Context, pi: PendingIntent,
                                title: String, msg: String): Notification? {

        var notification: Notification? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW)
                channel.setShowBadge(false)
                val notificationManager: NotificationManager =
                    context.getSystemService(NotificationManager::class.java)
                notificationManager!!.createNotificationChannel(channel)
                notification = Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(msg)
                    .setWhen(System.currentTimeMillis())
                    .build()
            } else
                notification = Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(msg)
                    .setWhen(System.currentTimeMillis())
                    .build()
        } catch (throwable: Throwable) {
            return null
        }
        return notification
    }

}
