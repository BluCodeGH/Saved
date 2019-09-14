package com.example.saved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

class SubscribeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
    }

    fun sub(view: View) {
        val topic = findViewById<EditText>(R.id.editText).text.toString()
        if (topic.isEmpty()) {
            return
        }
        findViewById<EditText>(R.id.editText).setText("")
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
        .addOnCompleteListener { task ->
            var msg = "Subscribed to $topic"
            if (!task.isSuccessful) {
                msg = "Could not subscribe to $topic"
            }
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun unsub(view: View) {
        val topic = findViewById<EditText>(R.id.editText).text.toString()
        if (topic.isEmpty()) {
            return
        }
        findViewById<EditText>(R.id.editText).setText("")
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Unsubscribed from $topic"
                if (!task.isSuccessful) {
                    msg = "Could not unsubscribe from $topic"
                }
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }
}
