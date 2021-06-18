package ntou.cs.weatherapp.database

import android.app.Activity
import android.content.Context
import ntou.cs.weatherapp.util.CityItem

const val PREF_CITY = "pref_city"

object AppPreference{
    fun getCity(activity: Activity): Int? {
        val preferences =
            activity.getPreferences(Context.MODE_PRIVATE)
        return preferences.getInt(PREF_CITY, CityItem.KEELUNG)
    }

    fun setCity(activity: Activity,city:Int) {
        val preferences =
            activity.getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(
            PREF_CITY,
            city
        )
        editor.apply()
    }
}

