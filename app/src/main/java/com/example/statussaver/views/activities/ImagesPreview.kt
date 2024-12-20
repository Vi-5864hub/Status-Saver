package com.example.statussaver.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.statussaver.R
import com.example.statussaver.databinding.ActivityImagesPreviewBinding
import com.example.statussaver.models.MediaModel
import com.example.statussaver.utils.Constants
import com.example.statussaver.views.adapters.ImagePreviewAdapter

class ImagesPreview : AppCompatActivity() {
    private val activity = this
    private val binding  by lazy {
        ActivityImagesPreviewBinding.inflate(layoutInflater)
    }
 lateinit var adapter:ImagePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
         val list=intent.getSerializableExtra(Constants.MEDIA_LIST_KEY ) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY,0)
            adapter= ImagePreviewAdapter(list,activity)
            imagesViewPager.adapter=adapter
            imagesViewPager.setCurrentItem(scrollTo)
        }
    }
}