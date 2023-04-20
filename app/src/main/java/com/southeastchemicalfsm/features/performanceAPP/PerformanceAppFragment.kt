package com.southeastchemicalfsm.features.performanceAPP

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.southeastchemicalfsm.R
import com.southeastchemicalfsm.base.presentation.BaseFragment
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aatools.AAGradientColor
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import com.southeastchemicalfsm.widgets.AppCustomTextView


/**
 * Created by Saheli on 24-03-2023.
 */
class PerformanceAppFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var ownPerformanceTab: AppCustomTextView
    private lateinit var teamPerformanceTab: AppCustomTextView
    private lateinit var performanceTabPagerAdapter: PerformanceTabPagerAdapter
    private lateinit var performanceViewPager: ViewPager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_performance_app, container, false)
        initView(view)
        initAdapter()

        return view
    }

    private fun initView(view: View) {
        ownPerformanceTab = view.findViewById(R.id.ownPerformace_TV_frag_performace_app)
        teamPerformanceTab = view.findViewById(R.id.teamPerformace_TV_frag_performace_app)
        performanceViewPager = view.findViewById(R.id.performance_vp_frag_performance_app)

        ownPerformanceTab.setOnClickListener(this)
        teamPerformanceTab.setOnClickListener(this)
        performanceTabPagerAdapter = PerformanceTabPagerAdapter(fragmentManager)
        performanceViewPager.currentItem = 0
        isPerformanceWise(true)
        performanceViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
               if (position == 0) {
                    isPerformanceWise(true)
                } else {
                    isPerformanceWise(false)
                }
            }

        })

    }

    private fun initAdapter() {
        performanceViewPager.adapter = performanceTabPagerAdapter
    }

    open fun refreshAdapter() {
        performanceViewPager.adapter?.notifyDataSetChanged()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ownPerformace_TV_frag_performace_app -> {
                isPerformanceWise(true)
                performanceViewPager.currentItem = 0
            }
            R.id.teamPerformace_TV_frag_performace_app -> {
                isPerformanceWise(false)
                performanceViewPager.currentItem = 1
            }
        }
    }

    fun isPerformanceWise(isperformanceWise: Boolean) {
        if (isperformanceWise) {
            ownPerformanceTab.isSelected = true
            teamPerformanceTab.isSelected = false
        } else {
            ownPerformanceTab.isSelected = false
            teamPerformanceTab.isSelected = true
        }
    }
}