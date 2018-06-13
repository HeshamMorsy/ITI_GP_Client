package app.iti.client.iti_gp_client.screens.about

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.AboutContract.*

class AboutActivity : AppCompatActivity(), View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
