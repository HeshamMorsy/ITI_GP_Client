package app.iti.client.iti_gp_client.screens.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ProfileContract.Presenter
import app.iti.client.iti_gp_client.contracts.ProfileContract.View
import app.iti.client.iti_gp_client.entities.ProfileOption
import app.iti.client.iti_gp_client.screens.change_password.ChangePasswordActivity
import app.iti.client.iti_gp_client.screens.edit_profile.EditProfileActivity
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import app.iti.client.iti_gp_client.utilities.Constants
import app.iti.client.iti_gp_client.utilities.Constants.Companion.AVATAR_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.CURRENT_LANGUAGE_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.NAME_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.LocaleHelper
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_profile.*
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.setValue
import com.bumptech.glide.Glide

/**
 * created by Hesham
 */

class ProfileActivity : AppCompatActivity(), View {
    // reference to presenter
    var mPresenter: Presenter? = null
    // array of profile options
    var profileOptionsArray: ArrayList<ProfileOption>? = null
    // sub list options
    var subListArray: HashMap<String,ArrayList<String>>? = null
    // profile list adapter
    var profileAdapter: ProfileListAdapter? = null
    var expandAdapter: ExpandableListAdapter? = null
    var currentLanguage:String? = null


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!! , "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // get user data from shared preferences
        val sharedPreferences = PreferenceHelper.defaultPrefs(this)
        // get language from shared preferences if exists
        currentLanguage = sharedPreferences.get(CURRENT_LANGUAGE_SHARED_PREFERENCE, resources.getString(R.string.english))
        val name = sharedPreferences.get(NAME_SHARED_PREFERENCE,"user name")
        val imageUrl = sharedPreferences.get(AVATAR_SHARED_PREFERENCE,"")
        if(imageUrl != "")
            Glide.with(this).load(imageUrl).into(profile_image)
        profile_name.text = name



        // initializing presenter and call initPresenter to initialize view and model in presenter class
        mPresenter = ProfilePresenter()
        mPresenter?.initPresenter(this)

        // init expandable list arrays and adapter
        profileOptionsArray = ArrayList()
        subListArray = HashMap()
        expandAdapter = ExpandableListAdapter(this,profileOptionsArray!!,subListArray!!)


        // init paper to handle changing language in app runtime
        Paper.init(this)

        // default language is English
        val language: String? = Paper.book().read("language")

        if(language == null){
            Paper.book().write("language","en")
        }

        updateView(Paper.book().read("language"))

        expand_list.setAdapter(expandAdapter)

        // this method is to handle on click on child list
        onChildClick()

