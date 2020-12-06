package com.emintolgahanpolat.retrofitex.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

fun SharedPreferences.setValue(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        is Any -> edit {
            it.putString(key, Gson().toJson(value))
        }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

fun SharedPreferences.removeValue(key: String) {
    edit().remove(key).apply()
}

fun SharedPreferences.clear() {
    edit().clear().apply()
}

inline fun <reified T : Any> SharedPreferences.getObject(
    key: String,
    targetType: Class<T>,
    defaultValue: T? = null
): T? {
    return Gson().fromJson(getString(key, defaultValue as? String), targetType)
}

inline fun <reified T : Any> SharedPreferences.getObjectList(
    key: String,
    targetType: Class<T>,
    defaultValue: T? = null
): MutableList<T>? {
    return Gson().fromJson(getString(key, defaultValue as? String), object : TypeToken<T>() {}.type)
}

inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}
