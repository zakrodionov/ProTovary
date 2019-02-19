package com.zakrodionov.roskachestvo.app.ui.base;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import ru.terrakok.cicerone.Screen;

public abstract class SupportXAppScreen extends Screen {

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}