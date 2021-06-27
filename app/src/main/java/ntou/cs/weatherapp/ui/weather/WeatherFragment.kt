package ntou.cs.weatherapp.ui.weather

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.BitmapPalette.CallBack
import com.github.florent37.glidepalette.GlidePalette
import ntou.cs.weatherapp.BuildConfig
import ntou.cs.weatherapp.R
import ntou.cs.weatherapp.bean.WeatherBean
import ntou.cs.weatherapp.database.AppPreference
import ntou.cs.weatherapp.util.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {

    private var mainView: View? = null
    private var activity: Activity? = null

    private var iMainColor = 0
    private var city = 0

    private var progressBar: View? = null
    private var tv_1: TextView? = null
    private var tv_2: TextView? = null
    private var imageView: ImageView? = null
    private var first_status: ImageView? = null
    private var imgProgressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var tv_city: TextView? = null
    private var tv_date: TextView? = null
    private var main_nested: View? = null
    private var main_card: CardView? = null
    private var coor_view:CoordinatorLayout? = null

    private var spinner_city: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity()
        mainView = inflater.inflate(R.layout.fragment_weather, container, false)
        initView(mainView)
        return mainView
    }

    protected fun initView(view: View?) {
        recyclerView = view?.findViewById(R.id.rv_weather)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        tv_1 = view?.findViewById(R.id.weather_title)
        tv_2 = view?.findViewById(R.id.weather_status)
        imageView = view?.findViewById(R.id.city_image)
        progressBar = view?.findViewById(R.id.view_city_progressBar)
        imgProgressBar = view?.findViewById(R.id.city_progressBar)
        tv_date = view?.findViewById(R.id.weather_date)
        tv_city = view?.findViewById(R.id.weather_city)
        first_status = view?.findViewById(R.id.status_first)
        main_nested = view?.findViewById(R.id.main_nested)
        main_nested?.setVisibility(View.GONE)
        main_card = view?.findViewById(R.id.main_card)
        spinner_city = view?.findViewById(R.id.spinner_city)
        coor_view= view?.findViewById(R.id.coor_view)

        val spinnerArray = mutableListOf<String>()
        for (i in CityItem.cityList) {
            spinnerArray.add(i.value)
        }


        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_city?.adapter = adapter
        for(i in 0 until CityItem.cityList.size){
            if(CityItem.cityList[i].key == AppPreference.getCity(requireContext())!!){
                spinner_city?.setSelection(i)
            }
        }
        spinner_city?.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = CityItem.cityList[position].key
                AppPreference.setCity(requireActivity(),city)
                setHeadImg()
                getDataAsync(CityItem.getCityNameByID(CityItem.cityList[position].key))
            }
        }

        getDataAsync(CityItem.getCityNameByID(city))

    }

    private fun setHeadImg() {
        imgProgressBar!!.visibility = View.VISIBLE
        val callBack = CallBack { palette ->
            if (palette != null) {

                activity?.runOnUiThread {
                    if(isAdded){
                        iMainColor =
                            palette.getDominantColor(resources.getColor(R.color.black))
                        main_card!!.setCardBackgroundColor(iMainColor)
                    }
                }
            }
        }
        Glide
            .with(requireContext())
            .load("https://source.unsplash.com/daily?${CityItem.getCityNameByID(city)}")
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .transition(DrawableTransitionOptions.withCrossFade(Utility.drawableCrossFadeFactory))
            .error(R.drawable.loading_fail_background)
            .listener(
                GlidePalette.with("https://source.unsplash.com/daily?${CityItem.getCityNameByID(city)}")
                    .use(BitmapPalette.Profile.VIBRANT_LIGHT)
                    .crossfade(true, 200)
                    .intoCallBack(callBack)
            )
            .into(imageView!!)
    }

    private fun getDataAsync(name: String) {
        progressBar?.visibility = View.VISIBLE
        main_nested?.visibility = View.GONE

        val formatter =
            SimpleDateFormat("yyyy-MM-dd", Locale("zh", "TW"))
        //https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        val time = formatter.format(dt) + "T06:00:00"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CWTService::class.java)

        service.F_D0047_091(
            BuildConfig.CWT_SECRET,
            "JSON",
            name,
            time).enqueue(object : retrofit2.Callback<WeatherBean>{
            override fun onFailure(call: retrofit2.Call<WeatherBean>, t: Throwable) {
                if(coor_view!=null){
                    Utility.makeAnchorSnackbar(coor_view!!,"讀取資料失敗",R.id.spinner_city)
                }
            }

            override fun onResponse(
                call: retrofit2.Call<WeatherBean>,
                response: retrofit2.Response<WeatherBean>
            ) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        if(isAdded){
                            activity!!.runOnUiThread {
                                progressBar?.visibility = View.GONE
                                initLayout(response.body()!!)
                            }
                        }

                    }
                } else{
                    if(coor_view!=null){
                        Utility.makeAnchorSnackbar(coor_view!!,"讀取資料失敗",R.id.spinner_city)
                    }
                }
            }

        })

        service.F_D0047_091(
            BuildConfig.CWT_SECRET,
            "JSON",
            name,
            null).enqueue(object : retrofit2.Callback<WeatherBean>{
            override fun onFailure(call: retrofit2.Call<WeatherBean>, t: Throwable) {
            }

            override fun onResponse(
                call: retrofit2.Call<WeatherBean>,
                response: retrofit2.Response<WeatherBean>
            ) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        if(isAdded){
                            activity!!.runOnUiThread {
                                initHead(WeatherUtil.getFirstWeather(response.body()!!))
                            }
                        }

                    }
                } else{
                }
            }

        })

    }

    private fun initHead(weatherNode: WeatherNode) {
        val load = ResourcesCompat.getDrawable(resources, WeatherUtil.getWeatherImage(weatherNode.wxValue), null);
        Glide.with(mainView?.context!!)
            .load(load)
            .into(first_status!!)
        tv_1!!.text = weatherNode.minT.toString() + "~" + weatherNode.maxT + "°C"
        tv_2!!.text = weatherNode.wx
        val formatter =
            SimpleDateFormat("MM/dd", Locale("zh", "TW"))
        val dt = weatherNode.calendar.time
        val time = formatter.format(dt)
        val week = WeatherUtil.getWeekOfDayByNum(
            weatherNode.calendar[Calendar.DAY_OF_WEEK]
        )

        tv_city!!.text = CityItem.getCityNameByID(city)
        tv_date!!.text = "$time $week"

    }

    private fun initLayout(weatherBean: WeatherBean) {
        main_nested!!.visibility = View.VISIBLE
        recyclerView!!.adapter = WeatherRecyclerviewAdapter(
            context,
            WeatherUtil.sortWeather(weatherBean)
        )
    }

    private inner class WeatherRecyclerviewAdapter(
        var context: Context?,
        var weatherNodeList: List<WeatherNode>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val mInflater = LayoutInflater.from(context)
            val view: View
            val holder: RecyclerView.ViewHolder
            view = mInflater.inflate(R.layout.cardview_weather, parent, false)
            holder = WeatherViewHolder(view)
            return holder
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            if (holder is WeatherViewHolder) {
                val formatter =
                    SimpleDateFormat("yyyy-MM-dd", Locale("zh", "TW"))
                val dt = weatherNodeList[position].calendar.time
                val time = formatter.format(dt)
                val day =
                    weatherNodeList[position].calendar[Calendar.DAY_OF_WEEK]
                        .toString()
                val week = WeatherUtil.getWeekOfDayByNum(
                    weatherNodeList[position].calendar[Calendar.DAY_OF_WEEK]
                )
                holder.bind(
                    week,
                    weatherNodeList[position].minT,
                    weatherNodeList[position].maxT,
                    weatherNodeList[position].wx,
                    weatherNodeList[position].wxValue
                )
            }
        }

        override fun getItemCount(): Int {
            return weatherNodeList.size
        }

        inner class WeatherViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            private val tw_week: TextView = itemView.findViewById(R.id.week_t)
            private val tv_minT: TextView = itemView.findViewById(R.id.min_w)
            private val tv_maxT: TextView = itemView.findViewById(R.id.max_w)
            private val tv_rain: TextView = itemView.findViewById(R.id.tv_wx)
            private val iv_status: ImageView = itemView.findViewById(R.id.iv_status)
            fun bind(
                time: String,
                minT: Int,
                maxT: Int,
                wx: String,
                wxValue: Int
            ) {
                tw_week.text = time
                tv_minT.text = minT.toString()
                tv_maxT.text = maxT.toString()
                tv_rain.text = wx
                Glide.with(context!!)
                    .load(resources.getDrawable(WeatherUtil.getWeatherImage(wxValue)))
                    .into(iv_status)
            }

        }

    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        fun newInstance(param1: Int): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}