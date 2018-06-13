package app.iti.client.iti_gp_client.utilities

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

class LocaleHelper {

    companion object {
        val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        fun onAttach(context: Context) : Context{
            val lang: String = getPersistedData(context, Locale.getDefault().language)
            return setLocale(context, lang)
        }

        fun onAttach(context: Context, defaultLanguage: String) : Context{
            val lang: String = getPersistedData(context, defaultLanguage)
            return setLocale(context, lang)
        }

         fun setLocale(context: Context, lang: String) : Context{
            persist(context, lang)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return updateResources(context, lang)

            return updateResourcesLegacy(context, lang)
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, lang: String): Context {
            val locale: Locale = Locale(lang)
            Locale.setDefault(locale)

            val config: Configuration = context.resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            return context.createConfigurationContext(config)

        }


        @SuppressWarnings("deprecation")
        private fun updateResourcesLegacy(context: Context, lang: String): Context {
            val locale: Locale = Locale(lang)
            Locale.setDefault(locale)

            val resources: Resources = context.resources

            val config: Configuration = resources.configuration
            config.locale = locale

            config.setLayoutDirection(locale)
            resources.updateConfiguration(config,resources.displayMetrics)
            return context

        }

        private fun persist(context: Context, lang: String) {
            val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = pref.edit()

            editor.putString(SELECTED_LANGUAGE, lang)
            editor.apply()
        }

        private fun getPersistedData(context: Context, language: String) : String{
            val shared:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return shared.getString(SELECTED_LANGUAGE , language)
        }

    }
}