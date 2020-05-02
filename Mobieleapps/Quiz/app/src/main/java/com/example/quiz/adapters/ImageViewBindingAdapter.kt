package com.example.quiz.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageViewBindingAdapter {
    @BindingAdapter("imageRemoteUri")
    @JvmStatic
    fun getImageRemoteUri(imageView: ImageView, imageUri: String){
        Log.d(
            ImageViewBindingAdapter::class.java.name,
            "bindingAdapter called: setImageUri"
        )

        Glide.with(imageView.context)
            .load(imageUri)
            .into(imageView)
    }
}