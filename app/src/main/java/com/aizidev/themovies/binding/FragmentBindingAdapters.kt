package com.aizidev.themovies.binding

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aizidev.themovies.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
//@OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(fragment).load(url).listener(listener).into(imageView)
    }

    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_icon_no_image_s)
                .into(view)
        }
    }
}