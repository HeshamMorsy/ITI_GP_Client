package app.iti.client.iti_gp_client.screens.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ListView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ProfileContract.*
import app.iti.client.iti_gp_client.entities.ProfileOption
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.nav_header_home.*

class ProfileActivity : AppCompatActivity(), View {
    // reference to presenter
    var mPresenter: Presenter? = null
    // array of profile options
    var profileOptionsArray: ArrayList<ProfileOption>? = null
    // profile list adapter
    var profileAdapter: ProfileListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mPresenter = ProfilePresenter()
        mPresenter?.initPresenter(this)
        // initialize profile options array
        profileOptionsArray = createListOptions()
        profileAdapter = ProfileListAdapter(this,profileOptionsArray!!)
        profile_list.adapter = profileAdapter

        // handle on item click listener on list view items
        profile_list.setOnItemClickListener { parent, view, position, id ->  }

    }

    // creating options in list view in profile
    private fun createListOptions(): ArrayList<ProfileOption>{
        val options = ArrayList<ProfileOption>()
        options.add(ProfileOption(resources.getString(R.string.editProfile),R.mipmap.ic_more))
        options.add(ProfileOption(resources.getString(R.string.changePassword),R.mipmap.ic_more))
        options.add(ProfileOption(resources.getString(R.string.paymentMethod),R.mipmap.ic_more))
        options.add(ProfileOption(resources.getString(R.string.language),R.mipmap.ic_down_arrow))
        options.add(ProfileOption(resources.getString(R.string.logout),R.mipmap.ic_down_arrow))
        return options
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_MOVE)
            return true
        return super.dispatchTouchEvent(ev)
    }

}