        // this method is to handel on click on group list
        onHeaderClick()
    }

    // handle on  child click listener
    private fun onChildClick() {
        expand_list.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if(childPosition == 0){
                // use paper library to change application language
                Paper.book().write("language","en")

                // update the view
                updateView(Paper.book().read("language"))

                // remove the element where language is located
                profileOptionsArray!!.removeAt(3)

                // add language at the element that is removed
                profileOptionsArray!!.add(3,ProfileOption(resources.getString(R.string.language),
                        R.mipmap.ic_down_arrow,resources.getString(R.string.english)))

                // savae current language in shared preferences
                currentLanguage = resources.getString(R.string.english)
                saveInSharedPreferences(currentLanguage!!, CURRENT_LANGUAGE_SHARED_PREFERENCE)

                // start activity again to handle rotation
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }else if(childPosition == 1){
                // use paper library to change application language
                Paper.book().write("language","ar")

                // update the view
                updateView(Paper.book().read("language"))

                // remove the element where language is located
                profileOptionsArray!!.removeAt(3)

                // add language at the element that is removed
                profileOptionsArray!!.add(3,ProfileOption(resources.getString(R.string.language),
                        R.mipmap.ic_down_arrow,resources.getString(R.string.arabic)))

                // save current language in shared preferences
                currentLanguage = resources.getString(R.string.english)
                saveInSharedPreferences(currentLanguage!!, CURRENT_LANGUAGE_SHARED_PREFERENCE)

                // start activity again to handle rotation
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            expandAdapter!!.notifyDataSetChanged()
            return@setOnChildClickListener true
        }
    }


    // handle on group (header) click listener
    private fun onHeaderClick(){
        expand_list.setOnGroupClickListener { parent, v, groupPosition, id ->
            if(groupPosition == 0){
                //go to edit profile activity
                val myIntent = Intent(this , EditProfileActivity::class.java)
                startActivity(myIntent)
            }else if(groupPosition == 1){
                //go to change password activity
                val myIntent = Intent(this , ChangePasswordActivity::class.java)
                startActivity(myIntent)
            }else if(groupPosition == 2){
                Toast.makeText(this , resources.getString(R.string.soonToast) , Toast.LENGTH_SHORT).show()
            }else if(groupPosition == 3){
                if(expand_list.isGroupExpanded(groupPosition)){
                    profileOptionsArray!!.removeAt(3)
                    profileOptionsArray!!.add(3,ProfileOption(resources.getString(R.string.language),
                            R.mipmap.ic_more,currentLanguage!!))
                    expand_list.collapseGroup(groupPosition)
                }else {
                    profileOptionsArray!!.removeAt(3)
                    profileOptionsArray!!.add(3,ProfileOption(resources.getString(R.string.language),
                            R.mipmap.ic_down_arrow,currentLanguage!!))
                    expand_list.expandGroup(groupPosition)
                }
            }else if(groupPosition == 4){
                //logout
                emptyUserData()
                val myIntent = Intent(this , LoginActivity::class.java)
                startActivity(myIntent)
                finish()
                Toast.makeText(this , resources.getString(R.string.logoutSuccessfully), Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    // this method is to empty user data from shared preferences
    private fun emptyUserData(){
        val defaultPref = PreferenceHelper.defaultPrefs(this)
        defaultPref.setValue(Constants.TOKEN_SHARED_PREFERENCE,"")
        defaultPref.setValue(Constants.EMAIL_SHARED_PREFERENCE,"")
        defaultPref.setValue(Constants.PHONE_SHARED_PREFERENCE,"")
        defaultPref.setValue(Constants.NAME_SHARED_PREFERENCE,"")
        defaultPref.setValue(Constants.AVATAR_SHARED_PREFERENCE,"")
        defaultPref.setValue(Constants.LOGIN_STATUS_SHARED_PREFERENCE,false)
    }

    // creating options in list view in profile
    private fun createListOptions(resources: Resources): ArrayList<ProfileOption>{
        val prefs = PreferenceHelper.defaultPrefs(this)
        currentLanguage = prefs.get(CURRENT_LANGUAGE_SHARED_PREFERENCE,resources.getString(R.string.english))
        val options = ArrayList<ProfileOption>()
        options.add(ProfileOption(resources.getString(R.string.editProfile),R.mipmap.ic_more,""))
        options.add(ProfileOption(resources.getString(R.string.changePassword),R.mipmap.ic_more,""))
        options.add(ProfileOption(resources.getString(R.string.paymentMethod),R.mipmap.ic_more,""))
        options.add(ProfileOption(resources.getString(R.string.language),R.mipmap.ic_more,currentLanguage!!))
        options.add(ProfileOption(resources.getString(R.string.logout),R.mipmap.ic_down_arrow,""))
        return options
    }

    // creating sub lists objects
    private fun createLanguageList(resources: Resources) : HashMap<String,ArrayList<String>>{
        val langArray = ArrayList<String>()
        langArray.add(resources.getString(R.string.english))
        langArray.add(resources.getString(R.string.arabic))
        val emptyArr =ArrayList<String>()
        val subList: HashMap<String, ArrayList<String>> = hashMapOf(
                profileOptionsArray!![0].optionTitle to emptyArr,
                profileOptionsArray!![1].optionTitle to emptyArr,
                profileOptionsArray!![2].optionTitle to emptyArr,
                profileOptionsArray!![3].optionTitle to langArray,
                profileOptionsArray!![4].optionTitle to emptyArr
        )

        return subList
    }


    private fun updateView(lang: String){
        val context = LocaleHelper.setLocale(this, lang)
        val resources = context.resources
        profileOptionsArray!!.clear()
        profileOptionsArray!!.addAll(createListOptions(resources))
        subListArray!!.clear()
        subListArray!!.putAll(createLanguageList(resources))
        expandAdapter!!.notifyDataSetChanged()
    }

    private fun saveInSharedPreferences(data: String , key: String){
        val prefs = PreferenceHelper.defaultPrefs(this)
        prefs.setValue(key,data)
    }


}
