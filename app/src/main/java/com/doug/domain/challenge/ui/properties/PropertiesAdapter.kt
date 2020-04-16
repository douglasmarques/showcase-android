package com.doug.domain.challenge.ui.properties

import android.graphics.Color
import android.util.Log
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
        holder.setBeds(property.beds)
        holder.setBaths(property.baths)
        holder.setParking(property.parking)
        holder.setAddress(property.address)
        holder.setAgencyLogo(property.agencyLogo, property.agencyColor)
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun setImage(imageUrl: String?) {
            if (imageUrl?.isNotEmpty() == true) {
                // Use picasso to load the image
                Picasso.get()
                    .load(imageUrl)
                    .centerCrop()
                    .fit()
                    .into(itemView.propertyImageView)
            }
        }

        fun setPrice(price: String) {
            itemView.priceTextView.text = price
        }

        fun setBeds(beds: Int) {
            itemView.bedsTextView.text = beds.toString()
        }

        fun setBaths(baths: Int) {
            itemView.bathsTextView.text = baths.toString()
        }

        fun setParking(carSpaces: Int) {
            if (carSpaces > 0) {
                itemView.visibility = View.GONE
            } else {
                itemView.visibility = View.VISIBLE
                itemView.parkingTextView.text = carSpaces.toString()
            }
        }

        fun setAddress(address: String) {
            itemView.addressTextView.text = address
        }

        fun setAgencyLogo(imageUrl: String, color: String) {
            if (color.isNotEmpty()) {
                try {
                    itemView.agencyLogoImageView.setBackgroundColor(Color.parseColor(color))
                    // some colors are displayed on short Hex code like #fff and parseColor
                    // doesn't resolve them
                } catch (ex: IllegalArgumentException) {
                    Log.e("PropertiesAdapter", "invalid color: $color", ex)
                }
            }
            if (imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .into(itemView.agencyLogoImageView)
            }
        }
    }
}
