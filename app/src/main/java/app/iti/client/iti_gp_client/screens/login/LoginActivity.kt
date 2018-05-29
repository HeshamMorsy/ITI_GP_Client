package app.iti.client.iti_gp_client.screens.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        gotoSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }
}
