package com.example.statussaver.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaver.R
import com.example.statussaver.databinding.ItemMediaBinding
import com.example.statussaver.models.MEDIA_TYPE_IMAGE
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.saveStatus
import com.example.statussaver.views.activities.ImagesPreview
import com.example.statussaver.views.activities.VideosPreview

class MediaAdapter(val list :ArrayList<MediaModel> , val context: Context):RecyclerView.Adapter<MediaAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:ItemMediaBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(mediaModel: MediaModel){
            binding.apply {
                Glide.with(context)
                    .load(mediaModel.pathUri.toUri())
                    .into(statusImage)
                if (mediaModel.type == MEDIA_TYPE_IMAGE){
                    statusPlay.visibility=View.GONE
                }
                val downloadImage= if (mediaModel.isDownloaded){
                    R.drawable.ic_downloded
                }else {
                     R.drawable.ic_download
                }
                statusDownload.setImageResource(downloadImage)
                cardStatus.setOnClickListener {
                    if (mediaModel.type == MEDIA_TYPE_IMAGE){
                        //get image preview activity
                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY,list)
                            putExtra(Constants.MEDIA_SCROLL_KEY,layoutPosition)
                            setClass(context,ImagesPreview::class.java)
                            context.startActivity(this)
                        }
                    }else {
                        // get video preview activity
                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY,list)
                            putExtra(Constants.MEDIA_SCROLL_KEY,layoutPosition)
                            setClass(context,VideosPreview::class.java)
                            context.startActivity(this)
                        }
                    }
                }

                statusDownload.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded){
                        //status is downloaded
                        Toast.makeText(context," Saved",Toast.LENGTH_SHORT).show()
                        mediaModel.isDownloaded=true
                        statusDownload.setImageResource(R.drawable.ic_downloded)
                    }
                    else{
                        Toast.makeText(context,"unable to Save",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val model = list[position]
        holder.bind(model)
    }
}