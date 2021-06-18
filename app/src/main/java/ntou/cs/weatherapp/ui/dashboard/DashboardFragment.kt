package ntou.cs.weatherapp.ui.dashboard

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ntou.cs.weatherapp.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)



        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

            val tv_app_version: TextView = root.findViewById(R.id.tv_app_version)
            val tv_app_versioncode: TextView = root.findViewById(R.id.tv_app_versioncode)
            try {
                val pInfo: PackageInfo = activity?.packageManager!!.getPackageInfo(activity?.packageName!!, 0)
                val appVersion = pInfo.versionName
                tv_app_version.text = appVersion
                val verCode = pInfo.versionCode
                tv_app_versioncode.text = verCode.toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }



        })
        return root
    }
}