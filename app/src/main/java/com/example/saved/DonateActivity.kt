package com.example.saved

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import io.blockmason.link.Project
import org.json.JSONObject
import java.math.BigInteger

class DonateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        if (supportActionBar != null)
            supportActionBar?.hide()
    }

    fun donate(view: View) {
        val amnt = findViewById<EditText>(R.id.donation).text.toString()
        class transfer() : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? {
                val proj = Project.at("G0mLtXJAP_7fxN-IKCb5IuG1jEBYduM5zgRSdkqXkQk", "ywG25A0hNrO6zFd+Gc2Rf5XONsIx0B+IF92dhFRRQaIB4GX7gqfzLziCh76Rq1Z")
                val inputs = JSONObject()
                inputs.put("_to", "0xd06fb1891a51cc3f16cccc289cbf9ce347386809")
                inputs.put("_value", BigInteger("1000000000000000000").multiply(BigInteger(amnt)).toString(16))
                val out = proj.post("/transfer", inputs)
                Log.d("ME", out.toString())
                return null
            }
        }
        transfer().execute()
    }
}
