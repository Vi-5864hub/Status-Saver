package com.example.statussaver.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.R
import com.example.statussaver.data.StatusRepo
import com.example.statussaver.databinding.FragmentStatusBinding
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.SharedPrefKeys
import com.example.statussaver.utils.SharedPrefUtils
import com.example.statussaver.utils.getFolderPermissions
import com.example.statussaver.viewmodel.StatusViewModel
import com.example.statussaver.viewmodel.factories.StatusViewModelFactory
import com.example.statussaver.views.adapters.MediaViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class FragmentStatus : Fragment() {

    private val binding by lazy {
        FragmentStatusBinding.inflate(layoutInflater)
    }
    private lateinit var  type:String
    private val WHATSAPP_REQUEST_CODE= 101
    private val WHATSAPP_BUSINESS_REQUEST_CODE = 102

    lateinit var viewModel:StatusViewModel

    private val viewPagerTitle = arrayListOf("Images","Videos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
              arguments?.let {
                  val repo = StatusRepo(requireActivity())
                  viewModel =ViewModelProvider(
                      requireActivity(),
                      StatusViewModelFactory(repo)
                  )[StatusViewModel::class.java]

                  type = it.getString(Constants.FRAGMENT_TYPE_KEY,"")
                    when(type){
                        Constants.TYPE_WHATSAPP_MAIN->{
                            // check permission
                            // granted then fetch statuses
                            // get permission
                            // fetch statuses
                            val isPermissionGranted= SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED,false)
                            if (isPermissionGranted){
                                getWhatsappStatuses()
                            }
                            permissionLayout.btnPermission.setOnClickListener {
                                getFolderPermissions(
                                    context = requireActivity(),
                                    REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                    initialUri = Constants.getWhatsappUri()
                                )
                            }

                            val  viewPagerAdapter = MediaViewPagerAdapter(requireActivity())
                            statusViewPager.adapter = viewPagerAdapter
                            TabLayoutMediator(tabLayout,statusViewPager){ tab , pos ->
                                tab.text = viewPagerTitle[pos]
                            }.attach()

                            binding.swipeRefreshLayout.setOnRefreshListener {
                                refreshStatuses()
                            }

                        }
                        Constants.TYPE_WHATSAPP_BUSINESS->{
                            val isPermissionGranted= SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED,false)
                            if (isPermissionGranted){
                               getWhatsappBusinessStatuses()

                                binding.swipeRefreshLayout.setOnRefreshListener {
                                    refreshStatuses()
                                }
                            }
                            permissionLayout.btnPermission.setOnClickListener {
                                getFolderPermissions(
                                    context = requireActivity(),
                                    REQUEST_CODE = WHATSAPP_BUSINESS_REQUEST_CODE,
                                    initialUri = Constants.getWhatsappBusinessUri()
                                )
                            }
                            val  viewPagerAdapter = MediaViewPagerAdapter(
                                requireActivity(),
                                imagesType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES,
                                videosType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS
                                )
                            statusViewPager.adapter = viewPagerAdapter
                            TabLayoutMediator(tabLayout,statusViewPager){ tab , pos ->
                                tab.text = viewPagerTitle[pos]
                            }.attach()
                        }
                    }
              }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =binding.root

    fun refreshStatuses(){
        when(type){
            Constants.TYPE_WHATSAPP_MAIN->{
                Toast.makeText(requireActivity(),"Refresh WP Statuses" , Toast.LENGTH_SHORT).show()
                getWhatsappStatuses()
            }
            else->{
                Toast.makeText(requireActivity(),"Refreshing WP Business Statuses",Toast.LENGTH_SHORT).show()
                getWhatsappBusinessStatuses()
            }
        }
        Handler(Looper.myLooper()!!).postDelayed({
            binding.swipeRefreshLayout.isRefreshing =false
        } ,2000)
    }


    fun getWhatsappStatuses(){
        //function to get wp statuses
        binding.permissionLayoutHolder.visibility = View.GONE
        viewModel.getWhatsAppStatuses()

    }

    fun getWhatsappBusinessStatuses(){
        // function to get wp statuses
        binding.permissionLayoutHolder.visibility = View.GONE
        viewModel.getWhatsAppBusinessStatuses()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == AppCompatActivity.RESULT_OK){
            val treeUri =data?.data!!
            requireActivity().contentResolver.takePersistableUriPermission(
                treeUri,Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (requestCode == WHATSAPP_REQUEST_CODE){
                // whatsapp logic Here
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_TREE_URI,
                    treeUri.toString()
                )
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED,true)
                getWhatsappStatuses()
            } else if (requestCode == WHATSAPP_BUSINESS_REQUEST_CODE){
                // Whatsapp business logic here
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI,
                    treeUri.toString()
                )
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED
                    ,true)
                getWhatsappBusinessStatuses()
            }
        }
    }



}