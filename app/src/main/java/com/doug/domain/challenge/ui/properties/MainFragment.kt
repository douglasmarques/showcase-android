package com.doug.domain.challenge.ui.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doug.domain.challenge.R
import com.doug.domain.challenge.repository.domain.SearchType
import com.doug.domain.challenge.ui.BaseFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val rentFragment: PropertiesFragment by lazy {
        PropertiesFragment.newInstance(SearchType.RENT)
    }

    private val buyFragment: PropertiesFragment by lazy {
        PropertiesFragment.newInstance(SearchType.BUY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showRentFragment()
        setUpTabSelectedListener()
    }

    private fun setUpTabSelectedListener() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> showRentFragment()
                    1 -> showBuyFragment()
                }
            }
        })
    }

    private fun showRentFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, rentFragment, "RentFragment").commit()
    }

    private fun showBuyFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, buyFragment, "BuyFragment").commit()
    }

}
