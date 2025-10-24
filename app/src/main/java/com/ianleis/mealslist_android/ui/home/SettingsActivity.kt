package com.ianleis.mealslist_android.ui.home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.SettingsKeys
import com.ianleis.mealslist_android.data.dataStore
import com.ianleis.mealslist_android.databinding.ActivitySettingsBinding
import com.ianleis.mealslist_android.domain.model.SettingsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            val settings = getSettings().first()
            binding.switchDarkMode.isChecked = settings.darkMode
        }
        initUI()
    }

    private fun initUI() {
        initLanguageSpinner()

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            darkMode(value)
            lifecycleScope.launch(Dispatchers.IO) {
                saveDarkModeState(value)
            }
        }
    }

    private fun initLanguageSpinner() {
        val languages = resources.getStringArray(R.array.languages)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = adapter

        lifecycleScope.launch {
            val settings = getSettings().first()
            val currentLanguage = settings.language ?: if (Locale.getDefault().language == "es") "Spanish" else "English"
            val position = when (currentLanguage) {
                "Spanish" -> 1
                else -> 0
            }
            binding.spinnerLanguage.setSelection(position)
        }

        binding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedLanguage = when (position) {
                        1 -> "Spanish"
                        else -> "English"
                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        saveLanguageState(selectedLanguage)
                    }
                    updateLocale(selectedLanguage)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private suspend fun saveLanguageState(language: String) {
        dataStore.edit { settings ->
            settings[SettingsKeys.LANGUAGE_KEY] = language
        }
    }

    private fun updateLocale(language: String) {
        val locale = when (language) {
            "Spanish" -> Locale.forLanguageTag("es")
            else -> Locale.forLanguageTag("en")
        }
        val appLocale = LocaleListCompat.create(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }


    private suspend fun saveDarkModeState(isDarkMode: Boolean) {
        dataStore.edit { settings ->
            settings[SettingsKeys.DARK_MODE_KEY] = isDarkMode
        }
    }

    private fun getSettings(): Flow<SettingsModel> {
        return dataStore.data.map { preferences ->
            SettingsModel(
                darkMode = preferences[SettingsKeys.DARK_MODE_KEY] ?: isNightMode(this),
                language = preferences[SettingsKeys.LANGUAGE_KEY]
            )
        }
    }

    fun isNightMode(context: Context): Boolean {
        val nightModeFlags =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    private fun darkMode(enabled: Boolean) {
        if (enabled) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}