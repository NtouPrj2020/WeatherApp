package ntou.cs.weatherapp.database

import android.content.Context
import android.content.SharedPreferences
import ntou.cs.weatherapp.util.CityItem
import java.util.*

const val PREF_CITY = "pref_city"
const val SELE_TIME = "sele_time"
const val SELE_CITY = "sele_city"
const val SELE_WEEKDAY = "sele_weekday"

object AppPreference{
    fun getCity(context: Context?): Int? {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            PREF_CITY,
            Context.MODE_PRIVATE
        )
        if (preferences != null) {
            return preferences.getInt(PREF_CITY, CityItem.KEELUNG)
        }
        return 1
    }

    fun setCity(context: Context?, city: Int?) {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            PREF_CITY,
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putInt(
            PREF_CITY,
            city!!
        )
        editor?.apply()
    }
    fun getSelecteCity(context: Context?): Int? {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_CITY,
            Context.MODE_PRIVATE
        )
        if (preferences != null) {
            return preferences.getInt(SELE_CITY, CityItem.KEELUNG)
        }
        return 1
    }

    fun setSelecteCity(context: Context?,city:Int) {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_CITY,
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putInt(
            SELE_CITY,
            city
        )
        editor?.apply()
    }
    fun getWeekDay(context: Context?,weekday:String): Boolean {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_WEEKDAY,
            Context.MODE_PRIVATE
        )
        if (preferences != null) {
            return preferences.getBoolean(weekday, false )
        }
        return true
    }

    fun setWeekDay(context: Context?, weekday:String, isactive:Boolean) {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_WEEKDAY,
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putBoolean(
            weekday,
            isactive
        )
        editor?.apply()
    }
    fun getTime(context: Context?): Long? {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_TIME,
            Context.MODE_PRIVATE
        )
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        val longtime = c.timeInMillis
        if (preferences != null) {
            return preferences.getLong(SELE_TIME,longtime)
        }
        return 1
    }

    fun setTime(context: Context?, time: Long) {
        val preferences: SharedPreferences? = context?.getSharedPreferences(
            SELE_TIME,
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putLong(
            SELE_TIME,
            time
        )
        editor?.apply()
    }
}

