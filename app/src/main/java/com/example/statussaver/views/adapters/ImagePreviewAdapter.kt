package com.example.statussaver.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaver.R
import com.example.statussaver.databinding.ItemImagePreviewBinding
import com.example.statussaver.databinding.ItemMediaBinding
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.saveStatus

class ImagePreviewAdapter(val list :ArrayList<MediaModel>, val context: Context):
    RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: ItemImagePreviewBinding):RecyclerView.ViewHolder(binding.root){
      fun bind(mediaModel: MediaModel){
          binding.apply {
              Glide.with(context)
                  .load(mediaModel.pathUri.toUri())
                  .into(zoomableImageView)
          val downloadImage = if (mediaModel.isDownloaded){
              R.drawable.ic_downloded
          }else{
              R.drawable.ic_download
          }
              tools.statusDownload.setImageResource(downloadImage)

              tools.download.setOnClickListener {
                  val isDownloaded = context.saveStatus(mediaModel)
                  if (isDownloaded){
                      //status is downloaded
                      Toast.makeText(context," Saved", Toast.LENGTH_SHORT).show()
                      mediaModel.isDownloaded=true
                    tools.statusDownload.setImageResource(R.drawable.ic_downloded)
                  }
                  else{
                      Toast.makeText(context,"unable to Save", Toast.LENGTH_SHORT).show()
                  }

              }
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemImagePreviewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: ImagePreviewAdapter.ViewHolder, position: Int) {
          val model = list[position]
        holder.bind(model)
    }

}