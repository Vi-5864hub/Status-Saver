package com.example.statussaver.views.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.statussaver.R
import com.example.statussaver.databinding.ActivityMainBinding
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.SharedPrefKeys
import com.example.statussaver.utils.SharedPrefUtils
import com.example.statussaver.utils.replaceFragment
import com.example.statussaver.utils.slideFromStart
import com.example.statussaver.utils.slideToEndWithFadeOut
import com.example.statussaver.views.fragments.FragmentSettings
import com.example.statussaver.views.fragments.FragmentStatus

class MainActivity : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        SharedPrefUtils.init(activity)
        binding.apply {
            splashLogic()
            requestPermission()
            val fragmentWhatsAppStatus = FragmentStatus()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TYPE_KEY,Constants.TYPE_WHATSAPP_MAIN)
            replaceFragment(fragmentWhatsAppStatus,bundle)
            toolBar.setNavigationOnClickListener {
                drawerLayout.open()
            }
               navigationV.setNavigationItemSelectedListener {
                   when(it.itemId){
                        R.id.menu_Status->{
                           //whatsapp status
                           val fragmentWhatsAppStatus = FragmentStatus()
                           val bundle = Bundle()
                           bundle.putString(Constants.FRAGMENT_TYPE_KEY,Constants.TYPE_WHATSAPP_MAIN)
                           replaceFragment(fragmentWhatsAppStatus,bundle)
                            drawerLayout.close()
                       }
                       R.id.menu_business_status->{
                           //whatsapp business status
                           val fragmentWhatsAppStatus = FragmentStatus()
                           val bundle = Bundle()
                           bundle.putString(Constants.FRAGMENT_TYPE_KEY,Constants.TYPE_WHATSAPP_BUSINESS)
                           replaceFragment(fragmentWhatsAppStatus,bundle)
                           drawerLayout.close()
                       }
                       R.id.menu_settings->{
                           //setting
                           replaceFragment(FragmentSettings())
                           drawerLayout.close()
                       }
                   }
                   return@setNavigationItemSelectedListener true
               }
        }
    }
    private val PERMISSION_REQUEST_CODE =50

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val isPermissionGranted = SharedPrefUtils.getPrefBoolean(
                SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                false
            )
            if (!isPermissionGranted) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
                Toast.makeText(activity, "Please Grant Permission ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
             if (requestCode == PERMISSION_REQUEST_CODE){
                 val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                 if (isGranted){
                  SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,true)
                 }else {
                       SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,false)
                 }
             }
    }

    private fun splashLogic() {
        binding.apply {
            splashLayout.cardView.slideFromStart()
            Handler(Looper.myLooper()!!).postDelayed({
                splashScreenHolder.slideToEndWithFadeOut()
                splashScreenHolder.visibility=View.GONE
            },2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.fragment_container)
        fragment?.onActivityResult(requestCode,resultCode,data)
    }
}