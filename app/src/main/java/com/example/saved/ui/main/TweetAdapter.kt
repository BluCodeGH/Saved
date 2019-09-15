package com.example.saved.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saved.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetAdapter(private val id: String) :
RecyclerView.Adapter<TweetAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    var len : Int = 0
    init {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        myRef.child("incidents").child(id).child("live").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                len = dataSnapshot.childrenCount.toInt()
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ME", "Failed to read value.", error.toException())
            }
        })
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TweetAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_single_tweet, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        myRef.child("incidents").child(id).child("live").child(position.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val feed = dataSnapshot.getValue() as String;
                holder.view.findViewById<TextView>(R.id.body).text = feed
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ME", "Failed to read value.", error.toException())
            }
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int {
        return len
    }
}