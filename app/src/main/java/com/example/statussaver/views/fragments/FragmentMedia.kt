package com.example.statussaver.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.R
import com.example.statussaver.data.StatusRepo
import com.example.statussaver.databinding.FragmentMediaBinding
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.Constants
import com.example.statussaver.viewmodel.StatusViewModel
import com.example.statussaver.viewmodel.factories.StatusViewModelFactory
import com.example.statussaver.views.adapters.MediaAdapter


class FragmentMedia : Fragment() {

    private val binding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }

    lateinit var viewmodel :StatusViewModel
    lateinit var adapter :MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding.apply {
          arguments?.let {

              val repo =StatusRepo(requireActivity())
              viewmodel = ViewModelProvider(
                  requireActivity(),
                  StatusViewModelFactory(repo)
              )[StatusViewModel::class.java]

              val mediaType = it.getString(Constants.MEDIA_TYPE_KEY,"")
             when(mediaType){
                 Constants.MEDIA_TYPE_WHATSAPP_IMAGES->{
                     viewmodel.whatsAppImageLiveData.observe(requireActivity()){unFilteredList->
                         val filteredList = unFilteredList.distinctBy { model->
                             model.fileName
                         }
                         val list =ArrayList<MediaModel>()
                         filteredList.forEach {model->
                             list.add(model)
                         }
                         adapter= MediaAdapter(list,requireActivity())
                         mediaRecyclerView.adapter=adapter
                         if (list.size == 0){
                             tempMediaText.visibility = View.VISIBLE
                         } else {
                             tempMediaText.visibility = View.GONE
                         }
                     }
                 }

                 Constants.MEDIA_TYPE_WHATSAPP_VIDEOS->{
                     viewmodel.whatsAppBusinessVideosLiveData.observe(requireActivity()){unFilteredList->
                         val filteredList = unFilteredList.distinctBy { model->
                             model.fileName
                         }
                         val list =ArrayList<MediaModel>()
                         filteredList.forEach {model->
                             list.add(model)
                         }
                         adapter=MediaAdapter(list,requireActivity())
                         mediaRecyclerView.adapter=adapter
                         if (list.size == 0){
                             tempMediaText.visibility = View.VISIBLE
                         } else {
                             tempMediaText.visibility = View.GONE
                         }
                     }
                 }

                 Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES->{
                     viewmodel.whatsAppBusinessImageLiveData.observe(requireActivity()){unFilteredList->
                         val filteredList = unFilteredList.distinctBy { model->
                             model.fileName
                         }
                         val list =ArrayList<MediaModel>()
                         filteredList.forEach {model->
                             list.add(model)
                         }
                         adapter=MediaAdapter(list,requireActivity())
                         mediaRecyclerView.adapter=adapter
                         if (list.size == 0){
                             tempMediaText.visibility = View.VISIBLE
                         } else {
                             tempMediaText.visibility = View.GONE
                         }
                     }
                 }

                 Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS->{
                     viewmodel.whatsAppBusinessVideosLiveData.observe(requireActivity()){unFilteredList->
                         val filteredList = unFilteredList.distinctBy { model->
                             model.fileName
                         }
                         val list =ArrayList<MediaModel>()
                         filteredList.forEach {model->
                             list.add(model)
                         }
                         adapter=MediaAdapter(list,requireActivity())
                         mediaRecyclerView.adapter=adapter
                         if (list.size == 0){
                             tempMediaText.visibility = View.VISIBLE
                         } else {
                             tempMediaText.visibility = View.GONE
                         }
                     }
                 }
             }
          }
         }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root



}