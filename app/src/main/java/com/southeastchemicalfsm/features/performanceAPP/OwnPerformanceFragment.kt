package com.southeastchemicalfsm.features.performanceAPP

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.southeastchemicalfsm.R
import com.southeastchemicalfsm.app.AppDatabase
import com.southeastchemicalfsm.app.NetworkConstant
import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.app.domain.AddShopDBModelEntity
import com.southeastchemicalfsm.app.domain.ShopTypeEntity
import com.southeastchemicalfsm.app.utils.AppUtils
import com.southeastchemicalfsm.app.utils.Toaster
import com.southeastchemicalfsm.base.presentation.BaseActivity
import com.southeastchemicalfsm.base.presentation.BaseFragment
import com.southeastchemicalfsm.features.attendance.api.AttendanceRepositoryProvider
import com.southeastchemicalfsm.features.attendance.model.AttendanceRequest
import com.southeastchemicalfsm.features.attendance.model.AttendanceResponse
import com.southeastchemicalfsm.features.dashboard.presentation.DashboardActivity
import com.southeastchemicalfsm.features.nearbyshops.model.ShopListResponse
import com.southeastchemicalfsm.features.performanceAPP.model.AdapterPartywiseSalesRecyclerView
import com.southeastchemicalfsm.features.performanceAPP.model.ChartDataModel
import com.southeastchemicalfsm.features.performanceAPP.model.ChartDataModelNew
import com.southeastchemicalfsm.features.viewAllOrder.orderOptimized.ProductQtyRateSubmit
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Saheli on 26-03-2023.
 */

class OwnPerformanceFragment: BaseFragment(), View.OnClickListener {
    private lateinit var mContext: Context
    private lateinit var aaChart: AAChartView
    private lateinit var aaChart1: AAChartView
    private lateinit var aaChart2: AAChartView
    private lateinit var tv_present_atten: TextView
    private lateinit var tv_absent_atten: TextView
    private lateinit var tv_AttendHeader: TextView
    private lateinit var iv_frag_performance_attenshare: ImageView
    private lateinit var iv_frag_performance_MTDshare: ImageView
    private lateinit var iv_share_last10: ImageView
    private lateinit var tv_total_ordervalue_frag_own: TextView
    private lateinit var tv_totalOrdercount_frag_own_performance: TextView
    private lateinit var tv_avg_value_frag_own_performance: TextView
    private lateinit var tv_avg_orderCount_frag_own_performance: TextView
    private lateinit var ll_attend_view: LinearLayout
    private lateinit var ll_mtd_view: LinearLayout
    private lateinit var iv_loader_spin: ImageView
    private lateinit var iv_background_color_set: ImageView
    private lateinit var ll_last10order_frag_own: LinearLayout
    private lateinit var tv_frag_own_performnace_sel_shopType: TextView
    private var shopType_list: ArrayList<ShopTypeEntity>? = null
    private var sel_shopTypeID: String = ""
    private var sel_shopTypeName: String = ""
    private lateinit var tv_total_ordervalueshopTypewise_frag_own:TextView
    private lateinit var tv_totalOrdercount_shoptypewise_frag_own_performance:TextView
    private lateinit var tv_avgOrderValueshopTypewise_frag_own_performance:TextView
    private lateinit var tv_frag_own_performnace_sel_party:TextView
    private var mshoplist: ArrayList<AddShopDBModelEntity>? = null
    private lateinit var tv_frag_own_performance_lastvisitbyago:TextView
    private lateinit var tv_frag_own_performance_lastorderbyago:TextView
    private lateinit var tv_frag_own_performance_lastcollectionbyago:TextView
    private lateinit var tv_frag_own_performance_loginbyago:TextView
    private lateinit var ll_activityageing_frag_own:LinearLayout
    private lateinit var iv_share_activityageing: ImageView
    var calendar: Calendar = Calendar.getInstance()
    var inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    private var adapterPartynotVisited20days: AdapterPartyNotVisitRecyclerView? = null
    private lateinit var frag_own_performance_last20nitvisited_list_rv:RecyclerView
    private lateinit var shopList:ShopListResponse
    private var adapterPartywiseSales: AdapterPartywiseSalesRecyclerView? = null
    private lateinit var rcv_party_wise_items_own:RecyclerView
    private  var mPartywisesaleslist :ArrayList<listparty_wise_sales>?=null
    private lateinit  var tv_sel_party_multiple_sel_own:TextView
    private lateinit var iv_share_partywisesales: ImageView
    private lateinit var ll_party_wise_sales_performance:LinearLayout



