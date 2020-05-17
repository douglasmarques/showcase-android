package com.doug.showcase.photo.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.doug.showcase.R
import com.doug.showcase.photo.repository.domain.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * Recycler view adapter to display the photo list
 */
class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photos = mutableListOf<Photo>()

    fun setPhotos(photos: List<Photo>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        holder.setImage(photo.url, photo.title)
        holder.setUpItemClickListener(photo.title)
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun setImage(imageUrl: String?, title: String?) {
            if (imageUrl?.isNotEmpty() == true) {
                // Use picasso to load the image
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_img_placeholder)
                    .centerCrop()
                    .fit()
                    .into(itemView.photoImageView)
            }
            itemView.photoImageView.contentDescription = title
        }

        fun setUpItemClickListener(title: String) {
            itemView.setOnLongClickListener {
                Toast.makeText(itemView.context, title, Toast.LENGTH_SHORT).show()
                true
            }
        }
    }
}
