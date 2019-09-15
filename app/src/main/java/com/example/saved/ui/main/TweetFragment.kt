package com.example.saved.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.saved.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TweetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TweetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TweetFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_tweet, container, false)
        val id = arguments?.getString("id") as String

        viewManager = LinearLayoutManager(getContext())
        viewAdapter = TweetAdapter(id)

        recyclerView = rootView.findViewById<RecyclerView>(R.id.tweetrecycler).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        return rootView
    }
}
