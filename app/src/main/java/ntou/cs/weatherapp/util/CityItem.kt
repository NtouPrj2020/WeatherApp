package ntou.cs.weatherapp.util

import java.io.Serializable
import java.util.*

class CityItem(
    private val cityID: Int,
    private val cityName: String,
    private val cityDescription: String,
    private val drawable: Int
) : Serializable {

    companion object {
        const val KEELUNG = 101
        const val TAIPEI = 102
        const val NEWTAIPEI = 103
        const val TAOYUAN = 104
        const val OLDTAOYUAN = 1041
        const val HSINCHU = 105
        const val HSINCHUCITY = 1051
        const val MIAOLI = 106
        const val TAICHUNG = 201
        const val CHANGHUA = 202
        const val NANTOU = 203
        const val YUNLIN = 204
        const val CHIAYI = 301
        const val CHIAYICITY = 3011
        const val TAINAN = 302
        const val KAOHSIUNG = 303
        const val PINGTUNG = 304
        const val YILAN = 401
        const val HUALIEN = 402
        const val TAITUNG = 403
        const val PENGHU = 501
        const val KINMEN = 502
        const val LIENCHIANG = 503

        class Item(var key: Int, var value: String) {

        }

        val cityList = mutableListOf<Item>(
            Item(KEELUNG,"基隆市"),
            Item(TAIPEI,"臺北市"),
            Item(NEWTAIPEI,"新北市"),
            Item(TAOYUAN,"桃園市"),
            Item(HSINCHU,"新竹縣"),
            Item(MIAOLI,"苗栗縣"),
            Item(TAICHUNG,"臺中市"),
            Item(CHANGHUA,"彰化縣"),
            Item(NANTOU,"南投縣"),
            Item(YUNLIN,"雲林縣"),
            Item(CHIAYI,"嘉義縣"),
            Item(TAINAN,"臺南市"),
            Item(KAOHSIUNG,"高雄市"),
            Item(PINGTUNG,"屏東縣"),
            Item(YILAN,"宜蘭縣"),
            Item(HUALIEN,"花蓮縣"),
            Item(TAITUNG,"臺東縣"),
            Item(PENGHU,"澎湖縣"),
            Item(KINMEN,"金門縣"),
            Item(LIENCHIANG,"連江縣")
        )

        fun getCityNameByID(cityID: Int): String {
            return when (cityID) {
                KEELUNG -> "基隆市"
                TAIPEI -> "臺北市"
                NEWTAIPEI -> "新北市"
                OLDTAOYUAN -> "桃園縣"
                TAOYUAN -> "桃園市"
                HSINCHU -> "新竹縣"
                HSINCHUCITY -> "新竹市"
                MIAOLI -> "苗栗縣"
                TAICHUNG -> "臺中市"
                CHANGHUA -> "彰化縣"
                NANTOU -> "南投縣"
                YUNLIN -> "雲林縣"
                CHIAYI -> "嘉義縣"
                CHIAYICITY -> "嘉義市"
                TAINAN -> "臺南市"
                KAOHSIUNG -> "高雄市"
                PINGTUNG -> "屏東縣"
                YILAN -> "宜蘭縣"
                HUALIEN -> "花蓮縣"
                TAITUNG -> "臺東縣"
                PENGHU -> "澎湖縣"
                KINMEN -> "金門縣"
                LIENCHIANG -> "連江縣"
                else -> "基隆市"
            }
        }
    }

}