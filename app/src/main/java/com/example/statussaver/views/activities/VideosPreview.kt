package com.example.statussaver.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.statussaver.R
import com.example.statussaver.databinding.ActivityVideosPreviewBinding
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.Constants
import com.example.statussaver.views.adapters.ImagePreviewAdapter
import com.example.statussaver.views.adapters.VideoPreviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideosPreview : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityVideosPreviewBinding.inflate(layoutInflater)
    }
    lateinit var adapter :VideoPreviewAdapter
    private val TAG ="VideosPreview"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            val list=intent.getSerializableExtra(Constants.MEDIA_LIST_KEY ) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY,0)
            adapter= VideoPreviewAdapter(list,activity)
            videoRecyclerView.adapter=adapter
            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(videoRecyclerView)
            videoRecyclerView.scrollToPosition(scrollTo)

            videoRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                          stopAllPlayers()
                    }
                }
            })

        }
    }
    private  fun stopAllPlayers(){
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                binding.apply {
                    for(i in 0 until videoRecyclerView.childCount){
                        val child = videoRecyclerView.getChildAt(i)
                        val viewHolder = videoRecyclerView.getChildViewHolder(child)
                        if (viewHolder is VideoPreviewAdapter.ViewHolder){
                            viewHolder.stopPlayer()
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopAllPlayers()
    }

    override fun onDestroy() {
        super.onDestroy()
           stopAllPlayers()
    }
}