package com.example.saved

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.blockmason.link.Project
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        if (supportActionBar != null)
            supportActionBar?.hide()
        val personNames = arrayOf("+", "Earthquake(Green)", "Explosion(Yellow)", "Fire(Red)", "Floods(Cyan)", "Terrorism(Black)", "Typhoon(Blue)", "Crash(Magenta)")
        val spinner = findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        this@MapsActivity,
                        getString(R.string.selected_item) + " " + personNames[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        val options = GoogleMapOptions()
        options.compassEnabled(false)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(false)
            .zoomControlsEnabled(true)
        val mapFragment = MapFragment.newInstance(options)
        val fragmentTransaction = getFragmentManager().beginTransaction()
        fragmentTransaction.add(R.id.map, mapFragment)
        fragmentTransaction.commit()
        mapFragment.getMapAsync(this)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int, color: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            setColorFilter(PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP))
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        myRef.child("incidents").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                mMap.clear()
                for (child in dataSnapshot.getChildren()) {
                    val id = child.getKey() as String;
                    val lat = child.child("lat").getValue() as Double
                    val long = child.child("long").getValue() as Double
                    val type = child.child("type").getValue() as String

                    Log.d("ME", type)

                    val color = when (type) {
                        "crash" -> Color.MAGENTA
                        "earthquake" -> Color.GREEN
                        "explosion" -> Color.YELLOW
                        "fire" -> Color.RED
                        "floods" -> Color.CYAN
                        "terrorism" -> Color.BLACK
                        "typhoon" -> Color.BLUE
                        else -> Color.BLACK
                    }

                    addMarker(LatLng(lat, long), color, id)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ME", "Failed to read value.", error.toException())
            }
        })

        mMap.setOnMarkerClickListener(this)
        mMap.setPadding(0, 0, 200, 0)
    }

    fun addMarker(where: LatLng, color: Int, id: String) {
        mMap.addMarker(MarkerOptions().position(where).icon(bitmapDescriptorFromVector(this, R.drawable.ic_album_black_24dp, color)).anchor(0.5f, 0.5f)).setTag(id)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("MARKER", marker?.getTag() as String)
        startActivity(intent)
        return true
    }

    fun sub(view: View) {
        val intent = Intent(this, SubscribeActivity::class.java)
        startActivity(intent)
    }
}
