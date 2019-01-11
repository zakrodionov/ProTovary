package com.zakrodionov.roskachestvo.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.Screens
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.MvpAppXActivity
import com.zakrodionov.roskachestvo.common.SupportXAppNavigator
import com.zakrodionov.roskachestvo.model.interactor.MainInteractor
import com.zakrodionov.roskachestvo.model.interactor.SharedPreferenceInteractor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command

class AppActivity : MvpAppXActivity() {

    val navigatorHolder: NavigatorHolder by inject()
    val preferences: SharedPreferenceInteractor by inject()
    val router: Router by inject()

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment


    private val navigator: Navigator =
        object : SupportXAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun applyCommands(commands: Array<out Command>?) {
                super.applyCommands(commands)

            }

            override fun activityBack() {
                router.exit()
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

        val interactor: MainInteractor = get()

        router.navigateTo(Screens.MainFrag)

        interactor.getResearches().subscribe(
            { toast(it.size.toString()) },
            { it.printStackTrace() })
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
        if (currentFragment?.onBackPressed() == true) else super.onBackPressed()
    }
}
