package com.mobileprogramming.tp2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import androidx.appcompat.widget.Toolbar
import android.util.Log


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: LocationListAdapter
    private var dataList: List<LocationData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val filterInput: EditText = findViewById(R.id.filterInput)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        adapter = LocationListAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        filterInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val filter = s.toString()
                val filteredList = dataList.filter { it.name.contains(filter, ignoreCase = true) }
                adapter.updateData(filteredList)
            }
        })

        fetchData()
    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://webapi.akebono-astra.co.id/EVSE/location.php"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonArray = JSONArray(response)
            val tempList = mutableListOf<LocationData>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                val locationData = LocationData(
                    name = jsonObject.getString("name"),
                    address = jsonObject.getString("address"),
                    imageLocation = jsonObject.getString("image_location"),
                    latitude = jsonObject.getDouble("latitude"),
                    longitude = jsonObject.getDouble("longitude")
                )
                tempList.add(locationData)
            }
            dataList = tempList
            adapter.updateData(dataList)
        }, { error ->
            // Handle error
        })

        queue.add(stringRequest)
    }
}
