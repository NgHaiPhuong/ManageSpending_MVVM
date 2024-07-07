package com.example.managespending.databinding

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException

@BindingAdapter("bind:imageUrl")
fun loadImage(view: CircleImageView, url: String?) {
    Glide.with(view.context)
        .load(Uri.parse("file:///android_asset/$url"))
        .error(Uri.parse("file:///android_asset/spend/car.png")).into(view)
}
