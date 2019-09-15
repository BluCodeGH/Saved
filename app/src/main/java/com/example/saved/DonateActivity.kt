package com.example.saved

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.blockmason.link.Project
import org.json.JSONObject
import java.math.BigDecimal
import java.math.BigInteger

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class DonateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        if (supportActionBar != null)
            supportActionBar?.hide()

        val personNames = arrayOf("CAD", "USD", "CHF", "EUR", "JPY", "AUD", "YEN")
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@DonateActivity, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
    }



    fun donate(view: View) {
        class transfer() : AsyncTask<Double, Void, String>() {
            override fun doInBackground(vararg params: Double?): String? {
                val proj = Project.at("G0mLtXJAP_7fxN-IKCb5IuG1jEBYduM5zgRSdkqXkQk", "ywG25A0hNrO6zFd+Gc2Rf5XONsIx0B+IF92dhFRRQaIB4GX7gqfzLziCh76Rq1Z")
                val inputs = JSONObject()
                inputs.put("_to", "0xd06fb1891a51cc3f16cccc289cbf9ce347386809")
                inputs.put("_value", BigDecimal("1000000000000000000").multiply(BigDecimal(params[0].toString())).toBigInteger().toString(16))
                val out = proj.post("/transfer", inputs)
                Log.d("ME", out.toString())
                return null
            }
        }
        // Instantiate the RequestQueue.
        val amnt = findViewById<EditText>(R.id.donation).text.toString().toDouble()
        val queue = Volley.newRequestQueue(this)
        val url = "http://35.226.38.9:3389/convert/cad/usd"
        Log.d("ME", url)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Log.d("ME", response.toString())
                    val amnt = response.toString().toDouble() * amnt / 100.0
                    Log.d("ME", amnt.toString())
                    transfer().execute(amnt)
                },
                Response.ErrorListener { Log.e("ME", it.toString()) })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
