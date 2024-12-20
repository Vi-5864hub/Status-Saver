package com.example.statussaver.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.statussaver.R
import com.example.statussaver.databinding.FragmentSettingsBinding
import com.example.statussaver.models.SettingsModel
import com.example.statussaver.views.adapters.SettingsAdapter


class FragmentSettings : Fragment() {

    private val binding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }

     private val list = ArrayList<SettingsModel>()
    private val adapter by lazy {
        SettingsAdapter(list,requireActivity())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding.apply {
        settingsRecycleView.adapter=adapter
          list.add(
              SettingsModel(

                  title = "How to use",
                  desc = "Know how to download statuses"
              )
          )
          list.add(
              SettingsModel(
                  title = "Save in Folder",
                  desc = "/internalStorage/Document/${"Status Saver"}"
              )
          )
          list.add(
              SettingsModel(
                  title = "Disclaimer",
                  desc = "Read Our Disclaimer"
              )
          )
          list.add(
              SettingsModel(
                  title = "Privacy Policy",
                  desc = "Read Our Terms & Conditions"
              )
          )
          list.add(
              SettingsModel(
                  title = "Share",
                  desc = "Sharing is caring"
              )
          )
          list.add(
              SettingsModel(
                  title = "Rate Us",
                  desc = "Please support our work by rating on PlayStare "
              )
          )

      }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root



}