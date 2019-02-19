package com.zakrodionov.roskachestvo.domain.interactor

import com.orhanobut.hawk.Hawk

class SharedPreferenceInteractor() {

    fun getToken(): String = Hawk.get(Preferences.USER_TOKEN.name, "WORKED!!!")
    fun setToken(token: String) = Hawk.put(Preferences.USER_TOKEN.name, token)

    fun clearPreferences() {
        setToken("")
    }

    enum class Preferences {
        USER_TOKEN
    }
}