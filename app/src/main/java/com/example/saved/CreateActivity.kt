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
class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }


    fun accountCreate (view: View) {
        var email = findViewById<EditText>(R.id.email).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()
//        val intent = Intent(this, MapsActivity::class.java)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val intentGood = Intent(this, MapsActivity::class.java)
                    startActivity(intentGood)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("ME", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    val intentBad = Intent(this, MainActivity::class.java)
                    startActivity(intentBad)
                }

                // ...
            }

        /* startActivity(intent) */
    }
}
