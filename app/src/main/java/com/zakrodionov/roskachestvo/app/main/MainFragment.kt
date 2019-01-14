package com.zakrodionov.roskachestvo.app.main

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.Screens
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.Layout
import com.zakrodionov.roskachestvo.common.SupportXAppScreen
import com.zakrodionov.roskachestvo.extensions.colors
import kotlinx.android.synthetic.main.view_main_fragment.*
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router

@Layout(R.layout.view_main_fragment)
class MainFragment : BaseFragment(), MainView {

    //region Внедрение презентера
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    internal fun providePresenter(): MainPresenter =
        MainPresenter()
    //endregion

    val router: Router by inject()

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        AHBottomNavigationAdapter(activity, R.menu.main_bottom_menu).apply {
            setupWithBottomNavigation(bottomBar)
        }


        with(bottomBar) {
            accentColor = colors[R.color.colorPrimary]
            inactiveColor = colors[R.color.silver]

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) selectTab(
                    when (position) {
                        0 -> researchTab
                        1 -> barcodeTab
                        2 -> searchTab
                        else -> favoritesTab
                    }
                )
                true
            }
        }

        selectTab(
            when (currentTabFragment?.tag) {
                researchTab.screenKey -> researchTab
                barcodeTab.screenKey -> barcodeTab
                searchTab.screenKey -> searchTab
                favoritesTab.screenKey -> favoritesTab
                else -> researchTab
            }
        )
    }

    private fun selectTab(tab: SupportXAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(R.id.mainScreenContainer, createTabFragment(tab), tab.screenKey)

            currentFragment?.let {
                hide(it)
                it.userVisibleHint = false
            }
            newFragment?.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportXAppScreen) = tab.fragment

    override fun prepareUi(view: View) {

    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    companion object {
        private val researchTab = Screens.Research
        private val barcodeTab = Screens.Barcode
        private val searchTab = Screens.Search
        private val favoritesTab = Screens.Favorites
    }
}