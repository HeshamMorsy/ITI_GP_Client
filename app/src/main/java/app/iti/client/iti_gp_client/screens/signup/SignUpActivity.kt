package app.iti.client.iti_gp_client.screens.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        loginLabel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
    }
}
