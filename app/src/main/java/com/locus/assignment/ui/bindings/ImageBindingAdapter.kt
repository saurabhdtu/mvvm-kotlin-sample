package com.locus.assignment.ui.bindings

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.locus.assignment.R
import java.io.File

object ImageBindingAdapter {

    @BindingAdapter("image")
    @JvmStatic
    fun loadImage(imageView: ImageView, image: String?) {
        if (image == null) {
            imageView.setImageResource(R.drawable.ic_camera)
        } else {

            Glide.with(imageView)
                .load(Uri.fromFile(File(image)))
                .centerInside().into(imageView)
        }
    }
}