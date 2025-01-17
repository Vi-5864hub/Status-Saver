package com.example.statussaver.views.adapters

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.statussaver.R
import com.example.statussaver.databinding.DialogGuideBinding
import com.example.statussaver.databinding.ItemSettingsBinding
import com.example.statussaver.models.SettingsModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsAdapter(var list :ArrayList<SettingsModel> ,var context:Context) :
RecyclerView.Adapter<SettingsAdapter.viewHolder>(){


 inner   class  viewHolder(var binding: ItemSettingsBinding):RecyclerView.ViewHolder(binding.root){
          fun bind(model: SettingsModel,position: Int){
              binding.apply {
                  settingsTitle.text=model.title
                  settingDesc.text=model.desc

                  root.setOnClickListener {
                      when(position) {
                          0 -> {
                              val dialog = Dialog(context)
                              val dialogBinding =
                                  DialogGuideBinding.inflate((context as Activity).layoutInflater)
                              dialogBinding.okayBtn.setOnClickListener {
                                  dialog.dismiss()
                              }
                              dialog.setContentView(dialogBinding.root)
                              dialog.window?.setLayout(
                                  ActionBar.LayoutParams.MATCH_PARENT,
                                  ActionBar.LayoutParams.WRAP_CONTENT
                              )
                              dialog.show()
                          }

                          2 -> {
                              MaterialAlertDialogBuilder(context).apply {
                                  setTitle("Disclaimer")
                                  setMessage("Disclaimer More")
                                  show()
                              }
                          }
                          3->{
                              Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id")).apply {
                                  context.startActivity(this)
                              }
                          }
                          4->{
                              Intent(Intent.ACTION_SEND).apply {
                                  type="text/plain"
                                  putExtra(Intent.EXTRA_SUBJECT,context.getString(R.string.app_name))
                                  putExtra(Intent.EXTRA_TEXT,"My App is soo cool please download it:https://play.google.com/store/apps/details?id=${context.packageName}")
                                   context.startActivity(this)
                              }

                          }
                          5->{
                              Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id" +context.packageName)).apply {
                                  context.startActivity(this)
                              }
                          }
                      }
                  }
              }
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemSettingsBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    override fun getItemCount()=list.size


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(model = list[position], position)
    }
}