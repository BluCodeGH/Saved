package com.example.saved

import android.content.Context
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        addMarker(sydney, Color.BLUE)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 0f))
        mMap.setOnMarkerClickListener(this)
    }

    fun addMarker(where: LatLng, color: Int) {
        mMap.addMarker(MarkerOptions().position(where).icon(bitmapDescriptorFromVector(this, R.drawable.ic_album_black_24dp, color)).anchor(0.5f, 0.5f))
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
        return true
    }
}
