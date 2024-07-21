package com.mobileprogramming.tp2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class LocationListAdapter(private var items: List<LocationData>) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    fun updateData(newItems: List<LocationData>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.arrowImageView.setImageResource(R.drawable.ic_arrow_right) // Assuming you have an arrow icon
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("imageLocation", item.imageLocation)
                putExtra("name", item.name)
                putExtra("address", item.address)
                putExtra("latitude", item.latitude)
                putExtra("longitude", item.longitude)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val arrowImageView: ImageView = view.findViewById(R.id.arrowImageView)
    }
}

