package ntou.cs.weatherapp.util
import ntou.cs.weatherapp.bean.WeatherBean
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL =  "https://opendata.cwb.gov.tw"

interface CWTService {

    @GET("api/v1/rest/datastore/F-D0047-091")
    fun F_D0047_091(
        @Query("Authorization") authorization: String? = null,
        @Query("format") format: String? = "JSON",
        @Query("locationName") locationName: String? = null,
        @Query("timeFrom") timeFrom: String? = null
    ): Call<WeatherBean>

}
