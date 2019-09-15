package com.example.saved.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.saved.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * A placeholder fragment containing a simple view.
 */
class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val id = arguments?.getString("id") as String
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        myRef.child("incidents").child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val headline = dataSnapshot.child("headline").getValue() as String;
                val body = dataSnapshot.child("body").getValue() as String;
                view.findViewById<TextView>(R.id.title).setText(headline)
                view.findViewById<TextView>(R.id.body).setText(body)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ME", "Failed to read value.", error.toException())
            }
        })
        return view
    }
}