package ntou.cs.weatherapp.ui.notification


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import ntou.cs.weatherapp.R
import ntou.cs.weatherapp.database.AppPreference
import ntou.cs.weatherapp.util.CityItem
import ntou.cs.weatherapp.util.Utility.setNextAlarm
import ntou.cs.weatherapp.util.Utility.setPendingIntent
import java.util.*

open class NotificationFragment : Fragment() {
    private var mainView: View? = null
    private var notification_time: TimePicker? = null
    private var spinner_city: Spinner? = null
    private var city = 0
    private var checkbox_group: LinearLayout? = null
    private var savebtn: Button? = null
    private var deletebtn: Button? = null
    var pendingintent: PendingIntent? = null
    var am: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_notification, container, false)
        initView(mainView)
        pendingintent = setPendingIntent(requireContext())
        am = requireContext().getSystemService(Context.ALARM_SERVICE) as?
                AlarmManager?
        return mainView
    }

    private fun initView(view: View?) {

        notification_time = view?.findViewById(R.id.notification_time)
        spinner_city = view?.findViewById(R.id.spinner_city)
        savebtn = view?.findViewById(R.id.notification_savebtn)
        savebtn?.setOnClickListener { save() }
        deletebtn = view?.findViewById(R.id.notification_clearbtn)
        deletebtn?.setOnClickListener { clear() }

        var c: Calendar = Calendar.getInstance()
        c.timeInMillis = AppPreference.getTime(context)!!
        notification_time?.hour = c.get(Calendar.HOUR_OF_DAY)
        notification_time?.minute = c.get(Calendar.MINUTE)

        val spinnerArray = mutableListOf<String>()
        for (i in CityItem.cityList) {
            spinnerArray.add(i.value)
        }


        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_city?.adapter = adapter
        for(i in 0 until CityItem.cityList.size){
            if(CityItem.cityList[i].key == AppPreference.getSelecteCity(context)!!){
                spinner_city?.setSelection(i)
            }
        }
        checkbox_group = view?.findViewById(R.id.checkbox_group)
        checkbox_group?.forEachIndexed(){
            index, view ->
            if (view is CheckBox){
                view.isChecked = AppPreference.getWeekDay(context, view.tag as String)!!
            }
            view.setOnClickListener(View.OnClickListener {
                if (it is CheckBox) {
                AppPreference.setWeekDay(context, it.tag as String,it.isChecked)}
                Log.v("setweekday",it.tag as String)
            })
        }


    }

    private fun savepreference(){
        notification_time = view?.findViewById(R.id.notification_time)
        var c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, notification_time?.hour!!)
        c.set(Calendar.MINUTE, notification_time?.minute!!)
        AppPreference.setTime(context, c.timeInMillis)
        checkbox_group = view?.findViewById(R.id.checkbox_group)
        checkbox_group?.forEachIndexed(){
                _, view ->
                if (view is CheckBox) {
                    AppPreference.setWeekDay(context, view.tag as String,view.isChecked)}
                Log.v("setweekday",view.tag as String)
        }
        spinner_city = view?.findViewById(R.id.spinner_city)
        val position = spinner_city?.selectedItemPosition
        city = CityItem.cityList[position!!].key
        AppPreference.setSelecteCity(context,city)
    }
    private fun clearpreference(){
        notification_time = view?.findViewById(R.id.notification_time)
        var c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,0)
        c.set(Calendar.MINUTE, 0)
        AppPreference.setTime(context, c.timeInMillis)
        notification_time?.hour = 0
        notification_time?.minute = 0
        checkbox_group = view?.findViewById(R.id.checkbox_group)
        checkbox_group?.forEachIndexed(){
                _, view ->
            if (view is CheckBox) {
                AppPreference.setWeekDay(context, view.tag as String,false)
                view.isChecked = false}
            Log.v("setweekday",view.tag as String)
        }
        spinner_city = view?.findViewById(R.id.spinner_city)
        city = CityItem.cityList[0].key
        AppPreference.setSelecteCity(context,city)
        spinner_city?.setSelection(0)
    }
    private fun save(){
        Log.v("save","save")
        savepreference()
        setNextAlarm(requireContext(), am, pendingintent)
        Toast.makeText(requireContext(), "設定成功",
            Toast.LENGTH_SHORT).show();
    }
    private fun clear(){
        am?.cancel(pendingintent);
        clearpreference()
        Toast.makeText(requireContext(), "停止",
            Toast.LENGTH_SHORT).show();
    }
}