    private lateinit  var samplec:AAChartView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_own_performance, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        samplec=view.findViewById(R.id.samplec)
        iv_background_color_set = view.findViewById(R.id.iv_background_color_set)
        iv_loader_spin = view.findViewById(R.id.iv_loader_spin)
        ll_attend_view = view.findViewById(R.id.ll_attend_view)
        ll_mtd_view = view.findViewById(R.id.ll_mtd_view)
        tv_AttendHeader = view.findViewById(R.id.tv_frag_own_perf_attend_heading)
        iv_frag_performance_attenshare = view.findViewById(R.id.iv_frag_performance_attenshare)
        iv_frag_performance_MTDshare = view.findViewById(R.id.iv_frag_performance_MTDshare)
        iv_frag_performance_attenshare.setOnClickListener(this)
        iv_frag_performance_MTDshare.setOnClickListener(this)
        iv_share_last10 =  view.findViewById(R.id.iv_share_last10)
        iv_share_last10.setOnClickListener(this)
        ll_last10order_frag_own = view.findViewById(R.id.ll_last10order_frag_own)
        tv_total_ordervalue_frag_own = view.findViewById(R.id.tv_total_ordervalue_frag_own)
        tv_totalOrdercount_frag_own_performance =
            view.findViewById(R.id.tv_totalOrdercount_frag_own_performance)
        tv_avg_value_frag_own_performance =
            view.findViewById(R.id.tv_avg_value_frag_own_performance)
        tv_avg_orderCount_frag_own_performance =
            view.findViewById(R.id.tv_avg_orderCount_frag_own_performance)
        tv_frag_own_performnace_sel_shopType = view.findViewById(R.id.tv_frag_own_performnace_sel_shopType)
        tv_frag_own_performnace_sel_shopType.setOnClickListener(this)
        loadProgress()
        tv_total_ordervalueshopTypewise_frag_own = view.findViewById(R.id.tv_total_ordervalueshopTypewise_frag_own)
        tv_totalOrdercount_shoptypewise_frag_own_performance = view.findViewById(R.id.tv_totalOrdercount_shoptypewise_frag_own_performance)
        tv_avgOrderValueshopTypewise_frag_own_performance = view.findViewById(R.id.tv_avgOrderValueshopTypewise_frag_own_performance)
        iv_share_activityageing = view.findViewById(R.id.iv_share_activityageing)
        iv_share_activityageing.setOnClickListener(this)
        /*  tv_AttendHeader = view.findViewById(R.id.tv_frag_own_perf_attend_heading)

        val text = "<font color=" + context?.resources?.getColor(R.color.black) + ">Attendance Report</font> <font color="+
                context?.resources?.getColor(R.color.gray_50) + ">" + "(Last Month)" + "</font>"
        tv_AttendHeader.text = Html.fromHtml(text)*/


