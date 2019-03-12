package com.zakrodionov.roskachestvo.data.storage

import com.orhanobut.hawk.Hawk

class PreferenceStorage {

    fun getToken(): String = Hawk.get(Preferences.USER_TOKEN.name, "")
    fun setToken(token: String) = Hawk.put(Preferences.USER_TOKEN.name, token)

    fun clearPreferences() {
        setToken("")
    }

    enum class Preferences {
        USER_TOKEN
    }
}