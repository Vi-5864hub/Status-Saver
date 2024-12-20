package com.example.statussaver.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.statussaver.data.StatusRepo
import com.example.statussaver.models.MEDIA_TYPE_IMAGE
import com.example.statussaver.models.MEDIA_TYPE_VIDEO
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.SharedPrefKeys
import com.example.statussaver.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModel(val repo:StatusRepo):ViewModel() {

    private val wpStateLiveData  get() = repo.whatAppStatusesLiveData

    private val wpBusinessStatusLiveData get() = repo.whatsAppBusinessStatusesLiveData

 // wp main
    val whatsAppImageLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsAppVideosLiveData =MutableLiveData<ArrayList<MediaModel>>(
    )

  // wp business
    val whatsAppBusinessImageLiveData=MutableLiveData<ArrayList<MediaModel>>()
    val whatsAppBusinessVideosLiveData =MutableLiveData<ArrayList<MediaModel>>()

    private var isPermissionGranted = false

    init {
        SharedPrefUtils.init(repo.context)

        val wpPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED,false)
        val wpBusinessPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED,false)

        isPermissionGranted=wpPermission && wpBusinessPermission

        if (isPermissionGranted){
            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses()
            }
            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }
        }

    }
    fun getWhatsAppStatuses(){
        CoroutineScope(Dispatchers.IO).launch {
            if (!isPermissionGranted){
                repo.getAllStatuses()
            }
            withContext(Dispatchers.Main){
                getWhatsAppImages()
                getWhatsAppVideos()
            }

        }
    }

    fun getWhatsAppImages(){
        wpStateLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_IMAGE){
                    tempList.add(mediaModel)
                }
            }
            whatsAppImageLiveData.postValue(tempList)
        }
    }
    fun getWhatsAppVideos(){
        wpStateLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_VIDEO){
                    tempList.add(mediaModel)
                }
            }
            whatsAppVideosLiveData.postValue(tempList)
        }
    }




    fun getWhatsAppBusinessStatuses(){
        CoroutineScope(Dispatchers.IO).launch {
            if (!isPermissionGranted){
                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }
            withContext(Dispatchers.Main){
                getWhatsAppBusinessImages()
                getWhatsAppBusinessVideos()
            }

        }
    }

    fun getWhatsAppBusinessImages(){
        wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_IMAGE){
                    tempList.add(mediaModel)
                }
            }
            whatsAppBusinessImageLiveData.postValue(tempList)
        }
    }
    fun getWhatsAppBusinessVideos(){
        wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_VIDEO){
                    tempList.add(mediaModel)
                }
            }
            whatsAppBusinessVideosLiveData.postValue(tempList)
        }
    }
}