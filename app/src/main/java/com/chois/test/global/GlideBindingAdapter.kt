package com.chois.test.global

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
object GlideBindingAdapter {

    @BindingAdapter("lowImageUrl")
    @JvmStatic
    fun loadLowImage(imageView: ImageView, imageUrl: String?) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .override(200, 200)
            .thumbnail(0.1f)
            .into(imageView)
    }

    @BindingAdapter("highImageUrl")
    @JvmStatic
    fun loadHighImage(imageView: ImageView, imageUrl: String?) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .thumbnail(0.3f)
            .into(imageView)
    }
}