package com.zakrodionov.roskachestvo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.Screens
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.MvpAppXActivity
import com.zakrodionov.roskachestvo.common.SupportXAppNavigator
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command

class AppActivity : MvpAppXActivity() {

    val navigatorHolder: NavigatorHolder by inject()
    val router: Router by inject()

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment


    private val navigator: Navigator =
        object : SupportXAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun applyCommands(commands: Array<out Command>?) {
                super.applyCommands(commands)

            }

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

        router.replaceScreen(Screens.MainFrag)

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
//        if (supportFragmentManager.backStackEntryCount == 1 ) {
//           this.finish()
//        }
        if (currentFragment?.onBackPressed() == true) else super.onBackPressed()
    }
}
