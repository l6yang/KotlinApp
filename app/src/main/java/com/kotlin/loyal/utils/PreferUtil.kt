package com.kotlin.loyal.utils

import android.content.Context
import com.kotlin.loyal.beans.LoginBean
import com.kotlin.loyal.impl.Contact
import com.loyal.base.impl.IBaseContacts
import java.util.*

class PreferUtil private constructor() : Contact {

    init {
        throw AssertionError()
    }

    companion object {

        private val PREFERENCE_NAME = "Kotlin"

        /**
         * put string preferences

         * @param context context
         * *
         * @param key     The name of the preference to modify
         * *
         * @param value   The new_ value for the preference
         * *
         * @return True if the new_ values were successfully written to persistent storage.
         */
        fun putString(context: Context, key: String, value: String): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        fun putLoginBean(context: Context, beanJson: String): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(Contact.Str.KEY_ACCOUNT, beanJson)
            return editor.commit()
        }

        /**
         * get string preferences

         * @param context
         * *
         * @param key          The name of the preference to retrieve
         * *
         * @param defaultValue Value to return if this preference does not exist
         * *
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * * this name that is not a string
         */
        @JvmOverloads
        fun getString(context: Context, key: String, defaultValue: String? = null): String {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getString(key, defaultValue)
        }

        /**
         * put int preferences

         * @param context
         * *
         * @param key     The name of the preference to modify
         * *
         * @param value   The new_ value for the preference
         * *
         * @return True if the new_ values were successfully written to persistent storage.
         */
        fun putInt(context: Context, key: String, value: Int): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(key, value)
            return editor.commit()
        }

        /**
         * get int preferences

         * @param context
         * *
         * @param key          The name of the preference to retrieve
         * *
         * @param defaultValue Value to return if this preference does not exist
         * *
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * * this name that is not a int
         */
        @JvmOverloads
        fun getInt(context: Context, key: String, defaultValue: Int = -1): Int {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getInt(key, defaultValue)
        }

        /**
         * put long preferences

         * @param context
         * *
         * @param key     The name of the preference to modify
         * *
         * @param value   The new_ value for the preference
         * *
         * @return True if the new_ values were successfully written to persistent storage.
         */
        fun putLong(context: Context, key: String, value: Long): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putLong(key, value)
            return editor.commit()
        }

        /**
         * get long preferences

         * @param context
         * *
         * @param key          The name of the preference to retrieve
         * *
         * @param defaultValue Value to return if this preference does not exist
         * *
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * * this name that is not a long
         */
        @JvmOverloads
        fun getLong(context: Context, key: String, defaultValue: Long = -1): Long {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getLong(key, defaultValue)
        }

        /**
         * put float preferences

         * @param context
         * *
         * @param key     The name of the preference to modify
         * *
         * @param value   The new_ value for the preference
         * *
         * @return True if the new_ values were successfully written to persistent storage.
         */
        fun putFloat(context: Context, key: String, value: Float): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putFloat(key, value)
            return editor.commit()
        }

        /**
         * get float preferences

         * @param context
         * *
         * @param key          The name of the preference to retrieve
         * *
         * @param defaultValue Value to return if this preference does not exist
         * *
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * * this name that is not a float
         */
        @JvmOverloads
        fun getFloat(context: Context, key: String, defaultValue: Float = -1f): Float {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getFloat(key, defaultValue)
        }

        /**
         * put boolean preferences

         * @param context
         * *
         * @param key     The name of the preference to modify
         * *
         * @param value   The new_ value for the preference
         * *
         * @return True if the new_ values were successfully written to persistent storage.
         */
        fun putBoolean(context: Context, key: String, value: Boolean): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }

        /**
         * get boolean preferences

         * @param context
         * *
         * @param key          The name of the preference to retrieve
         * *
         * @param defaultValue Value to return if this preference does not exist
         * *
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * * this name that is not a boolean
         */
        @JvmOverloads
        fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getBoolean(key, defaultValue)
        }

        fun getHiddenApps(context: Context): Set<String> {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getStringSet("KeyHiddenApps", HashSet<String>())
        }

        fun setHiddenApps(context: Context, hiddenApps: Set<String>): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putStringSet("KeyHiddenApps", hiddenApps)
            return editor.commit()
        }
    }
}
/**
 * get string preferences

 * @param context
 * *
 * @param key     The name of the preference to retrieve
 * *
 * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
 * * name that is not a string
 * *
 * @see .getString
 */
/**
 * get int preferences

 * @param context
 * *
 * @param key     The name of the preference to retrieve
 * *
 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
 * * name that is not a int
 * *
 * @see .getInt
 */
/**
 * get long preferences

 * @param context
 * *
 * @param key     The name of the preference to retrieve
 * *
 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
 * * name that is not a long
 * *
 * @see .getLong
 */
/**
 * get float preferences

 * @param context
 * *
 * @param key     The name of the preference to retrieve
 * *
 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
 * * name that is not a float
 * *
 * @see .getFloat
 */
/**
 * get boolean preferences, default is false

 * @param context
 * *
 * @param key     The name of the preference to retrieve
 * *
 * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
 * * name that is not a boolean
 * *
 * @see .getBoolean
 */
