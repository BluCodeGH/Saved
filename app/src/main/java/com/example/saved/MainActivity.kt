package com.example.saved

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


private lateinit var auth: FirebaseAuth
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun enter(view: View) {
        var email = findViewById<EditText>(R.id.email).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()

        if (auth.currentUser != null) {
            val randomIntent = Intent(this, MapsActivity::class.java)
            startActivity(randomIntent)

        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    /* updateUI(user) */
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    /* updateUI(null) */
                }

                // ...
            }

    }

    fun createAccount (view: View) {
        val randomIntent = Intent(this, CreateActivity::class.java)
        startActivity(randomIntent)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        /* updateUI(currentUser) */
    }


}
