package com.zakrodionov.roskachestvo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.MvpAppXActivity
import com.zakrodionov.roskachestvo.common.SupportXAppNavigator
import com.zakrodionov.roskachestvo.common.interactor.MainInteractor
import com.zakrodionov.roskachestvo.common.interactor.SharedPreferenceInteractor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command

class AppActivity : MvpAppXActivity() {

    var navigatorHolder: NavigatorHolder = object : NavigatorHolder {
        override fun setNavigator(navigator: Navigator?) {
        }

        override fun removeNavigator() {
        }

    }

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    val preferences: SharedPreferenceInteractor by inject()

    private val navigator: Navigator =
        object : SupportXAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_container)

        val interactor: MainInteractor = get()

        interactor.getResearches().subscribe({
            toast(it.size.toString())
            Log.d("testinet", it.size.toString())
        }, { it.printStackTrace() })
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}
