package com.doug.domain.challenge.ui.properties

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doug.domain.challenge.R
import com.doug.domain.challenge.repository.domain.Property
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_property.view.*

/**
 * Recycler view adapter to display the property list
 */
class PropertiesAdapter : RecyclerView.Adapter<PropertiesAdapter.ViewHolder>() {
    private val properties = mutableListOf<Property>()

    fun setProperties(properties: List<Property>) {
        this.properties.clear()
        this.properties.addAll(properties)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_property, parent, false)
        )
    }

    override fun getItemCount() = properties.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]
        holder.setImage(property.image)
        holder.setPrice(property.price)
        holder.setDetails(property.beds, property.baths, property.carSpaces)
        holder.setAddress(property.address)
        holder.setAgencyLogo(property.agencyLogo)
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun setImage(imageUrl: String?) {
            if (imageUrl?.isNotEmpty() == true) {
                // Use picasso to load the image and set a placeholder on the image view
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_img_placeholder)
                    .centerCrop()
                    .fit()
                    .into(itemView.propertyImageView)
            }
        }

        fun setPrice(price: String) {
            itemView.priceTextView.text = price
        }

        fun setDetails(beds: Int, baths: Int, carSpaces: Int) {
            itemView.detailsTextView.text = itemView.context.getString(
                R.string.details_text,
                beds,
                baths,
                carSpaces
            )
        }

        fun setAddress(address: String) {
            itemView.addressTextView.text = address
        }

        fun setAgencyLogo(imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(itemView.agencyLogoImageView)
            }
        }
    }
}
