package com.genzen.zenspire

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_FILENAME = "authAppPrefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_UID = "uid"
        private const val KEY_EMAIL = "email"
        private const val KEY_FIRSTNAME = "firstname"
        private const val KEY_LASTNAME = "lastname"
        private const val KEY_EXP = "exp"
        private const val KEY_ISANONYMOUS = "isAnonymous"

        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context) : PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }
    fun saveUid(uid: Int) {
        sharedPreferences.edit().putInt(KEY_UID, uid).apply()
    }
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply()
    }
    fun saveFirstName(firstName: String) {
        sharedPreferences.edit().putString(KEY_FIRSTNAME, firstName).apply()
    }
    fun saveLastName(lastName: String?) {
        sharedPreferences.edit().putString(KEY_LASTNAME, lastName).apply()
    }
    fun saveExp(exp: Int) {
        sharedPreferences.edit().putInt(KEY_EXP, exp).apply()
    }
    fun setAnonymous(isAnonymous: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ISANONYMOUS, isAnonymous).apply()
    }


    fun getToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, "") ?: ""
    }
    fun getUid(): Int {
        return sharedPreferences.getInt(KEY_UID, 0)
    }
    fun getEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }
    fun getFirstName(): String {
        return sharedPreferences.getString(KEY_FIRSTNAME, "") ?: ""
    }
    fun getLastName(): String {
        return sharedPreferences.getString(KEY_LASTNAME, "") ?: ""
    }
    fun getExp(): Int {
        return sharedPreferences.getInt(KEY_EXP, 0)
    }
    fun isAnonymous(): Boolean {
        return sharedPreferences.getBoolean(KEY_ISANONYMOUS, false)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}