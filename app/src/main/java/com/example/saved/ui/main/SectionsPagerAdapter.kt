package com.example.saved.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.saved.R

private val TAB_TITLES = arrayOf(
    "Details",
    "Live Posts"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    var id : String = ""

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a InfoFragment (defined as a static inner class below).

        val f = if (position == 0) {
            InfoFragment()
        } else {
            TweetFragment()
        }

        val bundle = Bundle()
        bundle.putString("id", id)
        f.setArguments(bundle)

        return f
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}