package com.cardee.renter_car_details.view.viewholder

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cardee.R
import com.cardee.domain.owner.entity.Image
import com.cardee.renter_car_details.view.RenterCarDetailsActivity

class RenterCarDetailsViewHolder(private val mActivity: RenterCarDetailsActivity) {

    private val mGlideRequestManager : RequestManager? = Glide.with(mActivity)

    fun bind() {

    }

    private inner class ImagePagerAdapter private constructor(context: Context, images: Array<Image>?) : PagerAdapter() {

        @DrawableRes
        private val mDefaultImageId = R.drawable.img_no_car
        private val mInflater: LayoutInflater = LayoutInflater.from(context)
        private var mImages: Array<Image>? = null
        internal var mEmpty = true

        init {
            if (images == null || images.isEmpty()) {
                mEmpty = true
            } else {
                mImages = images
                mEmpty = false
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = mInflater.inflate(R.layout.item_image_slide, container, false)
            val imageView = itemView.findViewById<ImageView>(R.id.image_slide)
            val progressBar = itemView.findViewById<View>(R.id.image_progress_bar)
            if (!mEmpty) {
                progressBar.visibility = View.VISIBLE
                mGlideRequestManager?.
                        load(mImages?.get(position)?.link)?.
                        listener(object : RequestListener<String, GlideDrawable> {
                            override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        ?.error(mDefaultImageId)
                        ?.into(imageView)
            } else {
                imageView.setImageResource(mDefaultImageId)
            }
            container.addView(itemView)
            return itemView
        }

        private fun needRefresh(newImages: Array<Image>): Boolean {
            return mImages == null || mImages?.size != newImages.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return if (mEmpty) 1 else mImages!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}

