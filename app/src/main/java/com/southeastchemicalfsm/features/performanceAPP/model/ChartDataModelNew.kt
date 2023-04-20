package com.southeastchemicalfsm.features.performanceAPP.model

import com.southeastchemicalfsm.features.performanceAPP.PartyWiseDataModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels

class ChartDataModelNew {
    companion object{

        fun configurePieChart(attendP:Int,absentP : Int): AAChartModel {
            return AAChartModel()
                .chartType(AAChartType.Pie)
                .backgroundColor("#ffffff")
                .title("")
                .subtitle("")
                .colorsTheme(arrayOf("#0019b2", "#f5862c"))
                .dataLabelsEnabled(true)//是否直接显示扇形图数据
                .yAxisTitle("℃")
                .series(arrayOf(
                    AASeriesElement()
                        .name("")
                        .innerSize("60%")
                        .size(90)
                        .dataLabels(AADataLabels()
                            .enabled(true)
                            .useHTML(true)
                            .distance(10)
                            .format("<b></b> {point.percentage:.1f} %"))
                        .data(arrayOf(
                            arrayOf("Present",   attendP),
                            arrayOf("Absent", absentP)
                        ))))
        }
        fun configurePolarColumnChart(totalOrdervalue:Double,totalOrderCount:Double,avgOrdervalue:Double,avgOrdercount:Double): AAChartModel {
            return AAChartModel()
                .chartType(AAChartType.Column)
                .polar(false)
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .yAxisTitle("")
                .colorsTheme(arrayOf("#158650", "#0a69ab","#b5740e", "#b12408"))
                .categories(arrayOf("Total<br>Order<br>Values", "Total<br>Order<br>Count","Avg<br>Order<br>Value","Avg<br>Order<br>Count"))
                .series(arrayOf(
                    AASeriesElement()
                        .name("")
                        .colorByPoint(true)
                        .data(arrayOf(totalOrdervalue, totalOrderCount,avgOrdervalue,avgOrdercount))
                ))
        }

        fun configurePolarBarChart(nameL: ArrayList<String>,valueL: ArrayList<String>): AAChartModel {

            var arrOfName = Array(nameL.size) {nameL[it].toString()}
            var arrOfValue = Array(nameL.size) {valueL[it].toDouble()}


            return AAChartModel()
                .chartType(AAChartType.Bar)
                .polar(false)
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .yAxisTitle("")
                .colorsTheme(arrayOf("#879e1a", "#2150a0","#a61e73", "#E70EAF", "#131313"))
                .categories(arrOfName)
                .series(arrayOf(
                    AASeriesElement()
                        .name("")
                        .colorByPoint(true)
                        .data(arrOfValue as Array<Any>)
                ))
        }
    }


}