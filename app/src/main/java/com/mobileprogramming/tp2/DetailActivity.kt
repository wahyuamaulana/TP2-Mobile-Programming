package com.mobileprogramming.tp2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var detailImage: ImageView
    private lateinit var detailName: TextView
    private lateinit var detailAddress: TextView
    private lateinit var viewLocationButton: Button
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        detailImage = findViewById(R.id.detailImage)
        detailName = findViewById(R.id.detailName)
        detailAddress = findViewById(R.id.detailAddress)
        viewLocationButton = findViewById(R.id.viewLocationButton)

        val intent = intent
        val imageLocation = intent.getStringExtra("imageLocation")
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        Glide.with(this).load(imageLocation).into(detailImage)
        detailName.text = name
        detailAddress.text = address

        viewLocationButton.setOnClickListener {
            openLocationInMaps(latitude, longitude)
        }
    }

    private fun openLocationInMaps(latitude: Double, longitude: Double) {
        val mapUri = Uri.parse("https://maps.google.com/maps?q=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
}