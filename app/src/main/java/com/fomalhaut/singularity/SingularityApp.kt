package com.fomalhaut.singularity

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlin.lazy
import kotlin.getValue

val Context.dataStore by preferencesDataStore(name = "settings")
class SingularityApp: Application() {
    val vault by lazy { EncryptedSharedPreferences.create(this, "singularity_vault", masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ) }
    companion object{
    lateinit var masterKey: MasterKey
    }
    override fun onCreate() {
        super.onCreate()
            masterKey = MasterKey.Builder(this).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }
}
