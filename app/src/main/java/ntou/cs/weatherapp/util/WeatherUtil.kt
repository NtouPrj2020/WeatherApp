package ntou.cs.weatherapp.util

import android.util.Log
import ntou.cs.weatherapp.R
import ntou.cs.weatherapp.bean.WeatherBean
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtil {
    fun getWeekOfDayByNum(num: Int): String {
        return when (num) {
            1 -> "週日"
            2 -> "週一"
            3 -> "週二"
            4 -> "週三"
            5 -> "週四"
            6 -> "週五"
            7 -> "週六"
            else -> "null"
        }
    }

    fun sortWeather(weatherBean: WeatherBean): List<WeatherNode> {
        val weatherNodeList: MutableList<WeatherNode> =
            ArrayList()
        val timeStartList: MutableList<String>
        val timeEndList: MutableList<String>
        val minTList: MutableList<String>
        val maxTList: MutableList<String>
        val wxList: MutableList<String>
        val wxValueList: MutableList<String>
        timeStartList = ArrayList()
        timeEndList = ArrayList()
        minTList = ArrayList()
        maxTList = ArrayList()
        wxList = ArrayList()
        wxValueList = ArrayList()
        val wx =
            weatherBean.records.locations[0].location[0].weatherElement[6]
        val minT =
            weatherBean.records.locations[0].location[0].weatherElement[8]
        val maxT =
            weatherBean.records.locations[0].location[0].weatherElement[12]
        for (timeEntity in wx.time) {
            timeStartList.add(timeEntity.startTime.toString())
        }
        for (timeEntity in wx.time) {
            timeEndList.add(timeEntity.startTime.toString())
        }
        for (timeEntity in wx.time) {
            wxList.add(timeEntity.elementValue[0].value)
        }
        for (timeEntity in wx.time) {
            wxValueList.add(timeEntity.elementValue[1].value)
        }
        for (timeEntity in minT.time) {
            minTList.add(timeEntity.elementValue[0].value)
        }
        for (timeEntity in maxT.time) {
            maxTList.add(timeEntity.elementValue[0].value)
        }
        var i = 0
        while (i < wxList.size) {
            val time1 = wx.time[i].startTime
            val cal1 = Calendar.getInstance()
            var min1: Int
            var min2: Int
            var max1: Int
            var max2: Int
            var min = 0
            var max = 0
            try {
                val year = time1.substring(0, 4).toInt()
                val month = time1.substring(5, 7).toInt()
                val day = time1.substring(8, 10).toInt()
                cal1[Calendar.YEAR] = year
                cal1[Calendar.MONTH] = month - 1
                cal1[Calendar.DAY_OF_MONTH] = day
                val formatter =
                    SimpleDateFormat("yyyy-MM-dd", Locale("zh", "TW"))
                val dt = cal1.time
                val time = formatter.format(dt)
                val dayt = cal1[Calendar.DAY_OF_WEEK].toString()
                val week = "$time $dayt"
                Log.d("weather", week)
                min1 = minTList[i].toInt()
                max1 = maxTList[i].toInt()
                if (i + 1 >= wxList.size) {
                    min2 = minTList[i].toInt()
                    max2 = maxTList[i].toInt()
                } else {
                    min2 = minTList[i + 1].toInt()
                    max2 = maxTList[i + 1].toInt()
                }
                min = min1
                max = min1
                val array = intArrayOf(min1, min2, max1, max2)
                for (j in array.indices) {
                    if (array[j] <= min) {
                        min = array[j]
                    }
                    if (array[j] >= max) {
                        max = array[j]
                    }
                }
            } catch (e: NumberFormatException) {
            }
            val weatherNode =
                WeatherNode(cal1, min, max, wxList[i], wxValueList[i].toInt())
            weatherNodeList.add(weatherNode)
            i += 2
        }
        return weatherNodeList
    }

    fun getFirstWeather(weatherBean: WeatherBean): WeatherNode {
        val weatherNodeList: List<WeatherNode> =
            ArrayList()
        val timeStartList: MutableList<String>
        val timeEndList: MutableList<String>
        val minTList: MutableList<String>
        val maxTList: MutableList<String>
        val wxList: MutableList<String>
        val wxValueList: MutableList<String>
        timeStartList = ArrayList()
        timeEndList = ArrayList()
        minTList = ArrayList()
        maxTList = ArrayList()
        wxList = ArrayList()
        wxValueList = ArrayList()
        val wx =
            weatherBean.records.locations[0].location[0].weatherElement[6]
        val minT =
            weatherBean.records.locations[0].location[0].weatherElement[8]
        val maxT =
            weatherBean.records.locations[0].location[0].weatherElement[12]
        for (timeEntity in wx.time) {
            timeStartList.add(timeEntity.startTime.toString())
        }
        for (timeEntity in wx.time) {
            timeEndList.add(timeEntity.startTime.toString())
        }
        for (timeEntity in wx.time) {
            wxList.add(timeEntity.elementValue[0].value)
        }
        for (timeEntity in wx.time) {
            wxValueList.add(timeEntity.elementValue[1].value)
        }
        for (timeEntity in minT.time) {
            minTList.add(timeEntity.elementValue[0].value)
        }
        for (timeEntity in maxT.time) {
            maxTList.add(timeEntity.elementValue[0].value)
        }
        val i = 0
        val time1 = wx.time[i].startTime
        val cal1 = Calendar.getInstance()
        var min = 0
        var max = 0
        try {
            val year = time1.substring(0, 4).toInt()
            val month = time1.substring(5, 7).toInt()
            val day = time1.substring(8, 10).toInt()
            cal1[Calendar.YEAR] = year
            cal1[Calendar.MONTH] = month - 1
            cal1[Calendar.DAY_OF_MONTH] = day
            val formatter =
                SimpleDateFormat("yyyy-MM-dd", Locale("zh", "TW"))
            val dt = cal1.time
            val time = formatter.format(dt)
            val dayt = cal1[Calendar.DAY_OF_WEEK].toString()
            val week = "$time $dayt"
            min = minTList[i].toInt()
            max = maxTList[i].toInt()
        } catch (e: NumberFormatException) {
        }
        return WeatherNode(cal1, min, max, wxList[i], wxValueList[i].toInt())
    }

    fun getWeatherImage(status: Int): Int {
        val sun = intArrayOf(1)
        val sun_cloud = intArrayOf(2, 3)
        val cloud = intArrayOf(4, 5, 6, 7)
        val rain1 = intArrayOf(8, 9, 10, 19, 20, 30, 31, 32, 37, 38, 39, 41)
        val rain2 = intArrayOf(11, 12, 13, 13, 29)
        val storm = intArrayOf(15, 16, 17, 18, 21, 22, 33, 34, 35, 36)
        val snow = intArrayOf(15, 16, 17, 18, 21, 23, 42)
        val fog = intArrayOf(24, 25, 26, 27, 28)
        for (i in sun.indices) {
            if (status == sun[i]) {
                return R.drawable.w_sun
            }
        }
        for (i in sun_cloud.indices) {
            if (status == sun_cloud[i]) {
                return R.drawable.w_cloudy_1
            }
        }
        for (i in cloud.indices) {
            if (status == cloud[i]) {
                return R.drawable.w_cloudy_2
            }
        }
        for (i in rain1.indices) {
            if (status == rain1[i]) {
                return R.drawable.w_rain_1
            }
        }
        for (i in rain2.indices) {
            if (status == rain2[i]) {
                return R.drawable.w_rain
            }
        }
        for (i in storm.indices) {
            if (status == storm[i]) {
                return R.drawable.w_storm_1
            }
        }
        for (i in snow.indices) {
            if (status == snow[i]) {
                return R.drawable.w_snowy
            }
        }
        for (i in fog.indices) {
            if (status == fog[i]) {
                return R.drawable.w_wind
            }
        }
        return R.drawable.w_wind
    }
}