        aaChart = view.findViewById<AAChartView>(R.id.aa_chart_view)
        aaChart1 = view.findViewById<AAChartView>(R.id.aa_chart_view1)
        aaChart2 = view.findViewById<AAChartView>(R.id.aa_chart_view2)
        tv_present_atten = view.findViewById(R.id.tv_frag_own_performance_present_atten)
        tv_absent_atten = view.findViewById(R.id.tv_frag_own_performance_absent_atten)
        tv_frag_own_performnace_sel_party =  view.findViewById(R.id.tv_frag_own_performnace_sel_party)
        tv_frag_own_performnace_sel_party.setOnClickListener(this)
        tv_frag_own_performance_lastvisitbyago = view.findViewById(R.id.tv_frag_own_performance_lastvisitbyago)
        tv_frag_own_performance_lastorderbyago =  view.findViewById(R.id.tv_frag_own_performance_lastorderbyago)
        tv_frag_own_performance_lastcollectionbyago = view.findViewById(R.id.tv_frag_own_performance_lastcollectionbyago)
        tv_frag_own_performance_loginbyago = view.findViewById(R.id.tv_frag_own_performance_loginbyago)
        ll_activityageing_frag_own = view.findViewById(R.id.ll_activityageing_frag_own)

        frag_own_performance_last20nitvisited_list_rv = view.findViewById(R.id.frag_own_performance_last20nitvisited_list_rv)
        rcv_party_wise_items_own = view.findViewById(R.id.rcv_party_wise_items_own)
        tv_sel_party_multiple_sel_own = view.findViewById(R.id.tv_sel_party_multiple_sel_own)
        tv_sel_party_multiple_sel_own.setOnClickListener(this)
        iv_share_partywisesales = view.findViewById(R.id.iv_share_partywisesales)
        iv_share_partywisesales.setOnClickListener(this)
        ll_party_wise_sales_performance = view.findViewById(R.id.ll_party_wise_sales_performance)
        last20NotVisitedList()

        calendar.add(Calendar.MONTH, -1)
        val sdf = SimpleDateFormat("MMM")
        val lastMonthDate: String = sdf.format(calendar.time)
        val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.setTime(sdf.parse(lastMonthDate))
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val firstDate = sdf1.format(calendar.time)
        calendar[Calendar.DAY_OF_MONTH] = daysInMonth
        val lastDate = sdf1.format(calendar.time)
        println("Month " + lastMonthDate)
        println("month in days " + daysInMonth)
        println("1st Date $lastMonthDate month " + firstDate)
        println("End Date $lastMonthDate month " + lastDate)
        val attendanceReq = AttendanceRequest()
        attendanceReq.user_id = Pref.user_id
        attendanceReq.session_token = Pref.session_token
        attendanceReq.start_date = firstDate
        attendanceReq.end_date = lastDate
        callAttendanceListApi(attendanceReq, firstDate, lastDate, daysInMonth)


