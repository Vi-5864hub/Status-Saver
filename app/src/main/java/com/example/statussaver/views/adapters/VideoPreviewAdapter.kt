package com.example.statussaver.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaver.R
import com.example.statussaver.databinding.ItemImagePreviewBinding
import com.example.statussaver.databinding.ItemMediaBinding
import com.example.statussaver.databinding.ItemVideoPreviewBinding
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.saveStatus

class VideoPreviewAdapter(val list :ArrayList<MediaModel>, val context: Context):
    RecyclerView.Adapter<VideoPreviewAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: ItemVideoPreviewBinding):RecyclerView.ViewHolder(binding.root){
      fun bind(mediaModel: MediaModel){
          binding.apply {
                val player = ExoPlayer.Builder(context).build()
              playerView.player = player
              val mediaItem = MediaItem.fromUri(mediaModel.pathUri)

              player.setMediaItem(mediaItem)

              player.prepare()



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
        fun stopPlayer(){
            binding.playerView.player?.stop()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVideoPreviewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: VideoPreviewAdapter.ViewHolder, position: Int) {
          val model = list[position]
        holder.bind(model)
    }

}