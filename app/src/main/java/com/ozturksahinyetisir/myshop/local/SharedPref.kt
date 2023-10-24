package com.ozturksahinyetisir.myshop.local
import android.content.Context
import android.content.SharedPreferences
/**
 Using SharedPref for basic login credentials.
 */
class SharedPref(context: Context) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPrefs.edit()

    /**
     * Saves data to SharedPreferences.
     * @param key The key under which the data will be stored.
     * @param value The value to be stored.
     */

    fun saveData(key: String, value: String) {

        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Loads data from SharedPreferences.
     */
    fun loadData(key: String): String {
        return sharedPrefs.getString(key, "") ?: ""
    }
    /**
     * Clears all data stored in SharedPreferences.
     */
    fun clearAllData() {
        editor.clear().apply()
    }


}