        /*MTD Calculation*/
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = dateFormat.format(currentDate)
        println(formattedDate)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        Locale.setDefault(Locale.US)
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = 1
        cal[year, month] = day
        println("current Date " + currentDate)
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate1 = dateFormat1.format(currentDate)
        println("formate date " + formattedDate1)
        val inputDate = dateFormat1.parse(formattedDate1)
        val calendar = Calendar.getInstance()
        calendar.time = inputDate
        calendar[Calendar.DAY_OF_MONTH] = 1
        val firstDateOfMonth = calendar.time
        val outputDate = dateFormat1.format(firstDateOfMonth)
        println("First date of month: $outputDate")
        try {
            val totalOrderValueMTDwise = AppDatabase.getDBInstance()!!.orderDetailsListDao().getOrderValueMTD(outputDate, formattedDate1)
            val totalOrderCountMTDwise = AppDatabase.getDBInstance()!!.orderDetailsListDao().getOrderCountMTD(outputDate, formattedDate1)
            val shopDetailsCount = AppDatabase.getDBInstance()!!.addShopEntryDao().countUsers()
            println("Total Order Value+count MTDwise: $totalOrderValueMTDwise $totalOrderCountMTDwise")
            tv_total_ordervalue_frag_own.setText(
                "Total Order Value \n" + String.format(
                    "%.2f",
                    totalOrderValueMTDwise.toDouble()
                )
            )
            tv_totalOrdercount_frag_own_performance.setText("Total Order count \n" + totalOrderCountMTDwise)
            tv_avg_value_frag_own_performance.setText(
                "Avg Order Value \n" + String.format(
                    "%.2f",
                    (totalOrderValueMTDwise.toDouble() / totalOrderCountMTDwise.toDouble())
                )
            )
            val orderavgCount = String.format(
                "%.2f",
                ((totalOrderValueMTDwise.toDouble() / totalOrderCountMTDwise.toDouble()))
            )
            tv_avg_orderCount_frag_own_performance.setText(
                "Avg Order Count \n" + String.format(
                    "%.2f",
                    ((orderavgCount.toDouble() / shopDetailsCount))
                )
            )
            val avgCount = String.format("%.2f", ((orderavgCount.toDouble() / shopDetailsCount)))
            aaChart1.aa_drawChartWithChartModel(
                ChartDataModelNew.configurePolarColumnChart(
                    totalOrderValueMTDwise.toDouble(),
                    totalOrderCountMTDwise.toDouble(),
                    orderavgCount.toDouble(),
                    avgCount.toDouble()
                )
            )
            loadNotProgress()
        } catch (ex: Exception) {
            ex.printStackTrace()
            tv_total_ordervalue_frag_own.setText("Total Order Value \n" + 0)
            tv_totalOrdercount_frag_own_performance.setText("Total Order count \n" + 0)
            tv_avg_value_frag_own_performance.setText("Avg Order Value \n" + 0)
            tv_avg_orderCount_frag_own_performance.setText("Avg Order Count \n" + 0)
            loadNotProgress()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun callAttendanceListApi(
        attendanceReq: AttendanceRequest,
        firstDate: String,
        lastDate: String,
        daysInMonth: Int
    ) {
        val repository = AttendanceRepositoryProvider.provideAttendanceRepository()
        BaseActivity.compositeDisposable.add(
            repository.getAttendanceList(attendanceReq)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val attendanceList = result as AttendanceResponse
                    if (attendanceList.status == NetworkConstant.SUCCESS) {
//                        val filteredAttendanceRecords = attendanceList.shop_list!!.filter { it.login_date!! in formattedFirstDate..formattedLastDate && it.Isonleave!!.equals("false")  }
                        val filteredAttendanceRecords =
                            attendanceList.shop_list!!.filter { it.Isonleave!!.equals("false") }
                        val numPresentAttendances = filteredAttendanceRecords.count()
                        val numAbsentAttendances = daysInMonth - filteredAttendanceRecords.count()
                        println("Present & Absent attendance " + numPresentAttendances + numAbsentAttendances)
                        tv_present_atten.setText(numPresentAttendances.toString())
                        tv_absent_atten.setText(numAbsentAttendances.toString())
                        viewAttendanceReport(numPresentAttendances, numAbsentAttendances)

                    } else if (attendanceList.status == NetworkConstant.SESSION_MISMATCH) {

                    } else if (attendanceList.status == NetworkConstant.NO_DATA) {

                    } else {

                    }
                }, { error ->
                })
        )

    }

    fun viewAttendanceReport(attend: Int, absent: Int) {
        aaChart.aa_drawChartWithChartModel(ChartDataModel.configurePieChart(attend, absent))
        aaChart.aa_drawChartWithChartModel(ChartDataModelNew.configurePieChart(attend, absent))
//        aaChart2.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarBarChart())
/*
       Handler().postDelayed(Runnable {
            var totalH:Int = ll_attend_view.height
            var totalW:Int = ll_attend_view.width
            ll_attend_view.isDrawingCacheEnabled = true
            var b:Bitmap = Bitmap.createBitmap(ll_attend_view.getDrawingCache())
            ll_attend_view.isDrawingCacheEnabled = false
        }, 5000)*/
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun ShareDataAsPdf(ReportName: String) {
        var document: Document = Document(PageSize.A4, 36f, 36f, 36f, 80f)

        val time = System.currentTimeMillis()
        var fileName = ReportName.toUpperCase() + "_" + time
        fileName = fileName.replace("/", "_")
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/FSMApp/PERFORMANCE/"

        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        try {
            //PdfWriter.getInstance(document, FileOutputStream(path + fileName + ".pdf"))
            var pdfWriter: PdfWriter =
                PdfWriter.getInstance(document, FileOutputStream(path + fileName + ".pdf"))

            document.open()
            var font: Font = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD)
            val projectName = Paragraph(ReportName + ":", font)
            projectName.alignment = Element.ALIGN_LEFT
            projectName.spacingAfter = 5f
            document.add(projectName)

            if (ReportName.contains("Attendance REPORT")) {
                ll_attend_view.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_attend_view.getDrawingCache())
                ll_attend_view.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            } else if(ReportName.contains("MTD")) {
                ll_mtd_view.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_mtd_view.getDrawingCache())
                ll_mtd_view.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)

            }else if(ReportName.contains("Last 10 Orders")){
                ll_last10order_frag_own.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_last10order_frag_own.getDrawingCache())
                ll_last10order_frag_own.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            }
            else if(ReportName.contains("PartyWise Sales")){
                ll_party_wise_sales_performance.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_party_wise_sales_performance.getDrawingCache())
                ll_party_wise_sales_performance.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            }
            else{
                ll_activityageing_frag_own.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_activityageing_frag_own.getDrawingCache())
                ll_activityageing_frag_own.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            }

            //val pHead = Paragraph()
            //pHead.add(Chunk(img, 90f, -90f))
            //document.add(pHead)


            document.close()

            var sendingPath = path + fileName + ".pdf"
            if (!TextUtils.isEmpty(sendingPath)) {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    val fileUrl = Uri.parse(sendingPath)
                    val file = File(fileUrl.path)
                    val uri: Uri = FileProvider.getUriForFile(
                        mContext,
                        context!!.applicationContext.packageName.toString() + ".provider",
                        file
                    )
                    shareIntent.type = "image/png"
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                    startActivity(Intent.createChooser(shareIntent, "Share pdf using"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toaster.msgShort(mContext, ex.message.toString())
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }

    }

    fun get_Resized_Bitmap(bmp: Bitmap, newHeight: Int, newWidth: Int): Bitmap? {
        val width = bmp.width
        val height = bmp.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)
        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_frag_performance_attenshare -> {
                ShareDataAsPdf("Attendance REPORT")
            }
            R.id.iv_frag_performance_MTDshare -> {
                ShareDataAsPdf("MTD")
            }
            R.id.tv_frag_own_performnace_sel_shopType -> {
                loadShopTypeList()
            }
            R.id.iv_share_last10 ->{
                ShareDataAsPdf("Last 10 Orders")
            }
            R.id.tv_frag_own_performnace_sel_party ->{
                loadPartyList()
            }
            R.id.iv_share_activityageing->{
                ShareDataAsPdf("Activity Ageing")
            }
            R.id.tv_sel_party_multiple_sel_own->{
                /*Party wise sales Order 17-04-2023*/
                partyWiseSalesOrder()
            }
            R.id.iv_share_partywisesales-> {
                ShareDataAsPdf("PartyWise Sales")
            }
        }
    }

    private fun loadProgress() {
        disableScreen()
        iv_background_color_set.setBackgroundColor(resources.getColor(R.color.color_transparent_blue))
        iv_background_color_set.visibility = View.VISIBLE
        iv_loader_spin.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.loadernew_2)
            .into(iv_loader_spin)
    }

    private fun loadNotProgress() {
        enableScreen()
        iv_background_color_set.visibility = View.GONE
        iv_loader_spin.visibility = View.GONE
    }

    private fun disableScreen() {
        requireActivity().getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
    }

    private fun enableScreen() {
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private fun loadShopTypeList() {
        var mOrderValue: Double = 0.0
        var mOrderCount: Double = 0.0
        val list = AppDatabase.getDBInstance()?.shopTypeDao()?.getAll()
        if(list!!.size>0){
            shopType_list = list as ArrayList<ShopTypeEntity>
            ShopTypeListDialog.newInstance("Select shop Type", shopType_list!!) {
                tv_frag_own_performnace_sel_shopType.text = it.shoptype_name
                sel_shopTypeID = it.shoptype_id!!
                sel_shopTypeName = it.shoptype_name!!
                val totalOrderVShopTypWise = AppDatabase.getDBInstance()!!.orderDetailsListDao().getTotalOrdershopTypewise(sel_shopTypeID)
              /*  try {
                    for (i in totalOrderVShopTypWise) {
                        mOrderValue += i.amount!!.toDouble()
                        mOrderCount++
                    }
                    tv_total_ordervalueshopTypewise_frag_own.setText("Total Order value \n"+String.format("%.2f",mOrderValue))
                    tv_totalOrdercount_shoptypewise_frag_own_performance.setText("Total Order Count \n"+String.format("%.2f",mOrderCount))
                    tv_avgOrderValueshopTypewise_frag_own_performance.setText("Avg Order Value \n" + String.format("%.2f", (mOrderValue / mOrderCount)))
                }catch (ex:Exception){
                    ex.printStackTrace()
                    tv_total_ordervalueshopTypewise_frag_own.setText("Total Order value \n"+0)
                    tv_totalOrdercount_shoptypewise_frag_own_performance.setText("Total Order Count \n"+0)
                    tv_avgOrderValueshopTypewise_frag_own_performance.setText("Avg Order Value \n" +0)
                }*/

                val totalOrderCountShopTypWise = AppDatabase.getDBInstance()!!.orderDetailsListDao().getOrderCountshopTypewise(sel_shopTypeID)
               try{
                    if(totalOrderVShopTypWise == null){
                        tv_total_ordervalueshopTypewise_frag_own.setText("Total Order value \n"+0)
                        tv_totalOrdercount_shoptypewise_frag_own_performance.setText("Total Order Count \n"+0)
                        tv_avgOrderValueshopTypewise_frag_own_performance.setText("Avg Order Value \n" +0)

                    }else{
                        tv_total_ordervalueshopTypewise_frag_own.setText("Total Order value \n"+String.format("%.2f",totalOrderVShopTypWise.toDouble()))
                        tv_totalOrdercount_shoptypewise_frag_own_performance.setText("Total Order Count \n"+String.format("%.2f",totalOrderCountShopTypWise.toDouble()))
                        tv_avgOrderValueshopTypewise_frag_own_performance.setText("Avg Order Value \n" + String.format("%.2f", (totalOrderVShopTypWise.toDouble() / totalOrderCountShopTypWise.toDouble())))
                    }

                }catch (ex:Exception){
                    ex.printStackTrace()
                }
            }.show((mContext as DashboardActivity).supportFragmentManager, "")

        }
    }

    private fun loadPartyList() {
        try{
            mshoplist = AppDatabase.getDBInstance()?.addShopEntryDao()?.all as ArrayList<AddShopDBModelEntity>?
            ShopListDatamodelDialog.newInstance("Select party", mshoplist!!) {
                tv_frag_own_performnace_sel_party.text = it.shopName
                var mshopId = it.shop_id
                var lastVisitedDate = it.lastVisitedDate
                val lastVisitAge = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(lastVisitedDate),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
                tv_frag_own_performance_lastvisitbyago.text = "$lastVisitAge \n Days Ago"
                var lastOrderDate = AppDatabase.getDBInstance()!!.orderDetailsListDao().getLastOrderDate(mshopId)
                try {
                    var date_str = lastOrderDate.split("T")[0]
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val date = format.parse(date_str)
                    val newFormat = SimpleDateFormat("dd-MMM-yy")
                    val formattedDate = newFormat.format(date)
                    var lastOrder = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(formattedDate),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
                    tv_frag_own_performance_lastorderbyago.text = "$lastOrder \n Days Ago"
                } catch (e: Exception) {
                    e.printStackTrace()
                    tv_frag_own_performance_lastorderbyago.text = "0 \n Days Ago"
                }
                try {
                    var lastcollection = AppDatabase.getDBInstance()!!.collectionDetailsDao().getLastCollectionDate(mshopId)
                    var lastcollDatebyAgo = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(lastcollection),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
                    tv_frag_own_performance_lastcollectionbyago.text = "$lastcollDatebyAgo \n Days Ago"
                }catch (e: Exception) {
                    e.printStackTrace()
                    tv_frag_own_performance_lastcollectionbyago.text = "0 \n Days Ago"
                }
                try{
                    var lastlogindayAgo = AppDatabase.getDBInstance()!!.userAttendanceDataDao().getLastLoginDate()
                    var lastlogin = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(lastlogindayAgo),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
                    tv_frag_own_performance_loginbyago.text = "$lastlogin \n Days Ago"
                }catch (e: Exception) {
                    e.printStackTrace()
                    tv_frag_own_performance_loginbyago.text = "0 \n Days Ago"
                }



            }.show((mContext as DashboardActivity).supportFragmentManager, "")
        }
        catch (ex:Exception){
            ex.printStackTrace()
        }

    }

    private fun last20NotVisitedList(){
        var dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        calendar.add(Calendar.DAY_OF_YEAR, -20)
        val date20DaysAgo = calendar.time
        println("last 20 days ago"+ dateFormat1.format(date20DaysAgo))

//        adapterPartynotVisited20days=AdapterPartyNotVisitRecyclerView(mContext,"")
//        frag_own_performance_last20nitvisited_list_rv.adapter = adapterPartynotVisited20days
    }

    private fun partyWiseSalesOrder(){
        println("tag_shop partyWiseSalesOrder call")
        var mshopId:String=""
        var mshopName:String=""
        mshoplist = AppDatabase.getDBInstance()?.addShopEntryDao()?.all as ArrayList<AddShopDBModelEntity>?

        var mShopFilterList :ArrayList<PartyWiseDataModel>? = ArrayList()
        var listwiseData: PartyWiseDataModel
        PartySaleWiseListDatamodelDialog.newInstance("Select party", mshoplist!!,{},object :
            PartySaleWiseListDatamodelDialog.submitListOnCLick{
            override fun onSubmitCLick(list: ArrayList<PerformDataClass>) {
                for (i in 0..list.size-1) {
                    mshopId = list.get(i).shop_id!!
                        try{
                             listwiseData = AppDatabase.getDBInstance()!!.orderDetailsListDao().getTotalShopNTwiseSalesValues(mshopId!!)
                            println("data class "+listwiseData)
                            mShopFilterList!!.add(listwiseData)
                            println("data class adapter size"+mShopFilterList.size)
                            println("data class adapter"+mShopFilterList)
                        }catch(ex:Exception){
                            ex.printStackTrace()
                        }
                }
                setValuePartywiseList(mShopFilterList!!)
            }
        }).show((mContext as DashboardActivity).supportFragmentManager, "")



    }

    fun  setValuePartywiseList(mShopFilterList:ArrayList<PartyWiseDataModel>){
        if(mShopFilterList!!.size>0){

            var nameList = mShopFilterList.map { it.shop_name+"<br />"+it.shop_type_name } as ArrayList<String>
            println("nameL Size"+nameList)
            var valueList = mShopFilterList.map { it.total_sales_value } as ArrayList<String>

            val params: LayoutParams = samplec.getLayoutParams()
            params.height = nameList.size*135
            //params.width = 0
            samplec.setLayoutParams(params)
            samplec.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarBarChart(nameList,valueList))
            //sample_aa.aa_drawChartWithChartModel(ChartDataModelNew.configurePieChart(5,30))

            //adapterPartywiseSales = AdapterPartywiseSalesRecyclerView(mContext,mShopFilterList!!)
            //rcv_party_wise_items_own.adapter = adapterPartywiseSales
            println("tag_shop partyWiseSalesOrder data")
        }
        else{
            Toaster.msgShort(mContext, "No data found")
            println("tag_shop partyWiseSalesOrder no-data")
        }
    }



}



