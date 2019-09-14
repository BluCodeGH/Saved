package com.example.saved

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import java.security.AccessController.getContext

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

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
                for (child in dataSnapshot.getChildren()) {
                    val lat = child.child("lat").getValue() as Double
                    val long = child.child("long").getValue() as Double
                    val type = child.child("type").getValue() as String

                    Log.d("ME", type)

                    val color = when (type) {
                        "earthquake" -> Color.GREEN
                        "fire" -> Color.RED
                        "hurricane" -> Color.BLUE
                        "tornado" -> Color.WHITE
                        else -> Color.BLACK
                    }

                    addMarker(LatLng(lat, long), color)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ME", "Failed to read value.", error.toException())
            }
        })

        mMap.setOnMarkerClickListener(this)
    }

    fun addMarker(where: LatLng, color: Int) {
        mMap.addMarker(MarkerOptions().position(where).icon(bitmapDescriptorFromVector(this, R.drawable.ic_album_black_24dp, color)).anchor(0.5f, 0.5f))
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        //val intent = Intent(this, DetailActivity::class.java)
        val intent = Intent(this, SubscribeActivity::class.java)
        startActivity(intent)
        return true
    }
}
