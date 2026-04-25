@file:Suppress("DEPRECATION")

package com.fomalhaut.singularity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.EncryptedSharedPreferences.create
import com.fomalhaut.singularity.ui.theme.SingularityDevTheme
import com.fomalhaut.singularity.viewModel.ViewModel
import com.fomalhaut.singularity.data.database.AppDatabase
import com.fomalhaut.singularity.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreference = create(this,"secret_shared_prefs", SingularityApp.masterKey,
            AES256_SIV,
            AES256_GCM
        )
        sharedPreference.edit { putString("password", "твой_секрет") }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val db = AppDatabase.getDatabase(this)
            val dao = db.passwordDao()
            val viewModel = ViewModel(dao)
            SingularityDevTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}
