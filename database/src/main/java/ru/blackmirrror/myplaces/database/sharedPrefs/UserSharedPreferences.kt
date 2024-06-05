package ru.blackmirrror.myplaces.database.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import ru.blackmirrror.myplaces.database.R
import ru.blackmirrror.myplaces.database.sharedPrefs.DefaultValues.DEFAULT_USER_ID
import javax.inject.Inject

class UserSharedPreferences @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE)

    var id: Long
        get() = sharedPreferences.getLong(context.getString(R.string.key_id), DEFAULT_USER_ID)
        set(value) {
            sharedPreferences.edit().putLong(context.getString(R.string.key_id), value).apply()
        }

    var username: String?
        get() = sharedPreferences.getString(context.getString(R.string.key_username), null)
        set(value) {
            sharedPreferences.edit().putString(context.getString(R.string.key_username), value).apply()
        }

    var password: String?
        get() = sharedPreferences.getString(context.getString(R.string.key_password), null)
        set(value) {
            sharedPreferences.edit().putString(context.getString(R.string.key_password), value).apply()
        }

    var isGuest: Boolean?
        get() = sharedPreferences.getBoolean(context.getString(R.string.key_is_guest), false)
        set(value) {
            sharedPreferences.edit().putBoolean(context.getString(R.string.key_is_guest), value ?: false).apply()
        }

    fun clearPreferences() {
        sharedPreferences.edit().remove(context.getString(R.string.key_id)).apply()
        sharedPreferences.edit().remove(context.getString(R.string.key_username)).apply()
        sharedPreferences.edit().remove(context.getString(R.string.key_password)).apply()
        sharedPreferences.edit().putBoolean(context.getString(R.string.key_is_guest), false).apply()
    }
}