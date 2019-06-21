package com.danielburgnerjr.flipulator

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment

import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.content.DialogInterface
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.danielburgnerjr.flipulator.model.Calculate

import java.io.*
import java.text.DecimalFormat
import java.util.Locale

/*
import jxl.CellView
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.format.UnderlineStyle
import jxl.write.Formula
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCell
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException
*/

class FinalResultActivity : FragmentActivity() {

    private var spnTimeFrame: Spinner? = null
    private var calC: Calculate? = null
/*
    private var timesBold: WritableCellFormat? = null
    private var times: WritableCellFormat? = null
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_result_activity)

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.time_frame, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnTimeFrame = findViewById<View>(R.id.spnTimeFrame) as Spinner
        spnTimeFrame!!.adapter = aradAdapter

        val intI = getIntent()

        calC = intI.getSerializableExtra("Calculate") as Calculate
/*
        val tvSettings = findViewById<View>(R.id.txtRehabFinance) as TextView
        tvSettings.setText(calC!!.getFinanceValue())

        val tvFRAddress = findViewById<View>(R.id.txtLocationAddress) as TextView
        val tvFRCityStZip = findViewById<View>(R.id.txtLocationCityStZip) as TextView
        val tvSF = findViewById<View>(R.id.txtLocationSqFootage) as TextView
        val tvBedBath = findViewById<View>(R.id.txtLocationBedBath) as TextView

        tvFRAddress.text = "Address:\t\t\t\t\t\t\t " + locL!!.getAddress()
        tvFRCityStZip.text = "City/State/ZIP Code:\t\t " + locL!!.getCity() + ", " + locL!!.getState() + " " + locL!!.getZIPCode()
        tvSF.text = "Square Footage:\t\t\t\t " + locL!!.getSquareFootage() + ""
        tvBedBath.text = "BR/BA:\t\t\t\t\t\t\t\t " + locL!!.getBedrooms() + " BR/" + locL!!.getBathrooms() + " BA"

        val tvSalePrice = findViewById<View>(R.id.txtSalePrice) as TextView
        val tvPercentDown = findViewById<View>(R.id.txtPercentDown) as TextView
        val tvOfferBid = findViewById<View>(R.id.txtOfferBid) as TextView
        val tvRehabBudget = findViewById<View>(R.id.txtRehabBudget) as TextView
        val tvBudgetItems = findViewById<View>(R.id.txtBudgetItems) as TextView

        tvSalePrice.text = "Sale Price:\t\t\t\t\t\t\t $" + String.format("%.0f", smSM!!.getSalesPrice())
        tvPercentDown.text = "Percent Down %:\t\t\t\t " + String.format("%.0f", smSM!!.getPercentDown()) + "%"
        tvOfferBid.text = "Offer/Bid Price:\t\t\t\t $" + String.format("%.0f", smSM!!.getOfferBid())
        tvRehabBudget.text = "Rehab Budget:\t\t\t\t\t $" + String.format("%.0f", rR!!.getBudget())
        tvBudgetItems.text = "Budget Items:\t\t\t\t\t " + rR!!.getBudgetItems()

        val tvDownPayment = findViewById<View>(R.id.txtDownPayment) as TextView
        val tvLoanAmount = findViewById<View>(R.id.txtLoanAmount) as TextView
        val tvInterestRate = findViewById<View>(R.id.txtInterestRate) as TextView
        val tvTerm = findViewById<View>(R.id.txtTerm) as TextView
        val tvMonthlyPmt = findViewById<View>(R.id.txtMonthlyPmt) as TextView

        tvDownPayment.text = "Down Payment:\t\t\t\t $" + String.format("%.0f", smSM!!.getDownPayment())
        tvLoanAmount.text = "Loan Amount:\t\t\t\t\t $" + String.format("%.0f", smSM!!.getLoanAmount())
        tvInterestRate.text = "Interest Rate %:\t\t\t\t " + String.format("%.0f", smSM!!.getInterestRate()) + "%"
        tvTerm.text = "Term (months):\t\t\t\t " + smSM!!.getTerm()
        tvMonthlyPmt.text = "Monthly Pmt:\t\t\t\t\t $" + String.format("%.0f", smSM!!.getMonthlyPmt())

        frF = FinalResult()
        frF!!.setRECost(cemC!!.getSellingPrice(), cemC!!.getRealEstComm())
        frF!!.setBCCost(smSM!!.getSalesPrice(), cemC!!.getBuyClosCost())
        frF!!.setSCCost(smSM!!.getSalesPrice(), cemC!!.getSellClosCost())

        val tvRealEstComm = findViewById<View>(R.id.txtRealEstComm) as TextView
        val tvRealEstCommPer = findViewById<View>(R.id.txtRealEstCommPer) as TextView
        val tvBuyerClosCost = findViewById<View>(R.id.txtBuyerClosCost) as TextView
        val tvBuyerClosCostPer = findViewById<View>(R.id.txtBuyerClosCostPer) as TextView
        val tvSellerClosCost = findViewById<View>(R.id.txtSellerClosCost) as TextView
        val tvSellerClosCostPer = findViewById<View>(R.id.txtSellerClosCostPer) as TextView

        tvRealEstComm.text = "Real Estate Comm:\t\t\t $" + String.format("%.0f", frF!!.getRECost())
        tvRealEstCommPer.text = "Commission %:\t\t\t\t " + String.format("%.0f", cemC!!.getRealEstComm()) + "%"
        tvBuyerClosCost.text = "Buyer Clos Cost:\t\t\t\t $" + String.format("%.0f", frF!!.getBCCost())
        tvBuyerClosCostPer.text = "Buyer Closing Cost %:\t " + String.format("%.0f", cemC!!.getBuyClosCost()) + "%"
        tvSellerClosCost.text = "Sell Clos Cost:\t\t\t\t\t $" + String.format("%.0f", frF!!.getSCCost())
        tvSellerClosCostPer.text = "Seller Closing Cost %:\t " + String.format("%.0f", cemC!!.getSellClosCost()) + "%"

        val tvFMVARV = findViewById<View>(R.id.txtFMVARV) as TextView
        val tvComparables = findViewById<View>(R.id.txtComparables) as TextView
        val tvSellingPrice = findViewById<View>(R.id.txtSellingPrice) as TextView

        tvFMVARV.text = "FMV/ARV:\t\t\t\t\t\t\t $" + String.format("%.0f", cemC!!.getFMVARV())
        tvComparables.text = "Comparables:\t\t\t\t\t $" + String.format("%.0f", cemC!!.getComparables())
        tvSellingPrice.text = "Selling Price:\t\t\t\t\t $" + String.format("%.0f", cemC!!.getSellingPrice())

        spnTimeFrame!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                spnTimeFrame!!.setSelection(i, true)
                val item = adapterView.getItemAtPosition(i)
                var dTimeFrameFactor = 1.0
                // set time frame factor based on time frame value
                if (i == 0) {
                    dTimeFrameFactor = 1.0
                }
                if (i == 1) {
                    dTimeFrameFactor = 1.5
                }
                if (i == 2) {
                    dTimeFrameFactor = 2.0
                }

                val tvResMort = findViewById<View>(R.id.txtMortPmt) as TextView
                val tvResTaxes = findViewById<View>(R.id.txtPropertyTaxes) as TextView
                val tvResIns = findViewById<View>(R.id.txtInsurance) as TextView
                val tvResElect = findViewById<View>(R.id.txtElectric) as TextView
                val tvResWater = findViewById<View>(R.id.txtWater) as TextView
                val tvResGas = findViewById<View>(R.id.txtGas) as TextView
                val tvTotRes = findViewById<View>(R.id.txtTotalReserves) as TextView

                // calculates reserves based on time frame factor
                tvResMort.text = "Mortgage:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getMortgage() * dTimeFrameFactor)
                tvResTaxes.text = "Property Taxes:\t\t\t\t $" + String.format("%.0f", rsR!!.getTaxes() * dTimeFrameFactor)
                tvResIns.text = "Insurance:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getInsurance() * dTimeFrameFactor)
                tvResElect.text = "Electric:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getElectric() * dTimeFrameFactor)
                tvResWater.text = "Water:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getWater() * dTimeFrameFactor)
                tvResGas.text = "Gas:\t\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getGas() * dTimeFrameFactor)
                tvTotRes.text = "Total Reserves:\t\t\t\t $" + String.format("%.0f", rsR!!.getTotalExpenses() * dTimeFrameFactor)

                frF!!.setTotalCost(smSM!!.getOfferBid(), rR!!.getBudget(), rsR!!.getTotalExpenses() * dTimeFrameFactor)
                // if finance rehab flag is not selected, set out of pocket expenses as follows
                if (setS!!.getFinance() !== 2) {
                    frF!!.setOOPExp(smSM!!.getDownPayment(), rsR!!.getTotalExpenses() * dTimeFrameFactor, rR!!.getBudget())
                }
                // if finance rehab flag is selected, set out of pocket expenses as follows
                if (setS!!.getFinance() === 2) {
                    frF!!.setOOPExp(smSM!!.getDownPayment(), rsR!!.getTotalExpenses() * dTimeFrameFactor, 0.0)
                }
                frF!!.setGrossProfit(cemC!!.getSellingPrice())
                frF!!.setCapGains()
                frF!!.setNetProfit()
                if (frF!!.getGrossProfit() < 30000.0) {
                    val adAlertBox = AlertDialog.Builder(this@FinalResultActivity)
                            .setMessage("Your gross profit is below $30K! Would you like to make changes now?")
                            .setPositiveButton("Yes") { arg0, arg1 ->
                                // do something when the button is clicked
                                val intB = Intent(this@FinalResultActivity, SettingsActivity::class.java)
                                intB.putExtra("Settings", setS)
                                intB.putExtra("Location", locL)
                                intB.putExtra("SalesMortgage", smSM)
                                intB.putExtra("Rehab", rR)
                                intB.putExtra("Reserves", rsR)
                                intB.putExtra("ClosExpPropMktInfo", cemC)
                                startActivity(intB)
                                finish()
                            }
                            .setNegativeButton("No") { arg0, arg1 -> }
                            .show()
                }
                frF!!.setROI(cemC!!.getSellingPrice())
                frF!!.setCashOnCash()

                val tvTotalCosts = findViewById<View>(R.id.txtTotalCosts) as TextView
                val tvOutOfPocket = findViewById<View>(R.id.txtOutOfPocketExpenses) as TextView

                tvTotalCosts.text = "Total Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getTotalCost())
                tvOutOfPocket.text = "Out of Pocket Exp:\t\t\t $" + String.format("%.0f", frF!!.getOOPExp())

                val tvBuyerCosts = findViewById<View>(R.id.txtBuyerCosts) as TextView
                val tvGrossProfit = findViewById<View>(R.id.txtGrossProfit) as TextView
                val tvCapGains = findViewById<View>(R.id.txtCapGains) as TextView
                val tvNetProfit = findViewById<View>(R.id.txtNetProfit) as TextView
                val tvMoneyOut = findViewById<View>(R.id.txtMoneyOut) as TextView
                val tvMoneyIn = findViewById<View>(R.id.txtMoneyIn) as TextView
                val tvPercReturn = findViewById<View>(R.id.txtPercReturn) as TextView
                val tvCashCashRet = findViewById<View>(R.id.txtCashCashRet) as TextView

                tvBuyerCosts.text = "Buy + Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getTotalCost())
                tvGrossProfit.text = "Gross Profit:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getGrossProfit())
                tvCapGains.text = "Capital Gains:\t\t\t\t\t $" + String.format("%.0f", frF!!.getCapGains())
                tvNetProfit.text = "Net Profit:\t\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getNetProfit())
                tvMoneyOut.text = "Money Out:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getOOPExp())
                tvMoneyIn.text = "Money In:\t\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getNetProfit())
                tvPercReturn.text = "% Return:\t\t\t\t\t\t\t " + String.format("%.1f", frF!!.getROI()) + "%"
                tvCashCashRet.text = "Cash on Cash Return:\t " + String.format("%.1f", frF!!.getCashOnCash()) + "%"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // sometimes you need nothing here
            }
        }
*/
    }

    // returns to main menu
    fun mainMenu(view: View) {
        val intI = Intent(this@FinalResultActivity, MainActivity::class.java)
        startActivity(intI)
        finish()
    }

    fun prevPage(view: View) {
        val intI = Intent(this, ClosExpPropMktInfoActivity::class.java)
        if (calC != null) {
            intI.putExtra("Calculate", calC)
        }
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {
        val adAlertBox = AlertDialog.Builder(this)
                .setMessage("Do you want to email results as text or as an Excel spreadsheet?")
                .setPositiveButton("Text") { arg0, arg1 ->
                    // do something when the button is clicked
                    //emailPlainText()
                    //close();
                }
                .setNegativeButton("Excel") { arg0, arg1 ->
                    // do something when the button is clicked
/*
                    try {
                        emailExcelSpreadsheet()
                    } catch (e: IOException) {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    } catch (e: WriteException) {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    }
*/
                }
                .show()
    }

/*
    @Throws(IOException::class, WriteException::class)
    fun createSpreadsheet(fDir: File, strXLSFile: String) {
        val fileXls = File(fDir, strXLSFile)
        val wbSettings = WorkbookSettings()

        wbSettings.setLocale(Locale("en", "EN"))

        val workbook = Workbook.createWorkbook(fileXls, wbSettings)
        workbook.createSheet(locL!!.getAddress() + " " + locL!!.getCity() + " " + locL!!.getState() + " " + locL!!.getZIPCode(), 0)
        val excelSheet = workbook.getSheet(0)

        // Lets create a times font
        val times18ptHeader = WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD)
        // Define the cell format
        times = WritableCellFormat(times18ptHeader)
        // Lets automatically wrap the cells
        times!!.setWrap(true)

        // create a bold font
        val times10ptBold = WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false)
        timesBold = WritableCellFormat(times10ptBold)
        // Lets automatically wrap the cells
        timesBold!!.setWrap(true)

        val cv = CellView()
        cv.setFormat(times)
        cv.setFormat(timesBold)
        cv.setAutosize(true)

        // Number and writable cell formats
        val nbfDollar = NumberFormat("$###,##0")
        val wcfDollar = WritableCellFormat(nbfDollar)

        val nbfPercent = NumberFormat("##.0%")
        val wcfPercent = WritableCellFormat(nbfPercent)

        val nbfPercentTwoPlaces = NumberFormat("#0.00%")
        val wcfPercentTwoPlaces = WritableCellFormat(nbfPercentTwoPlaces)

        // Write a few headers
        val wrcOriginal = Label(0, 0, "Original", times)
        excelSheet.addCell(wrcOriginal)
        excelSheet.mergeCells(0, 0, 4, 0)

        val wrcOwnerCarry = Label(7, 0, "Owner Carry Rehab Cost", times)
        excelSheet.addCell(wrcOwnerCarry)
        excelSheet.mergeCells(7, 0, 11, 0)

        val wrcFinanceRehab = Label(13, 0, "Finance Rehab Cost", times)
        excelSheet.addCell(wrcFinanceRehab)
        excelSheet.mergeCells(13, 0, 17, 0)

        val wrcSalesOrig = Label(0, 6, "I. Sales Information", times)
        excelSheet.addCell(wrcSalesOrig)
        excelSheet.mergeCells(0, 6, 1, 6)

        val wrcMortOrig = Label(2, 6, "II. Mortgage Information", times)
        excelSheet.addCell(wrcMortOrig)
        excelSheet.mergeCells(2, 6, 4, 6)

        val wrcSalesOwnerCarry = Label(7, 6, "I. Sales Information", times)
        excelSheet.addCell(wrcSalesOwnerCarry)
        excelSheet.mergeCells(7, 6, 8, 6)

        val wrcMortOwnerCarry = Label(9, 6, "II. Mortgage Information", times)
        excelSheet.addCell(wrcMortOwnerCarry)
        excelSheet.mergeCells(9, 6, 11, 6)

        val wrcSalesFinanceRehab = Label(13, 6, "I. Sales Information", times)
        excelSheet.addCell(wrcSalesFinanceRehab)
        excelSheet.mergeCells(13, 6, 14, 6)

        val wrcMortFinanceRehab = Label(15, 6, "II. Mortgage Information", times)
        excelSheet.addCell(wrcMortFinanceRehab)
        excelSheet.mergeCells(15, 6, 17, 6)

        val wrcReservesOrig = Label(0, 18, "III. Reserves", times)
        excelSheet.addCell(wrcReservesOrig)
        excelSheet.mergeCells(0, 18, 4, 18)

        val wrcReservesOC = Label(7, 18, "III. Reserves", times)
        excelSheet.addCell(wrcReservesOC)
        excelSheet.mergeCells(7, 18, 11, 18)

        val wrcReservesFinRehab = Label(13, 18, "III. Reserves", times)
        excelSheet.addCell(wrcReservesFinRehab)
        excelSheet.mergeCells(13, 18, 17, 18)

        val wrcClosingOrig = Label(0, 34, "IV. Closing Expenses", times)
        excelSheet.addCell(wrcClosingOrig)
        excelSheet.mergeCells(0, 34, 4, 34)

        val wrcClosingOC = Label(7, 34, "IV. Closing Expenses", times)
        excelSheet.addCell(wrcClosingOC)
        excelSheet.mergeCells(7, 34, 11, 34)

        val wrcClosingFinRehab = Label(13, 34, "IV. Closing Expenses", times)
        excelSheet.addCell(wrcClosingFinRehab)
        excelSheet.mergeCells(13, 34, 17, 34)

        val wrcPropMktOrig = Label(0, 45, "V. Property/Market Information", times)
        excelSheet.addCell(wrcPropMktOrig)
        excelSheet.mergeCells(0, 45, 4, 45)

        val wrcPropMktOC = Label(7, 45, "V. Property/Market Information", times)
        excelSheet.addCell(wrcPropMktOC)
        excelSheet.mergeCells(7, 45, 11, 45)

        val wrcPropMktFinRehab = Label(13, 45, "V. Property/Market Information", times)
        excelSheet.addCell(wrcPropMktFinRehab)
        excelSheet.mergeCells(13, 45, 17, 45)

        val wrcROIOrig = Label(0, 58, "VI. Return on Investment", times)
        excelSheet.addCell(wrcROIOrig)
        excelSheet.mergeCells(0, 58, 4, 58)

        val wrcROIOC = Label(7, 58, "VI. Return on Investment", times)
        excelSheet.addCell(wrcROIOC)
        excelSheet.mergeCells(7, 58, 11, 58)

        val wrcROIFinRehab = Label(13, 58, "VI. Return on Investment", times)
        excelSheet.addCell(wrcROIFinRehab)
        excelSheet.mergeCells(13, 58, 17, 58)

        // set up column views
        excelSheet.setColumnView(0, 25)
        excelSheet.setColumnView(1, 14)
        excelSheet.setColumnView(2, 17)
        excelSheet.setColumnView(3, 17)
        excelSheet.setColumnView(4, 16)
        excelSheet.setColumnView(5, 3)
        excelSheet.setColumnView(6, 3)
        excelSheet.setColumnView(7, 29)
        excelSheet.setColumnView(8, 14)
        excelSheet.setColumnView(9, 20)
        excelSheet.setColumnView(10, 17)
        excelSheet.setColumnView(11, 16)
        excelSheet.setColumnView(12, 3)
        excelSheet.setColumnView(13, 29)
        excelSheet.setColumnView(14, 14)
        excelSheet.setColumnView(15, 19)
        excelSheet.setColumnView(16, 17)
        excelSheet.setColumnView(17, 14)

        // set up row views
        excelSheet.setRowView(0, (1.5 * 14 * 20).toInt(), false)
        excelSheet.setRowView(6, (1.5 * 14 * 20).toInt(), false)
        excelSheet.setRowView(18, (1.5 * 14 * 20).toInt(), false)
        excelSheet.setRowView(34, (1.5 * 14 * 20).toInt(), false)
        excelSheet.setRowView(45, (1.5 * 14 * 20).toInt(), false)
        excelSheet.setRowView(58, (1.5 * 14 * 20).toInt(), false)

        // location info - original
        val lblPropAddressOrig: Label
        lblPropAddressOrig = Label(0, 1, "Property Address:", timesBold)
        excelSheet.addCell(lblPropAddressOrig)
        excelSheet.mergeCells(1, 1, 4, 1)
        val lblStreetAddress = Label(1, 1, locL!!.getAddress(), timesBold)
        excelSheet.addCell(lblStreetAddress)

        val lblCityOrig: Label
        lblCityOrig = Label(0, 2, "City:", timesBold)
        excelSheet.addCell(lblCityOrig)
        val lblCity = Label(1, 2, locL!!.getCity(), timesBold)
        excelSheet.addCell(lblCity)

        val lblStateOrig: Label
        lblStateOrig = Label(0, 3, "State:", timesBold)
        excelSheet.addCell(lblStateOrig)
        val lblState = Label(1, 3, locL!!.getState(), timesBold)
        excelSheet.addCell(lblState)

        val lblZipOrig: Label
        lblZipOrig = Label(0, 4, "ZIP:", timesBold)
        excelSheet.addCell(lblZipOrig)
        val lblStZip = Label(1, 4, locL!!.getZIPCode(), timesBold)
        excelSheet.addCell(lblStZip)

        val lblSquareFootageOrig: Label
        lblSquareFootageOrig = Label(2, 2, "Square Footage:", timesBold)
        excelSheet.addCell(lblSquareFootageOrig)
        excelSheet.mergeCells(3, 2, 4, 2)
        val lblSquareFootage = Label(3, 2, locL!!.getSquareFootage() + "", timesBold)
        excelSheet.addCell(lblSquareFootage)

        val lblBROrig: Label
        lblBROrig = Label(2, 3, "BR:", timesBold)
        excelSheet.addCell(lblBROrig)
        excelSheet.mergeCells(3, 3, 4, 3)
        val lblBR = Label(3, 3, locL!!.getBedrooms() + "", timesBold)
        excelSheet.addCell(lblBR)

        val lblBAOrig: Label
        lblBAOrig = Label(2, 4, "BA:", timesBold)
        excelSheet.addCell(lblBAOrig)
        excelSheet.mergeCells(3, 4, 4, 4)
        val lblBA = Label(3, 4, locL!!.getBathrooms() + "", timesBold)
        excelSheet.addCell(lblBA)

        // sales info - original
        val lblSalesPriceOrig: Label
        lblSalesPriceOrig = Label(0, 8, "Sales Price:", timesBold)
        excelSheet.addCell(lblSalesPriceOrig)
        val nbrSalesPriceO = Number(1, 8, smSM!!.getSalesPrice(), wcfDollar)
        excelSheet.addCell(nbrSalesPriceO)

        val lblPercentDownOrig: Label
        lblPercentDownOrig = Label(0, 10, "Percent Down:", timesBold)
        excelSheet.addCell(lblPercentDownOrig)
        val nbrPercentDownO = Number(1, 10, smSM!!.getPercentDown() / 100, wcfPercent)
        excelSheet.addCell(nbrPercentDownO)

        val lblOfferBidOrig: Label
        lblOfferBidOrig = Label(0, 12, "Offer/Bid Price:", timesBold)
        excelSheet.addCell(lblOfferBidOrig)
        val nbrOfferBidO = Number(1, 12, smSM!!.getOfferBid(), wcfDollar)
        excelSheet.addCell(nbrOfferBidO)

        val lblRehabOrig: Label
        lblRehabOrig = Label(0, 14, "Rehab Budget:", timesBold)
        excelSheet.addCell(lblRehabOrig)
        val nbrRehabO = Number(1, 14, rR!!.getBudget(), wcfDollar)
        excelSheet.addCell(nbrRehabO)

        // mortgage info - original
        val lblDownPaymentOrig: Label
        lblDownPaymentOrig = Label(2, 8, "Down Payment:", timesBold)
        excelSheet.addCell(lblDownPaymentOrig)
        var buf = StringBuffer()
        buf.append("SUM(B11*B9)")
        val forDownPaymentO = Formula(3, 8, buf.toString(), wcfDollar)
        excelSheet.addCell(forDownPaymentO)

        val lblLoanAmountOrig: Label
        lblLoanAmountOrig = Label(2, 10, "Loan Amount:", timesBold)
        excelSheet.addCell(lblLoanAmountOrig)
        buf = StringBuffer()
        buf.append("(B13-D9)")
        val forLoanAmountO = Formula(3, 10, buf.toString(), wcfDollar)
        excelSheet.addCell(forLoanAmountO)

        val lblInterestRateOrig: Label
        lblInterestRateOrig = Label(2, 12, "Interest Rate:", timesBold)
        excelSheet.addCell(lblInterestRateOrig)
        val nbrInterestRateO = Number(3, 12, smSM!!.getInterestRate() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrInterestRateO)

        val lblTermOrig: Label
        lblTermOrig = Label(2, 14, "Term:", timesBold)
        excelSheet.addCell(lblTermOrig)
        val nbrTermO = Number(3, 14, smSM!!.getTerm())
        excelSheet.addCell(nbrTermO)

        val lblMonthlyPmtOrig: Label
        lblMonthlyPmtOrig = Label(2, 16, "Monthly Pmt:", timesBold)
        excelSheet.addCell(lblMonthlyPmtOrig)
        buf = StringBuffer()
        buf.append("SUM(D13*D11/12)")
        val forMonthlyPmtO = Formula(3, 16, buf.toString(), wcfDollar)
        excelSheet.addCell(forMonthlyPmtO)

        // reserves info - original
        val lblMonthlyReservesOrig: Label
        lblMonthlyReservesOrig = Label(1, 19, "Monthly", timesBold)
        excelSheet.addCell(lblMonthlyReservesOrig)

        val lblSixMonthReservesOrig: Label
        lblSixMonthReservesOrig = Label(2, 19, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthReservesOrig)

        val lblNineMonthReservesOrig: Label
        lblNineMonthReservesOrig = Label(3, 19, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthReservesOrig)

        val lblTwelveMonthReservesOrig: Label
        lblTwelveMonthReservesOrig = Label(4, 19, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthReservesOrig)

        val lblMortgageReservesOrig: Label
        lblMortgageReservesOrig = Label(0, 20, "Mortgage", timesBold)
        excelSheet.addCell(lblMortgageReservesOrig)

        buf = StringBuffer()
        buf.append("(D17)")
        val forMortgageMonthO = Formula(1, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageMonthO)

        buf = StringBuffer()
        buf.append("SUM(D17*6)")
        val forMortgageSixMonthO = Formula(2, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageSixMonthO)

        buf = StringBuffer()
        buf.append("SUM(D17*9)")
        val forMortgageNineMonthO = Formula(3, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(D13*D11)")
        val forMortgageAnnualO = Formula(4, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageAnnualO)

        val lblTaxesReservesOrig: Label
        lblTaxesReservesOrig = Label(0, 22, "Taxes", timesBold)
        excelSheet.addCell(lblTaxesReservesOrig)

        buf = StringBuffer()
        buf.append("(C23/6)")
        val forTaxesMonthO = Formula(1, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesMonthO)

        val nbrTaxesO = Number(2, 22, rsR!!.getTaxes(), wcfDollar)
        excelSheet.addCell(nbrTaxesO)

        buf = StringBuffer()
        buf.append("SUM(C23*9/6)")
        val forTaxesNineMonthO = Formula(3, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(C23*2)")
        val forTaxesAnnualO = Formula(4, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesAnnualO)

        val lblInsuranceReservesOrig: Label
        lblInsuranceReservesOrig = Label(0, 24, "Insurance", timesBold)
        excelSheet.addCell(lblInsuranceReservesOrig)

        buf = StringBuffer()
        buf.append("(C25/6)")
        val forInsuranceMonthO = Formula(1, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceMonthO)

        val nbrInsuranceO = Number(2, 24, rsR!!.getInsurance(), wcfDollar)
        excelSheet.addCell(nbrInsuranceO)

        buf = StringBuffer()
        buf.append("SUM(C25*9/6)")
        val forInsuranceNineMonthO = Formula(3, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(C25*2)")
        val forInsuranceAnnualO = Formula(4, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceAnnualO)

        val lblWaterReservesOriginal: Label
        lblWaterReservesOriginal = Label(0, 26, "Water", timesBold)
        excelSheet.addCell(lblWaterReservesOriginal)

        buf = StringBuffer()
        buf.append("(C27/6)")
        val forWaterMonthO = Formula(1, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterMonthO)

        val nbrWaterO = Number(2, 26, rsR!!.getWater(), wcfDollar)
        excelSheet.addCell(nbrWaterO)

        buf = StringBuffer()
        buf.append("SUM(C27*9/6)")
        val forWaterNineMonthO = Formula(3, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(C27*2)")
        val forWaterAnnualO = Formula(4, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterAnnualO)

        val lblGasReservesOriginal: Label
        lblGasReservesOriginal = Label(0, 28, "Gas", timesBold)
        excelSheet.addCell(lblGasReservesOriginal)

        buf = StringBuffer()
        buf.append("(C29/6)")
        val forGasMonthO = Formula(1, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasMonthO)

        val nbrGasO = Number(2, 28, rsR!!.getGas(), wcfDollar)
        excelSheet.addCell(nbrGasO)

        buf = StringBuffer()
        buf.append("SUM(C29*9/6)")
        val forGasNineMonthO = Formula(3, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(C29*2)")
        val forGasAnnualO = Formula(4, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasAnnualO)

        val lblElectricReservesOriginal: Label
        lblElectricReservesOriginal = Label(0, 30, "Electric", timesBold)
        excelSheet.addCell(lblElectricReservesOriginal)

        buf = StringBuffer()
        buf.append("(C31/6)")
        val forElectricMonthO = Formula(1, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricMonthO)

        val nbrElectricO = Number(2, 30, rsR!!.getElectric(), wcfDollar)
        excelSheet.addCell(nbrElectricO)

        buf = StringBuffer()
        buf.append("SUM(C31*9/6)")
        val forElectricNineMonthO = Formula(3, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(C31*2)")
        val forElectricAnnualO = Formula(4, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricAnnualO)

        val lblTotalReservesOriginal: Label
        lblTotalReservesOriginal = Label(0, 32, "Total Expenses", timesBold)
        excelSheet.addCell(lblTotalReservesOriginal)

        buf = StringBuffer()
        buf.append("SUM(B21:B31)")
        val forTotalMonthO = Formula(1, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalMonthO)

        buf = StringBuffer()
        buf.append("SUM(C21:C31)")
        val forTotalSixMonthO = Formula(2, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalSixMonthO)

        buf = StringBuffer()
        buf.append("SUM(D21:D31)")
        val forTotalNineMonthO = Formula(3, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalNineMonthO)

        buf = StringBuffer()
        buf.append("SUM(E21:E31)")
        val forTotalAnnualO = Formula(4, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalAnnualO)

        // closing expenses - original
        val lblRealEstateCommissionOriginal: Label
        lblRealEstateCommissionOriginal = Label(0, 36, "Real Estate Commission", timesBold)
        excelSheet.addCell(lblRealEstateCommissionOriginal)

        buf = StringBuffer()
        buf.append("(C50*D37)")
        val forRealEstateCommissionO = Formula(1, 36, buf.toString(), wcfDollar)
        excelSheet.addCell(forRealEstateCommissionO)

        val lblCommissionPercentageOriginal: Label
        lblCommissionPercentageOriginal = Label(2, 36, "Commission %", timesBold)
        excelSheet.addCell(lblCommissionPercentageOriginal)

        val nbrCommissionPercentageO = Number(3, 36, cemC!!.getRealEstComm() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrCommissionPercentageO)

        val lblBuyerClosingCostOriginal: Label
        lblBuyerClosingCostOriginal = Label(0, 38, "Buyer Closing Costs", timesBold)
        excelSheet.addCell(lblBuyerClosingCostOriginal)

        buf = StringBuffer()
        buf.append("(B13*D39)")
        val forBuyerClosingCostO = Formula(1, 38, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerClosingCostO)

        val lblBuyerClosingCostPercentageOriginal: Label
        lblBuyerClosingCostPercentageOriginal = Label(2, 38, "Closing Costs %", timesBold)
        excelSheet.addCell(lblBuyerClosingCostPercentageOriginal)

        val nbrBuyerClosingCostPercentageO = Number(3, 38, cemC!!.getBuyClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrBuyerClosingCostPercentageO)

        val lblSellerClosingCostOriginal: Label
        lblSellerClosingCostOriginal = Label(0, 39, "Seller Closing Costs", timesBold)
        excelSheet.addCell(lblSellerClosingCostOriginal)

        buf = StringBuffer()
        buf.append("(B9*D40)")
        val forSellerClosingCostO = Formula(1, 39, buf.toString(), wcfDollar)
        excelSheet.addCell(forSellerClosingCostO)

        val lblSellerClosingCostPercentageOriginal: Label
        lblSellerClosingCostPercentageOriginal = Label(2, 39, "Closing Costs %", timesBold)
        excelSheet.addCell(lblSellerClosingCostPercentageOriginal)

        val nbrSellerClosingCostPercentageO = Number(3, 39, cemC!!.getSellClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrSellerClosingCostPercentageO)

        val lblSixMonthClosExpOrig: Label
        lblSixMonthClosExpOrig = Label(2, 41, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthClosExpOrig)

        val lblNineMonthClosExpOrig: Label
        lblNineMonthClosExpOrig = Label(3, 41, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthClosExpOrig)

        val lblTwelveMonthClosExpOrig: Label
        lblTwelveMonthClosExpOrig = Label(4, 41, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthClosExpOrig)

        val lblTotalCostsOriginal: Label
        lblTotalCostsOriginal = Label(0, 42, "Total Costs", timesBold)
        excelSheet.addCell(lblTotalCostsOriginal)

        buf = StringBuffer()
        buf.append("(B13+B15+C33+B37+B39)")
        val forTotalCostsSixMonthO = Formula(2, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsSixMonthO)

        buf = StringBuffer()
        buf.append("(B13+B15+D33+B37+B39)")
        val forTotalCostsNineMonthO = Formula(3, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsNineMonthO)

        buf = StringBuffer()
        buf.append("(B13+B15+E33+B37+B39)")
        val forTotalCostsAnnualO = Formula(4, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsAnnualO)

        val lblOutOfPocketOriginal: Label
        lblOutOfPocketOriginal = Label(0, 43, "Out of Pocket Expenses", timesBold)
        excelSheet.addCell(lblOutOfPocketOriginal)

        buf = StringBuffer()
        buf.append("(B15+B39+C33+D9)")
        val forOutOfPocketSixMonthO = Formula(2, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketSixMonthO)

        buf = StringBuffer()
        buf.append("(B15+B39+D33+D9)")
        val forOutOfPocketNineMonthO = Formula(3, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketNineMonthO)

        buf = StringBuffer()
        buf.append("(B15+B39+E33+D9)")
        val forOutOfPocketAnnualO = Formula(4, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketAnnualO)

        // property market info - original
        val lblSixMonthPropMktOrig: Label
        lblSixMonthPropMktOrig = Label(2, 46, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthPropMktOrig)

        val lblNineMonthPropMktOrig: Label
        lblNineMonthPropMktOrig = Label(3, 46, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthPropMktOrig)

        val lblTwelveMonthPropMktOrig: Label
        lblTwelveMonthPropMktOrig = Label(4, 46, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthPropMktOrig)

        val lblFMVARVOriginal: Label
        lblFMVARVOriginal = Label(0, 47, "FMV/ARV", timesBold)
        excelSheet.addCell(lblFMVARVOriginal)

        val nbrFMVARVSixMonthO = Number(2, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVSixMonthO)

        val nbrFMVARVNineMonthO = Number(3, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVNineMonthO)

        val nbrFMVARVAnnualO = Number(4, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVAnnualO)

        val lblComparablesOriginal: Label
        lblComparablesOriginal = Label(0, 48, "Comparables", timesBold)
        excelSheet.addCell(lblComparablesOriginal)

        val nbrComparablesSixMonthO = Number(2, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesSixMonthO)

        val nbrComparablesNineMonthO = Number(3, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesNineMonthO)

        val nbrComparablesAnnualO = Number(4, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesAnnualO)

        val lblSellingPriceOriginal: Label
        lblSellingPriceOriginal = Label(0, 49, "Selling Price", timesBold)
        excelSheet.addCell(lblSellingPriceOriginal)

        val nbrSellingPriceSixMonthO = Number(2, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceSixMonthO)

        val nbrSellingPriceNineMonthO = Number(3, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceNineMonthO)

        val nbrSellingPriceAnnualO = Number(4, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceAnnualO)

        val lblBuyerCostsOriginal: Label
        lblBuyerCostsOriginal = Label(0, 51, "Buyer's Costs", timesBold)
        excelSheet.addCell(lblBuyerCostsOriginal)

        buf = StringBuffer()
        buf.append("(C43)")
        val forBuyerCostsSixMonthO = Formula(2, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsSixMonthO)

        buf = StringBuffer()
        buf.append("(D43)")
        val forBuyerCostsNineMonthO = Formula(3, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsNineMonthO)

        buf = StringBuffer()
        buf.append("(E43)")
        val forBuyerCostsAnnualO = Formula(4, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsAnnualO)

        val lblGrossProfitOriginal: Label
        lblGrossProfitOriginal = Label(0, 52, "Gross Profit", timesBold)
        excelSheet.addCell(lblGrossProfitOriginal)

        buf = StringBuffer()
        buf.append("(C50-C52)")
        val forGrossProfitSixMonthO = Formula(2, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitSixMonthO)

        buf = StringBuffer()
        buf.append("(D50-D52)")
        val forGrossProfitNineMonthO = Formula(3, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitNineMonthO)

        buf = StringBuffer()
        buf.append("(E50-E52)")
        val forGrossProfitAnnualO = Formula(4, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitAnnualO)

        val lblCapitalGainsOriginal: Label
        lblCapitalGainsOriginal = Label(0, 53, "Capital Gains", timesBold)
        excelSheet.addCell(lblCapitalGainsOriginal)

        buf = StringBuffer()
        buf.append("(C53*0.3)")
        val forCapitalGainsSixMonthO = Formula(2, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsSixMonthO)

        buf = StringBuffer()
        buf.append("(D53*0.3)")
        val forCapitalGainsNineMonthO = Formula(3, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsNineMonthO)

        buf = StringBuffer()
        buf.append("(E53*0.3)")
        val forCapitalGainsAnnualO = Formula(4, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsAnnualO)

        val lblNetProfitOriginal: Label
        lblNetProfitOriginal = Label(0, 54, "Net Profit", timesBold)
        excelSheet.addCell(lblNetProfitOriginal)

        buf = StringBuffer()
        buf.append("(C53-C54)")
        val forNetProfitSixMonthO = Formula(2, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitSixMonthO)

        buf = StringBuffer()
        buf.append("(D53-D54)")
        val forNetProfitNineMonthO = Formula(3, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitNineMonthO)

        buf = StringBuffer()
        buf.append("(E53-E54)")
        val forNetProfitAnnualO = Formula(4, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitAnnualO)

        val lblRateOfReturnOriginal: Label
        lblRateOfReturnOriginal = Label(0, 56, "Rate of Return", timesBold)
        excelSheet.addCell(lblRateOfReturnOriginal)

        buf = StringBuffer()
        buf.append("(C55/C50)")
        val forRateOfReturnSixMonthO = Formula(2, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnSixMonthO)

        buf = StringBuffer()
        buf.append("(D55/D50)")
        val forRateOfReturnNineMonthO = Formula(3, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnNineMonthO)

        buf = StringBuffer()
        buf.append("(E55/E50)")
        val forRateOfReturnAnnualO = Formula(4, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnAnnualO)

        // rate of return info - original
        val lblSixMonthRateReturnOrig: Label
        lblSixMonthRateReturnOrig = Label(2, 59, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthRateReturnOrig)

        val lblNineMonthRateReturnOrig: Label
        lblNineMonthRateReturnOrig = Label(3, 59, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthRateReturnOrig)

        val lblTwelveMonthRateReturnOrig: Label
        lblTwelveMonthRateReturnOrig = Label(4, 59, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthRateReturnOrig)

        val lblMoneyOutOriginal: Label
        lblMoneyOutOriginal = Label(0, 60, "Money Out", timesBold)
        excelSheet.addCell(lblMoneyOutOriginal)

        buf = StringBuffer()
        buf.append("(C43)")
        val forMoneyOutSixMonthO = Formula(2, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutSixMonthO)

        buf = StringBuffer()
        buf.append("(D43)")
        val forMoneyOutNineMonthO = Formula(3, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutNineMonthO)

        buf = StringBuffer()
        buf.append("(E43)")
        val forMoneyOutAnnualO = Formula(4, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutAnnualO)

        val lblMoneyInOriginal: Label
        lblMoneyInOriginal = Label(0, 61, "Money In", timesBold)
        excelSheet.addCell(lblMoneyInOriginal)

        buf = StringBuffer()
        buf.append("(C55)")
        val forMoneyInSixMonthO = Formula(2, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInSixMonthO)

        buf = StringBuffer()
        buf.append("(D55)")
        val forMoneyInNineMonthO = Formula(3, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInNineMonthO)

        buf = StringBuffer()
        buf.append("(E55)")
        val forMoneyInAnnualO = Formula(4, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInAnnualO)

        val lblCashCashReturnOriginal: Label
        lblCashCashReturnOriginal = Label(0, 62, "Cash on Cash Return", timesBold)
        excelSheet.addCell(lblCashCashReturnOriginal)

        buf = StringBuffer()
        buf.append("(C62/C61)")
        val forCashCashReturnSixMonthO = Formula(2, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnSixMonthO)

        buf = StringBuffer()
        buf.append("(D62/D61)")
        val forCashCashReturnNineMonthO = Formula(3, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnNineMonthO)

        buf = StringBuffer()
        buf.append("(E62/E61)")
        val forCashCashReturnAnnualO = Formula(4, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnAnnualO)

        // settings and budget items
        val lblRehab: Label
        lblRehab = Label(0, 64, "Rehab Type", timesBold)
        excelSheet.addCell(lblRehab)
        val lblRehabType = Label(1, 64, setS!!.getRehab() + "", timesBold)
        excelSheet.addCell(lblRehabType)
        val lblFinance: Label
        lblFinance = Label(2, 64, "Finance Type", timesBold)
        excelSheet.addCell(lblFinance)
        val lblFinanceType = Label(3, 64, setS!!.getFinance() + "", timesBold)
        excelSheet.addCell(lblFinanceType)
        val lblBudgetItems: Label
        lblBudgetItems = Label(0, 65, "Budget Items", timesBold)
        excelSheet.addCell(lblBudgetItems)
        val lblBudgetItemsList = Label(1, 65, rR!!.getBudgetItems(), timesBold)
        excelSheet.addCell(lblBudgetItemsList)
        excelSheet.mergeCells(1, 65, 4, 67)

        // location info - owner carry
        val lblPropAddressOwnerCarry: Label
        lblPropAddressOwnerCarry = Label(7, 1, "Property Address:", timesBold)
        excelSheet.addCell(lblPropAddressOwnerCarry)
        excelSheet.mergeCells(8, 1, 11, 1)
        val lblStreetAddressOC = Label(8, 1, locL!!.getAddress(), timesBold)
        excelSheet.addCell(lblStreetAddressOC)

        val lblCityOwnerCarry: Label
        lblCityOwnerCarry = Label(7, 2, "City:", timesBold)
        excelSheet.addCell(lblCityOwnerCarry)
        val lblCityOC = Label(8, 2, locL!!.getCity(), timesBold)
        excelSheet.addCell(lblCityOC)

        val lblStOwnerCarry: Label
        lblStOwnerCarry = Label(7, 3, "State:", timesBold)
        excelSheet.addCell(lblStOwnerCarry)
        val lblStOC = Label(8, 3, locL!!.getState(), timesBold)
        excelSheet.addCell(lblStOC)

        val lblZipOwnerCarry: Label
        lblZipOwnerCarry = Label(7, 4, "ZIP:", timesBold)
        excelSheet.addCell(lblZipOwnerCarry)
        val lblZipOC = Label(8, 4, locL!!.getZIPCode(), timesBold)
        excelSheet.addCell(lblZipOC)

        val lblSquareFootageOwnerCarry: Label
        lblSquareFootageOwnerCarry = Label(9, 2, "Square Footage:", timesBold)
        excelSheet.addCell(lblSquareFootageOwnerCarry)
        excelSheet.mergeCells(10, 2, 11, 2)
        val lblSquareFootageOC = Label(10, 2, locL!!.getSquareFootage() + "", timesBold)
        excelSheet.addCell(lblSquareFootageOC)

        val lblBROwnerCarry: Label
        lblBROwnerCarry = Label(9, 3, "BR:", timesBold)
        excelSheet.addCell(lblBROwnerCarry)
        excelSheet.mergeCells(10, 3, 11, 3)
        val lblBROC = Label(10, 3, locL!!.getBedrooms() + "", timesBold)
        excelSheet.addCell(lblBROC)

        val lblBAOwnerCarry: Label
        lblBAOwnerCarry = Label(9, 4, "BA:", timesBold)
        excelSheet.addCell(lblBAOwnerCarry)
        excelSheet.mergeCells(10, 4, 11, 4)
        val lblBAOC = Label(10, 4, locL!!.getBathrooms() + "", timesBold)
        excelSheet.addCell(lblBAOC)

        // sales info - owner carry
        val lblSalesPriceOwnerCarry: Label
        lblSalesPriceOwnerCarry = Label(7, 8, "Sales Price:", timesBold)
        excelSheet.addCell(lblSalesPriceOwnerCarry)
        val nbrSalesPriceOC = Number(8, 8, smSM!!.getSalesPrice(), wcfDollar)
        excelSheet.addCell(nbrSalesPriceOC)

        val lblPercentDownOwnerCarry: Label
        lblPercentDownOwnerCarry = Label(7, 10, "Percent Down:", timesBold)
        excelSheet.addCell(lblPercentDownOwnerCarry)
        val nbrPercentDownOC = Number(8, 10, smSM!!.getPercentDown() / 100, wcfPercent)
        excelSheet.addCell(nbrPercentDownOC)

        val lblOfferBidOwnerCarry: Label
        lblOfferBidOwnerCarry = Label(7, 12, "Offer/Bid Price:", timesBold)
        excelSheet.addCell(lblOfferBidOwnerCarry)
        val nbrOfferBidOC = Number(8, 12, smSM!!.getOfferBid(), wcfDollar)
        excelSheet.addCell(nbrOfferBidOC)

        val lblRehabOwnerCarry: Label
        lblRehabOwnerCarry = Label(7, 14, "Rehab Budget:", timesBold)
        excelSheet.addCell(lblRehabOwnerCarry)
        val nbrRehabOC = Number(8, 14, rR!!.getBudget(), wcfDollar)
        excelSheet.addCell(nbrRehabOC)

        // mortgage info - owner carry
        val lblDownPaymentOwnerCarry: Label
        lblDownPaymentOwnerCarry = Label(9, 8, "Down Payment:", timesBold)
        excelSheet.addCell(lblDownPaymentOwnerCarry)
        buf = StringBuffer()
        buf.append("SUM(I11*I9)")
        val forDownPaymentOC = Formula(10, 8, buf.toString(), wcfDollar)
        excelSheet.addCell(forDownPaymentOC)

        val lblLoanAmountOwnerCarry: Label
        lblLoanAmountOwnerCarry = Label(9, 10, "Loan Amount:", timesBold)
        excelSheet.addCell(lblLoanAmountOwnerCarry)
        buf = StringBuffer()
        buf.append("(I13-K9)")
        val forLoanAmountOC = Formula(10, 10, buf.toString(), wcfDollar)
        excelSheet.addCell(forLoanAmountOC)

        val lblInterestRateOwnerCarry: Label
        lblInterestRateOwnerCarry = Label(9, 12, "Interest Rate:", timesBold)
        excelSheet.addCell(lblInterestRateOwnerCarry)
        val nbrInterestRateOC = Number(10, 12, smSM!!.getInterestRate() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrInterestRateOC)

        val lblTermOwnerCarry: Label
        lblTermOwnerCarry = Label(9, 14, "Term:", timesBold)
        excelSheet.addCell(lblTermOwnerCarry)
        val nbrTermOC = Number(10, 14, smSM!!.getTerm())
        excelSheet.addCell(nbrTermOC)

        val lblMonthlyPmtOwnerCarry: Label
        lblMonthlyPmtOwnerCarry = Label(9, 16, "Monthly Pmt:", timesBold)
        excelSheet.addCell(lblMonthlyPmtOwnerCarry)
        buf = StringBuffer()
        buf.append("SUM(K13*K11/12)")
        val forMonthlyPmtOC = Formula(10, 16, buf.toString(), wcfDollar)
        excelSheet.addCell(forMonthlyPmtOC)

        // reserves info - owner carry
        val lblMonthlyReservesOwnerCarry: Label
        lblMonthlyReservesOwnerCarry = Label(8, 19, "Monthly", timesBold)
        excelSheet.addCell(lblMonthlyReservesOwnerCarry)

        val lblSixMonthReservesOwnerCarry: Label
        lblSixMonthReservesOwnerCarry = Label(9, 19, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthReservesOwnerCarry)

        val lblNineMonthReservesOwnerCarry: Label
        lblNineMonthReservesOwnerCarry = Label(10, 19, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthReservesOwnerCarry)

        val lblTwelveMonthReservesOwnerCarry: Label
        lblTwelveMonthReservesOwnerCarry = Label(11, 19, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthReservesOwnerCarry)

        val lblMortgageReservesOwnerCarry: Label
        lblMortgageReservesOwnerCarry = Label(7, 20, "Mortgage", timesBold)
        excelSheet.addCell(lblMortgageReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(K17)")
        val forMortgageMonthOC = Formula(8, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageMonthOC)

        buf = StringBuffer()
        buf.append("SUM(K17*6)")
        val forMortgageSixMonthOC = Formula(9, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageSixMonthOC)

        buf = StringBuffer()
        buf.append("SUM(K17*9)")
        val forMortgageNineMonthOC = Formula(10, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(K13*K11)")
        val forMortgageAnnualOC = Formula(11, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageAnnualOC)

        val lblTaxesReservesOwnerCarry: Label
        lblTaxesReservesOwnerCarry = Label(7, 22, "Taxes", timesBold)
        excelSheet.addCell(lblTaxesReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(J23/6)")
        val forTaxesMonthOC = Formula(8, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesMonthOC)

        val nbrTaxesOC = Number(9, 22, rsR!!.getTaxes(), wcfDollar)
        excelSheet.addCell(nbrTaxesOC)

        buf = StringBuffer()
        buf.append("SUM(J23*9/6)")
        val forTaxesNineMonthOC = Formula(10, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J23*2)")
        val forTaxesAnnualOC = Formula(11, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesAnnualOC)

        val lblInsuranceReservesOwnerCarry: Label
        lblInsuranceReservesOwnerCarry = Label(7, 24, "Insurance", timesBold)
        excelSheet.addCell(lblInsuranceReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(J25/6)")
        val forInsuranceMonthOC = Formula(8, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceMonthOC)

        val nbrInsuranceOC = Number(9, 24, rsR!!.getInsurance(), wcfDollar)
        excelSheet.addCell(nbrInsuranceOC)

        buf = StringBuffer()
        buf.append("SUM(J25*9/6)")
        val forInsuranceNineMonthOC = Formula(10, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J25*2)")
        val forInsuranceAnnualOC = Formula(11, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceAnnualOC)

        val lblWaterReservesOwnerCarry: Label
        lblWaterReservesOwnerCarry = Label(7, 26, "Water", timesBold)
        excelSheet.addCell(lblWaterReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(J27/6)")
        val forWaterMonthOC = Formula(8, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterMonthOC)

        val nbrWaterOC = Number(9, 26, rsR!!.getWater(), wcfDollar)
        excelSheet.addCell(nbrWaterOC)

        buf = StringBuffer()
        buf.append("SUM(J27*9/6)")
        val forWaterNineMonthOC = Formula(10, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J27*2)")
        val forWaterAnnualOC = Formula(11, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterAnnualOC)

        val lblGasReservesOwnerCarry: Label
        lblGasReservesOwnerCarry = Label(7, 28, "Gas", timesBold)
        excelSheet.addCell(lblGasReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(J29/6)")
        val forGasMonthOC = Formula(8, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasMonthOC)

        val nbrGasOC = Number(9, 28, rsR!!.getGas(), wcfDollar)
        excelSheet.addCell(nbrGasOC)

        buf = StringBuffer()
        buf.append("SUM(J29*9/6)")
        val forGasNineMonthOC = Formula(10, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J29*2)")
        val forGasAnnualOC = Formula(11, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasAnnualOC)

        val lblElectricReservesOwnerCarry: Label
        lblElectricReservesOwnerCarry = Label(7, 30, "Electric", timesBold)
        excelSheet.addCell(lblElectricReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("(J31/6)")
        val forElectricMonthOC = Formula(8, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricMonthOC)

        val nbrElectricOC = Number(9, 30, rsR!!.getElectric(), wcfDollar)
        excelSheet.addCell(nbrElectricOC)

        buf = StringBuffer()
        buf.append("SUM(J31*9/6)")
        val forElectricNineMonthOC = Formula(10, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J31*2)")
        val forElectricAnnualOC = Formula(11, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricAnnualOC)

        val lblTotalReservesOwnerCarry: Label
        lblTotalReservesOwnerCarry = Label(7, 32, "Total Expenses", timesBold)
        excelSheet.addCell(lblTotalReservesOwnerCarry)

        buf = StringBuffer()
        buf.append("SUM(I21:I31)")
        val forTotalMonthOC = Formula(8, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalMonthOC)

        buf = StringBuffer()
        buf.append("SUM(J21:J31)")
        val forTotalSixMonthOC = Formula(9, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalSixMonthOC)

        buf = StringBuffer()
        buf.append("SUM(K21:K31)")
        val forTotalNineMonthOC = Formula(10, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalNineMonthOC)

        buf = StringBuffer()
        buf.append("SUM(L21:L31)")
        val forTotalAnnualOC = Formula(11, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalAnnualOC)

        // closing expenses - owner carry
        val lblRealEstateCommissionOwnerCarry: Label
        lblRealEstateCommissionOwnerCarry = Label(7, 36, "Real Estate Commission", timesBold)
        excelSheet.addCell(lblRealEstateCommissionOwnerCarry)

        buf = StringBuffer()
        buf.append("(J50*K37)")
        val forRealEstateCommissionOC = Formula(8, 36, buf.toString(), wcfDollar)
        excelSheet.addCell(forRealEstateCommissionOC)

        val lblCommissionPercentageOwnerCarry: Label
        lblCommissionPercentageOwnerCarry = Label(9, 36, "Commission %", timesBold)
        excelSheet.addCell(lblCommissionPercentageOwnerCarry)

        val nbrCommissionPercentageOC = Number(10, 36, cemC!!.getRealEstComm() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrCommissionPercentageOC)

        val lblBuyerClosingCostOwnerCarry: Label
        lblBuyerClosingCostOwnerCarry = Label(7, 38, "Buyer Closing Costs", timesBold)
        excelSheet.addCell(lblBuyerClosingCostOwnerCarry)

        buf = StringBuffer()
        buf.append("(I13*K39)")
        val forBuyerClosingCostOC = Formula(8, 38, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerClosingCostOC)

        val lblBuyerClosingCostPercentageOwnerCarry: Label
        lblBuyerClosingCostPercentageOwnerCarry = Label(9, 38, "Closing Costs %", timesBold)
        excelSheet.addCell(lblBuyerClosingCostPercentageOwnerCarry)

        val nbrBuyerClosingCostPercentageOC = Number(10, 38, cemC!!.getBuyClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrBuyerClosingCostPercentageOC)

        val lblSellerClosingCostOwnerCarry: Label
        lblSellerClosingCostOwnerCarry = Label(7, 39, "Seller Closing Costs", timesBold)
        excelSheet.addCell(lblSellerClosingCostOwnerCarry)

        buf = StringBuffer()
        buf.append("(I9*K40)")
        val forSellerClosingCostOC = Formula(8, 39, buf.toString(), wcfDollar)
        excelSheet.addCell(forSellerClosingCostOC)

        val lblSellerClosingCostPercentageOwnerCarry: Label
        lblSellerClosingCostPercentageOwnerCarry = Label(9, 39, "Closing Costs %", timesBold)
        excelSheet.addCell(lblSellerClosingCostPercentageOwnerCarry)

        val nbrSellerClosingCostPercentageOC = Number(10, 39, cemC!!.getSellClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrSellerClosingCostPercentageOC)

        val lblSixMonthClosExpOwnerCarry: Label
        lblSixMonthClosExpOwnerCarry = Label(9, 41, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthClosExpOwnerCarry)

        val lblNineMonthClosExpOwnerCarry: Label
        lblNineMonthClosExpOwnerCarry = Label(10, 41, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthClosExpOwnerCarry)

        val lblTwelveMonthClosExpOwnerCarry: Label
        lblTwelveMonthClosExpOwnerCarry = Label(11, 41, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthClosExpOwnerCarry)

        val lblTotalCostsOwnerCarry: Label
        lblTotalCostsOwnerCarry = Label(7, 42, "Total Costs", timesBold)
        excelSheet.addCell(lblTotalCostsOwnerCarry)

        buf = StringBuffer()
        buf.append("(I13+I15+J33+I37+I39)")
        val forTotalCostsSixMonthOC = Formula(9, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsSixMonthOC)

        buf = StringBuffer()
        buf.append("(I13+I15+K33+I37+I39)")
        val forTotalCostsNineMonthOC = Formula(10, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsNineMonthOC)

        buf = StringBuffer()
        buf.append("(I13+I15+L33+I37+I39)")
        val forTotalCostsAnnualOC = Formula(11, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsAnnualOC)

        val lblOutOfPocketOwnerCarry: Label
        lblOutOfPocketOwnerCarry = Label(7, 43, "Out of Pocket Expenses", timesBold)
        excelSheet.addCell(lblOutOfPocketOwnerCarry)

        buf = StringBuffer()
        buf.append("(I15+I39+J33+K9)")
        val forOutOfPocketSixMonthOC = Formula(9, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketSixMonthOC)

        buf = StringBuffer()
        buf.append("(I15+I39+K33+K9)")
        val forOutOfPocketNineMonthOC = Formula(10, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketNineMonthOC)

        buf = StringBuffer()
        buf.append("(I15+I39+L33+K9)")
        val forOutOfPocketAnnualOC = Formula(11, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketAnnualOC)

        // property market info - owner carry
        val lblSixMonthPropMktOwnerCarry: Label
        lblSixMonthPropMktOwnerCarry = Label(9, 46, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthPropMktOwnerCarry)

        val lblNineMonthPropMktOwnerCarry: Label
        lblNineMonthPropMktOwnerCarry = Label(10, 46, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthPropMktOwnerCarry)

        val lblTwelveMonthPropMktOwnerCarry: Label
        lblTwelveMonthPropMktOwnerCarry = Label(11, 46, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthPropMktOwnerCarry)

        val lblFMVARVOwnerCarry: Label
        lblFMVARVOwnerCarry = Label(7, 47, "FMV/ARV", timesBold)
        excelSheet.addCell(lblFMVARVOwnerCarry)

        val nbrFMVARVSixMonthOC = Number(9, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVSixMonthOC)

        val nbrFMVARVNineMonthOC = Number(10, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVNineMonthOC)

        val nbrFMVARVAnnualOC = Number(11, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVAnnualOC)

        val lblComparablesOwnerCarry: Label
        lblComparablesOwnerCarry = Label(7, 48, "Comparables", timesBold)
        excelSheet.addCell(lblComparablesOwnerCarry)

        val nbrComparablesSixMonthOC = Number(9, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesSixMonthOC)

        val nbrComparablesNineMonthOC = Number(10, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesNineMonthOC)

        val nbrComparablesAnnualOC = Number(11, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesAnnualOC)

        val lblSellingPriceOwnerCarry: Label
        lblSellingPriceOwnerCarry = Label(7, 49, "Selling Price", timesBold)
        excelSheet.addCell(lblSellingPriceOwnerCarry)

        val nbrSellingPriceSixMonthOC = Number(9, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceSixMonthOC)

        val nbrSellingPriceNineMonthOC = Number(10, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceNineMonthOC)

        val nbrSellingPriceAnnualOC = Number(11, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceAnnualOC)

        val lblBuyerCostsOwnerCarry: Label
        lblBuyerCostsOwnerCarry = Label(7, 51, "Buyer's Costs", timesBold)
        excelSheet.addCell(lblBuyerCostsOwnerCarry)

        buf = StringBuffer()
        buf.append("(J43)")
        val forBuyerCostsSixMonthOC = Formula(9, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsSixMonthOC)

        buf = StringBuffer()
        buf.append("(K43)")
        val forBuyerCostsNineMonthOC = Formula(10, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsNineMonthOC)

        buf = StringBuffer()
        buf.append("(L43)")
        val forBuyerCostsAnnualOC = Formula(11, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsAnnualOC)

        val lblGrossProfitOwnerCarry: Label
        lblGrossProfitOwnerCarry = Label(7, 52, "Gross Profit", timesBold)
        excelSheet.addCell(lblGrossProfitOwnerCarry)

        buf = StringBuffer()
        buf.append("(J50-J52)")
        val forGrossProfitSixMonthOC = Formula(9, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitSixMonthOC)

        buf = StringBuffer()
        buf.append("(K50-K52)")
        val forGrossProfitNineMonthOC = Formula(10, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitNineMonthOC)

        buf = StringBuffer()
        buf.append("(L50-L52)")
        val forGrossProfitAnnualOC = Formula(11, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitAnnualOC)

        val lblCapitalGainsOwnerCarry: Label
        lblCapitalGainsOwnerCarry = Label(7, 53, "Capital Gains", timesBold)
        excelSheet.addCell(lblCapitalGainsOwnerCarry)

        buf = StringBuffer()
        buf.append("(J53*0.3)")
        val forCapitalGainsSixMonthOC = Formula(9, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsSixMonthOC)

        buf = StringBuffer()
        buf.append("(K53*0.3)")
        val forCapitalGainsNineMonthOC = Formula(10, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsNineMonthOC)

        buf = StringBuffer()
        buf.append("(L53*0.3)")
        val forCapitalGainsAnnualOC = Formula(11, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsAnnualOC)

        val lblNetProfitOwnerCarry: Label
        lblNetProfitOwnerCarry = Label(7, 54, "Net Profit", timesBold)
        excelSheet.addCell(lblNetProfitOwnerCarry)

        buf = StringBuffer()
        buf.append("(J53-J54)")
        val forNetProfitSixMonthOC = Formula(9, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitSixMonthOC)

        buf = StringBuffer()
        buf.append("(K53-K54)")
        val forNetProfitNineMonthOC = Formula(10, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitNineMonthOC)

        buf = StringBuffer()
        buf.append("(L53-L54)")
        val forNetProfitAnnualOC = Formula(11, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitAnnualOC)

        val lblRateOfReturnOwnerCarry: Label
        lblRateOfReturnOwnerCarry = Label(7, 56, "Rate of Return", timesBold)
        excelSheet.addCell(lblRateOfReturnOwnerCarry)

        buf = StringBuffer()
        buf.append("(J55/J50)")
        val forRateOfReturnSixMonthOC = Formula(9, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnSixMonthOC)

        buf = StringBuffer()
        buf.append("(K55/K50)")
        val forRateOfReturnNineMonthOC = Formula(10, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnNineMonthOC)

        buf = StringBuffer()
        buf.append("(L55/L50)")
        val forRateOfReturnAnnualOC = Formula(11, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnAnnualOC)

        // rate of return info - owner carry
        val lblSixMonthRateReturnOwnerCarry: Label
        lblSixMonthRateReturnOwnerCarry = Label(9, 59, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthRateReturnOwnerCarry)

        val lblNineMonthRateReturnOwnerCarry: Label
        lblNineMonthRateReturnOwnerCarry = Label(10, 59, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthRateReturnOwnerCarry)

        val lblTwelveMonthRateReturnOwnerCarry: Label
        lblTwelveMonthRateReturnOwnerCarry = Label(11, 59, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthRateReturnOwnerCarry)

        val lblMoneyOutOwnerCarry: Label
        lblMoneyOutOwnerCarry = Label(7, 60, "Money Out", timesBold)
        excelSheet.addCell(lblMoneyOutOwnerCarry)

        buf = StringBuffer()
        buf.append("(J43)")
        val forMoneyOutSixMonthOC = Formula(9, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutSixMonthOC)

        buf = StringBuffer()
        buf.append("(K43)")
        val forMoneyOutNineMonthOC = Formula(10, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutNineMonthOC)

        buf = StringBuffer()
        buf.append("(L43)")
        val forMoneyOutAnnualOC = Formula(11, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutAnnualOC)

        val lblMoneyInOwnerCarry: Label
        lblMoneyInOwnerCarry = Label(7, 61, "Money In", timesBold)
        excelSheet.addCell(lblMoneyInOwnerCarry)

        buf = StringBuffer()
        buf.append("(J55)")
        val forMoneyInSixMonthOC = Formula(9, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInSixMonthOC)

        buf = StringBuffer()
        buf.append("(K55)")
        val forMoneyInNineMonthOC = Formula(10, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInNineMonthOC)

        buf = StringBuffer()
        buf.append("(L55)")
        val forMoneyInAnnualOC = Formula(11, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInAnnualOC)

        val lblCashCashReturnOwnerCarry: Label
        lblCashCashReturnOwnerCarry = Label(7, 62, "Cash on Cash Return", timesBold)
        excelSheet.addCell(lblCashCashReturnOwnerCarry)

        buf = StringBuffer()
        buf.append("(J62/J61)")
        val forCashCashReturnSixMonthOC = Formula(9, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnSixMonthOC)

        buf = StringBuffer()
        buf.append("(K62/K61)")
        val forCashCashReturnNineMonthOC = Formula(10, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnNineMonthOC)

        buf = StringBuffer()
        buf.append("(L62/L61)")
        val forCashCashReturnAnnualOC = Formula(11, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnAnnualOC)

        // location info - finance rehab
        val lblPropAddressFinRehab: Label
        lblPropAddressFinRehab = Label(13, 1, "Property Address:", timesBold)
        excelSheet.addCell(lblPropAddressFinRehab)
        excelSheet.mergeCells(14, 1, 17, 1)
        val lblStreetAddressFR = Label(14, 1, locL!!.getAddress(), timesBold)
        excelSheet.addCell(lblStreetAddressFR)

        val lblCityFinRehab: Label
        lblCityFinRehab = Label(13, 2, "City:", timesBold)
        excelSheet.addCell(lblCityFinRehab)
        val lblCityFR = Label(14, 2, locL!!.getCity(), timesBold)
        excelSheet.addCell(lblCityFR)

        val lblStFinRehab: Label
        lblStFinRehab = Label(13, 3, "State:", timesBold)
        excelSheet.addCell(lblStFinRehab)
        val lblStFR = Label(14, 3, locL!!.getState(), timesBold)
        excelSheet.addCell(lblStFR)

        val lblZipFinRehab: Label
        lblZipFinRehab = Label(13, 4, "ZIP:", timesBold)
        excelSheet.addCell(lblZipFinRehab)
        val lblZipFR = Label(14, 4, locL!!.getZIPCode(), timesBold)
        excelSheet.addCell(lblZipFR)

        val lblSquareFootageFinRehab: Label
        lblSquareFootageFinRehab = Label(15, 2, "Square Footage:", timesBold)
        excelSheet.addCell(lblSquareFootageFinRehab)
        excelSheet.mergeCells(16, 2, 17, 2)
        val lblSquareFootageFR = Label(16, 2, locL!!.getSquareFootage() + "", timesBold)
        excelSheet.addCell(lblSquareFootageFR)

        val lblBRFinRehab: Label
        lblBRFinRehab = Label(15, 3, "BR:", timesBold)
        excelSheet.addCell(lblBRFinRehab)
        excelSheet.mergeCells(16, 3, 17, 3)
        val lblBRFR = Label(16, 3, locL!!.getBedrooms() + "", timesBold)
        excelSheet.addCell(lblBRFR)

        val lblBAFinRehab: Label
        lblBAFinRehab = Label(15, 4, "BA:", timesBold)
        excelSheet.addCell(lblBAFinRehab)
        excelSheet.mergeCells(16, 4, 17, 4)
        val lblBAFR = Label(16, 4, locL!!.getBathrooms() + "", timesBold)
        excelSheet.addCell(lblBAFR)

        // sales info - finance rehab
        val lblSalesPriceFinRehab: Label
        lblSalesPriceFinRehab = Label(13, 8, "Sales Price:", timesBold)
        excelSheet.addCell(lblSalesPriceFinRehab)
        val nbrSalesPriceFR = Number(14, 8, smSM!!.getSalesPrice(), wcfDollar)
        excelSheet.addCell(nbrSalesPriceFR)

        val lblPercentDownFinRehab: Label
        lblPercentDownFinRehab = Label(13, 10, "Percent Down:", timesBold)
        excelSheet.addCell(lblPercentDownFinRehab)
        val nbrPercentDownFR = Number(14, 10, smSM!!.getPercentDown() / 100, wcfPercent)
        excelSheet.addCell(nbrPercentDownFR)

        val lblOfferBidFinRehab: Label
        lblOfferBidFinRehab = Label(13, 12, "Offer/Bid Price:", timesBold)
        excelSheet.addCell(lblOfferBidFinRehab)
        val nbrOfferBidFR = Number(14, 12, smSM!!.getOfferBid(), wcfDollar)
        excelSheet.addCell(nbrOfferBidFR)

        val lblRehabFinRehab: Label
        lblRehabFinRehab = Label(13, 14, "Rehab Budget:", timesBold)
        excelSheet.addCell(lblRehabFinRehab)
        val nbrRehabFR = Number(14, 14, rR!!.getBudget(), wcfDollar)
        excelSheet.addCell(nbrRehabFR)

        // mortgage info - finance rehab
        val lblDownPaymentFinRehab: Label
        lblDownPaymentFinRehab = Label(15, 8, "Down Payment:", timesBold)
        excelSheet.addCell(lblDownPaymentFinRehab)
        buf = StringBuffer()
        buf.append("SUM(O11*O9)")
        val forDownPaymentFR = Formula(16, 8, buf.toString(), wcfDollar)
        excelSheet.addCell(forDownPaymentFR)

        val lblLoanAmountFinRehab: Label
        lblLoanAmountFinRehab = Label(15, 10, "Loan Amount:", timesBold)
        excelSheet.addCell(lblLoanAmountFinRehab)
        buf = StringBuffer()
        buf.append("(O13+O15-Q9)")
        val forLoanAmountFR = Formula(16, 10, buf.toString(), wcfDollar)
        excelSheet.addCell(forLoanAmountFR)

        val lblInterestRateFinRehab: Label
        lblInterestRateFinRehab = Label(15, 12, "Interest Rate:", timesBold)
        excelSheet.addCell(lblInterestRateFinRehab)
        val nbrInterestRateFR = Number(16, 12, smSM!!.getInterestRate() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrInterestRateFR)

        val lblTermFinRehab: Label
        lblTermFinRehab = Label(15, 14, "Term:", timesBold)
        excelSheet.addCell(lblTermFinRehab)
        val nbrTermFR = Number(16, 14, smSM!!.getTerm())
        excelSheet.addCell(nbrTermFR)

        val lblMonthlyPmtFinRehab: Label
        lblMonthlyPmtFinRehab = Label(15, 16, "Monthly Pmt:", timesBold)
        excelSheet.addCell(lblMonthlyPmtFinRehab)
        buf = StringBuffer()
        buf.append("SUM(Q13*Q11/12)")
        val forMonthlyPmtFR = Formula(16, 16, buf.toString(), wcfDollar)
        excelSheet.addCell(forMonthlyPmtFR)

        // reserves info - finance rehab
        val lblMonthlyReservesFinRehab: Label
        lblMonthlyReservesFinRehab = Label(14, 19, "Monthly", timesBold)
        excelSheet.addCell(lblMonthlyReservesFinRehab)

        val lblSixMonthReservesFinRehab: Label
        lblSixMonthReservesFinRehab = Label(15, 19, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthReservesFinRehab)

        val lblNineMonthReservesFinRehab: Label
        lblNineMonthReservesFinRehab = Label(16, 19, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthReservesFinRehab)

        val lblTwelveMonthReservesFinRehab: Label
        lblTwelveMonthReservesFinRehab = Label(17, 19, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthReservesFinRehab)

        val lblMortgageReservesFinRehab: Label
        lblMortgageReservesFinRehab = Label(13, 20, "Mortgage", timesBold)
        excelSheet.addCell(lblMortgageReservesFinRehab)

        buf = StringBuffer()
        buf.append("(Q17)")
        val forMortgageMonthFR = Formula(14, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageMonthFR)

        buf = StringBuffer()
        buf.append("SUM(Q17*6)")
        val forMortgageSixMonthFR = Formula(15, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageSixMonthFR)

        buf = StringBuffer()
        buf.append("SUM(Q17*9)")
        val forMortgageNineMonthFR = Formula(16, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(Q13*Q11)")
        val forMortgageAnnualFR = Formula(17, 20, buf.toString(), wcfDollar)
        excelSheet.addCell(forMortgageAnnualFR)

        val lblTaxesReservesFinRehab: Label
        lblTaxesReservesFinRehab = Label(13, 22, "Taxes", timesBold)
        excelSheet.addCell(lblTaxesReservesFinRehab)

        buf = StringBuffer()
        buf.append("(P23/6)")
        val forTaxesMonthFR = Formula(14, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesMonthFR)

        val nbrTaxesFR = Number(15, 22, rsR!!.getTaxes(), wcfDollar)
        excelSheet.addCell(nbrTaxesFR)

        buf = StringBuffer()
        buf.append("SUM(P23*9/6)")
        val forTaxesNineMonthFR = Formula(16, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P23*2)")
        val forTaxesAnnualFR = Formula(17, 22, buf.toString(), wcfDollar)
        excelSheet.addCell(forTaxesAnnualFR)

        val lblInsuranceReservesFinRehab: Label
        lblInsuranceReservesFinRehab = Label(13, 24, "Insurance", timesBold)
        excelSheet.addCell(lblInsuranceReservesFinRehab)

        buf = StringBuffer()
        buf.append("(P25/6)")
        val forInsuranceMonthFR = Formula(14, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceMonthFR)

        val nbrInsuranceFR = Number(15, 24, rsR!!.getInsurance(), wcfDollar)
        excelSheet.addCell(nbrInsuranceFR)

        buf = StringBuffer()
        buf.append("SUM(P25*9/6)")
        val forInsuranceNineMonthFR = Formula(16, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P25*2)")
        val forInsuranceAnnualFR = Formula(17, 24, buf.toString(), wcfDollar)
        excelSheet.addCell(forInsuranceAnnualFR)

        val lblWaterReservesFinRehab: Label
        lblWaterReservesFinRehab = Label(13, 26, "Water", timesBold)
        excelSheet.addCell(lblWaterReservesFinRehab)

        buf = StringBuffer()
        buf.append("(P27/6)")
        val forWaterMonthFR = Formula(14, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterMonthFR)

        val nbrWaterFR = Number(15, 26, rsR!!.getWater(), wcfDollar)
        excelSheet.addCell(nbrWaterFR)

        buf = StringBuffer()
        buf.append("SUM(P27*9/6)")
        val forWaterNineMonthFR = Formula(16, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P27*2)")
        val forWaterAnnualFR = Formula(17, 26, buf.toString(), wcfDollar)
        excelSheet.addCell(forWaterAnnualFR)

        val lblGasReservesFinRehab: Label
        lblGasReservesFinRehab = Label(13, 28, "Gas", timesBold)
        excelSheet.addCell(lblGasReservesFinRehab)

        buf = StringBuffer()
        buf.append("(P29/6)")
        val forGasMonthFR = Formula(14, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasMonthFR)

        val nbrGasFR = Number(15, 28, rsR!!.getGas(), wcfDollar)
        excelSheet.addCell(nbrGasFR)

        buf = StringBuffer()
        buf.append("SUM(P29*9/6)")
        val forGasNineMonthFR = Formula(16, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P29*2)")
        val forGasAnnualFR = Formula(17, 28, buf.toString(), wcfDollar)
        excelSheet.addCell(forGasAnnualFR)

        val lblElectricReservesFinRehab: Label
        lblElectricReservesFinRehab = Label(13, 30, "Electric", timesBold)
        excelSheet.addCell(lblElectricReservesFinRehab)

        buf = StringBuffer()
        buf.append("(P31/6)")
        val forElectricMonthFR = Formula(14, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricMonthFR)

        val nbrElectricFR = Number(15, 30, rsR!!.getElectric(), wcfDollar)
        excelSheet.addCell(nbrElectricFR)

        buf = StringBuffer()
        buf.append("SUM(P31*9/6)")
        val forElectricNineMonthFR = Formula(16, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P31*2)")
        val forElectricAnnualFR = Formula(17, 30, buf.toString(), wcfDollar)
        excelSheet.addCell(forElectricAnnualFR)

        val lblTotalReservesFinRehab: Label
        lblTotalReservesFinRehab = Label(13, 32, "Total Expenses", timesBold)
        excelSheet.addCell(lblTotalReservesFinRehab)

        buf = StringBuffer()
        buf.append("SUM(O21:O31)")
        val forTotalMonthFR = Formula(14, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalMonthFR)

        buf = StringBuffer()
        buf.append("SUM(P21:P31)")
        val forTotalSixMonthFR = Formula(15, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalSixMonthFR)

        buf = StringBuffer()
        buf.append("SUM(Q21:Q31)")
        val forTotalNineMonthFR = Formula(16, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalNineMonthFR)

        buf = StringBuffer()
        buf.append("SUM(R21:R31)")
        val forTotalAnnualFR = Formula(17, 32, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalAnnualFR)

        // closing expenses - finance rehab
        val lblRealEstateCommissionFinRehab: Label
        lblRealEstateCommissionFinRehab = Label(13, 36, "Real Estate Commission", timesBold)
        excelSheet.addCell(lblRealEstateCommissionFinRehab)

        buf = StringBuffer()
        buf.append("(P50*Q37)")
        val forRealEstateCommissionFR = Formula(14, 36, buf.toString(), wcfDollar)
        excelSheet.addCell(forRealEstateCommissionFR)

        val lblCommissionPercentageFinRehab: Label
        lblCommissionPercentageFinRehab = Label(15, 36, "Commission %", timesBold)
        excelSheet.addCell(lblCommissionPercentageFinRehab)

        val nbrCommissionPercentageFR = Number(16, 36, cemC!!.getRealEstComm() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrCommissionPercentageFR)

        val lblBuyerClosingCostFinRehab: Label
        lblBuyerClosingCostFinRehab = Label(13, 38, "Buyer Closing Costs", timesBold)
        excelSheet.addCell(lblBuyerClosingCostFinRehab)

        buf = StringBuffer()
        buf.append("(O13*Q39)")
        val forBuyerClosingCostFR = Formula(14, 38, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerClosingCostFR)

        val lblBuyerClosingCostPercentageFinRehab: Label
        lblBuyerClosingCostPercentageFinRehab = Label(15, 38, "Closing Costs %", timesBold)
        excelSheet.addCell(lblBuyerClosingCostPercentageFinRehab)

        val nbrBuyerClosingCostPercentageFR = Number(16, 38, cemC!!.getBuyClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrBuyerClosingCostPercentageFR)

        val lblSellerClosingCostFinRehab: Label
        lblSellerClosingCostFinRehab = Label(13, 39, "Seller Closing Costs", timesBold)
        excelSheet.addCell(lblSellerClosingCostFinRehab)

        buf = StringBuffer()
        buf.append("(O9*Q40)")
        val forSellerClosingCostFR = Formula(14, 39, buf.toString(), wcfDollar)
        excelSheet.addCell(forSellerClosingCostFR)

        val lblSellerClosingCostPercentageFinRehab: Label
        lblSellerClosingCostPercentageFinRehab = Label(15, 39, "Closing Costs %", timesBold)
        excelSheet.addCell(lblSellerClosingCostPercentageFinRehab)

        val nbrSellerClosingCostPercentageFR = Number(16, 39, cemC!!.getSellClosCost() / 100, wcfPercentTwoPlaces)
        excelSheet.addCell(nbrSellerClosingCostPercentageFR)

        val lblSixMonthClosExpFinRehab: Label
        lblSixMonthClosExpFinRehab = Label(15, 41, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthClosExpFinRehab)

        val lblNineMonthClosExpFinRehab: Label
        lblNineMonthClosExpFinRehab = Label(16, 41, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthClosExpFinRehab)

        val lblTwelveMonthClosExpFinRehab: Label
        lblTwelveMonthClosExpFinRehab = Label(17, 41, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthClosExpFinRehab)

        val lblTotalCostsFinRehab: Label
        lblTotalCostsFinRehab = Label(13, 42, "Total Costs", timesBold)
        excelSheet.addCell(lblTotalCostsFinRehab)

        buf = StringBuffer()
        buf.append("(O13+O15+P33+O37+O39)")
        val forTotalCostsSixMonthFR = Formula(15, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsSixMonthFR)

        buf = StringBuffer()
        buf.append("(O13+O15+Q33+O37+O39)")
        val forTotalCostsNineMonthFR = Formula(16, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsNineMonthFR)

        buf = StringBuffer()
        buf.append("(O13+O15+R33+O37+O39)")
        val forTotalCostsAnnualFR = Formula(17, 42, buf.toString(), wcfDollar)
        excelSheet.addCell(forTotalCostsAnnualFR)

        val lblOutOfPocketFinRehab: Label
        lblOutOfPocketFinRehab = Label(13, 43, "Out of Pocket Expenses", timesBold)
        excelSheet.addCell(lblOutOfPocketFinRehab)

        buf = StringBuffer()
        buf.append("(O39+P33+Q9)")
        val forOutOfPocketSixMonthFR = Formula(15, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketSixMonthFR)

        buf = StringBuffer()
        buf.append("(O39+Q33+Q9)")
        val forOutOfPocketNineMonthFR = Formula(16, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketNineMonthFR)

        buf = StringBuffer()
        buf.append("(O39+R33+Q9)")
        val forOutOfPocketAnnualFR = Formula(17, 43, buf.toString(), wcfDollar)
        excelSheet.addCell(forOutOfPocketAnnualFR)

        // property market info - fin rehab
        val lblSixMonthPropMktFinRehab: Label
        lblSixMonthPropMktFinRehab = Label(15, 46, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthPropMktFinRehab)

        val lblNineMonthPropMktFinRehab: Label
        lblNineMonthPropMktFinRehab = Label(16, 46, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthPropMktFinRehab)

        val lblTwelveMonthPropMktFinRehab: Label
        lblTwelveMonthPropMktFinRehab = Label(17, 46, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthPropMktFinRehab)

        val lblFMVARVFinRehab: Label
        lblFMVARVFinRehab = Label(13, 47, "FMV/ARV", timesBold)
        excelSheet.addCell(lblFMVARVFinRehab)

        val nbrFMVARVSixMonthFR = Number(15, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVSixMonthFR)

        val nbrFMVARVNineMonthFR = Number(16, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVNineMonthFR)

        val nbrFMVARVAnnualFR = Number(17, 47, cemC!!.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVAnnualFR)

        val lblComparablesFinRehab: Label
        lblComparablesFinRehab = Label(13, 48, "Comparables", timesBold)
        excelSheet.addCell(lblComparablesFinRehab)

        val nbrComparablesSixMonthFR = Number(15, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesSixMonthFR)

        val nbrComparablesNineMonthFR = Number(16, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesNineMonthFR)

        val nbrComparablesAnnualFR = Number(17, 48, cemC!!.getComparables(), wcfDollar)
        excelSheet.addCell(nbrComparablesAnnualFR)

        val lblSellingPriceFinRehab: Label
        lblSellingPriceFinRehab = Label(13, 49, "Selling Price", timesBold)
        excelSheet.addCell(lblSellingPriceFinRehab)

        val nbrSellingPriceSixMonthFR = Number(15, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceSixMonthFR)

        val nbrSellingPriceNineMonthFR = Number(16, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceNineMonthFR)

        val nbrSellingPriceAnnualFR = Number(17, 49, cemC!!.getSellingPrice(), wcfDollar)
        excelSheet.addCell(nbrSellingPriceAnnualFR)

        val lblBuyerCostsFinRehab: Label
        lblBuyerCostsFinRehab = Label(13, 51, "Buyer's Costs", timesBold)
        excelSheet.addCell(lblBuyerCostsFinRehab)

        buf = StringBuffer()
        buf.append("(P43)")
        val forBuyerCostsSixMonthFR = Formula(15, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q43)")
        val forBuyerCostsNineMonthFR = Formula(16, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsNineMonthFR)

        buf = StringBuffer()
        buf.append("(R43)")
        val forBuyerCostsAnnualFR = Formula(17, 51, buf.toString(), wcfDollar)
        excelSheet.addCell(forBuyerCostsAnnualFR)

        val lblGrossProfitFinRehab: Label
        lblGrossProfitFinRehab = Label(13, 52, "Gross Profit", timesBold)
        excelSheet.addCell(lblGrossProfitFinRehab)

        buf = StringBuffer()
        buf.append("(P50-P52)")
        val forGrossProfitSixMonthFR = Formula(15, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q50-Q52)")
        val forGrossProfitNineMonthFR = Formula(16, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitNineMonthFR)

        buf = StringBuffer()
        buf.append("(R50-R52)")
        val forGrossProfitAnnualFR = Formula(17, 52, buf.toString(), wcfDollar)
        excelSheet.addCell(forGrossProfitAnnualFR)

        val lblCapitalGainsFinRehab: Label
        lblCapitalGainsFinRehab = Label(13, 53, "Capital Gains", timesBold)
        excelSheet.addCell(lblCapitalGainsFinRehab)

        buf = StringBuffer()
        buf.append("(P53*0.3)")
        val forCapitalGainsSixMonthFR = Formula(15, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q53*0.3)")
        val forCapitalGainsNineMonthFR = Formula(16, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsNineMonthFR)

        buf = StringBuffer()
        buf.append("(R53*0.3)")
        val forCapitalGainsAnnualFR = Formula(17, 53, buf.toString(), wcfDollar)
        excelSheet.addCell(forCapitalGainsAnnualFR)

        val lblNetProfitFinRehab: Label
        lblNetProfitFinRehab = Label(13, 54, "Net Profit", timesBold)
        excelSheet.addCell(lblNetProfitFinRehab)

        buf = StringBuffer()
        buf.append("(P53-P54)")
        val forNetProfitSixMonthFR = Formula(15, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q53-Q54)")
        val forNetProfitNineMonthFR = Formula(16, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitNineMonthFR)

        buf = StringBuffer()
        buf.append("(R53-R54)")
        val forNetProfitAnnualFR = Formula(17, 54, buf.toString(), wcfDollar)
        excelSheet.addCell(forNetProfitAnnualFR)

        val lblRateOfReturnFinRehab: Label
        lblRateOfReturnFinRehab = Label(13, 56, "Rate of Return", timesBold)
        excelSheet.addCell(lblRateOfReturnFinRehab)

        buf = StringBuffer()
        buf.append("(P55/P50)")
        val forRateOfReturnSixMonthFR = Formula(15, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q55/Q50)")
        val forRateOfReturnNineMonthFR = Formula(16, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnNineMonthFR)

        buf = StringBuffer()
        buf.append("(R55/R50)")
        val forRateOfReturnAnnualFR = Formula(17, 56, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnAnnualFR)

        // rate of return info - finance rehab
        val lblSixMonthRateReturnFinRehab: Label
        lblSixMonthRateReturnFinRehab = Label(15, 59, "6 Months", timesBold)
        excelSheet.addCell(lblSixMonthRateReturnFinRehab)

        val lblNineMonthRateReturnFinRehab: Label
        lblNineMonthRateReturnFinRehab = Label(16, 59, "9 Months", timesBold)
        excelSheet.addCell(lblNineMonthRateReturnFinRehab)

        val lblTwelveMonthRateReturnFinRehab: Label
        lblTwelveMonthRateReturnFinRehab = Label(17, 59, "12 Months", timesBold)
        excelSheet.addCell(lblTwelveMonthRateReturnFinRehab)

        val lblMoneyOutFinRehab: Label
        lblMoneyOutFinRehab = Label(13, 60, "Money Out", timesBold)
        excelSheet.addCell(lblMoneyOutFinRehab)

        buf = StringBuffer()
        buf.append("(P43)")
        val forMoneyOutSixMonthFR = Formula(15, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q43)")
        val forMoneyOutNineMonthFR = Formula(16, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutNineMonthFR)

        buf = StringBuffer()
        buf.append("(R43)")
        val forMoneyOutAnnualFR = Formula(17, 60, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyOutAnnualFR)

        val lblMoneyInFinRehab: Label
        lblMoneyInFinRehab = Label(13, 61, "Money In", timesBold)
        excelSheet.addCell(lblMoneyInFinRehab)

        buf = StringBuffer()
        buf.append("(P55)")
        val forMoneyInSixMonthFR = Formula(15, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q55)")
        val forMoneyInNineMonthFR = Formula(16, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInNineMonthFR)

        buf = StringBuffer()
        buf.append("(R55)")
        val forMoneyInAnnualFR = Formula(17, 61, buf.toString(), wcfDollar)
        excelSheet.addCell(forMoneyInAnnualFR)

        val lblCashCashReturnFinRehab: Label
        lblCashCashReturnFinRehab = Label(13, 62, "Cash on Cash Return", timesBold)
        excelSheet.addCell(lblCashCashReturnFinRehab)

        buf = StringBuffer()
        buf.append("(P62/P61)")
        val forCashCashReturnSixMonthFR = Formula(15, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnSixMonthFR)

        buf = StringBuffer()
        buf.append("(Q62/Q61)")
        val forCashCashReturnNineMonthFR = Formula(16, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnNineMonthFR)

        buf = StringBuffer()
        buf.append("(R62/R61)")
        val forCashCashReturnAnnualFR = Formula(17, 62, buf.toString(), wcfPercent)
        excelSheet.addCell(forCashCashReturnAnnualFR)
        //createContent(excelSheet);

        workbook.write()
        workbook.close()
    }
*/

/*
    @Throws(IOException::class, WriteException::class)
    fun emailExcelSpreadsheet() {
        val myDir = File(applicationContext.getExternalFilesDir(null)!!.toString() + "/FlipulatorPremium")
        myDir.mkdirs()
        val strFileNameXls = locL!!.getAddress() + " " + locL!!.getCity() + " " + locL!!.getState() + " " + locL!!.getZIPCode() + ".xls"
        val file = File(myDir, strFileNameXls)

        if (!file.exists()) {
            createSpreadsheet(myDir, strFileNameXls)
        }

        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + locL!!.getAddress() + " " + locL!!.getCity() + ", " + locL!!.getState() + " " + locL!!.getZIPCode())
        intEmailActivity.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///$file"))
        intEmailActivity.type = "application/excel"
        startActivity(intEmailActivity)

    }
*/

/*
    fun emailPlainText() {
        // email results of calculate to those parties concerned
        var strMessage = "Address:\t\t\t\t\t\t\t   " + locL!!.getAddress() + "\n"
        strMessage += "City/State/ZIP Code:\t\t " + locL!!.getCity() + ", " + locL!!.getState() + " " + locL!!.getZIPCode() + "\n"
        strMessage += "Square Footage:\t\t\t\t  " + locL!!.getSquareFootage() + "\n"
        strMessage += "Bedrooms/Bathrooms:\t\t  " + locL!!.getBedrooms() + " BR " + locL!!.getBathrooms() + " BA\n"
        strMessage += "Sale Price:\t\t\t\t\t\t $" + String.format("%.0f", smSM!!.getSalesPrice()) + "\n"
        strMessage += "Percent Down %:\t\t\t\t  " + String.format("%.0f", smSM!!.getPercentDown()) + "%\n"
        strMessage += "Offer/Bid Price:\t\t\t\t$" + String.format("%.0f", smSM!!.getOfferBid()) + "\n"
        strMessage += "Rehab Budget:\t\t\t\t\t $" + String.format("%.0f", rR!!.getBudget()) + "\n"
        strMessage += "Budget Items:\t\t\t\t\t " + rR!!.getBudgetItems() + "\n"
        strMessage += "Down Payment:\t\t\t\t   $" + String.format("%.0f", smSM!!.getDownPayment()) + "\n"
        strMessage += "Loan Amount:\t\t\t\t\t  $" + String.format("%.0f", smSM!!.getLoanAmount()) + "\n"
        strMessage += "Interest Rate %:\t\t\t\t " + String.format("%.0f", smSM!!.getInterestRate()) + "%\n"
        strMessage += "Term (months):\t\t\t\t   " + smSM!!.getTerm() + "\n"
        strMessage += "Monthly Pmt:\t\t\t\t\t  $" + String.format("%.0f", smSM!!.getMonthlyPmt()) + "\n"
        strMessage += "Mortgage:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getMortgage()) + "\n"
        strMessage += "Property Taxes:\t\t\t\t $" + String.format("%.0f", rsR!!.getTaxes()) + "\n"
        strMessage += "Insurance:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getInsurance()) + "\n"
        strMessage += "Electric:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getElectric()) + "\n"
        strMessage += "Water:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getWater()) + "\n"
        strMessage += "Gas:\t\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR!!.getGas()) + "\n"
        strMessage += "Total Reserves:\t\t\t\t $" + String.format("%.0f", rsR!!.getTotalExpenses()) + "\n"
        strMessage += "Real Estate Comm:\t\t\t $" + String.format("%.0f", frF!!.getRECost()) + "\n"
        strMessage += "Commission %:\t\t\t\t " + String.format("%.0f", cemC!!.getRealEstComm()) + "%\n"
        strMessage += "Buyer Clos Cost:\t\t\t\t $" + String.format("%.0f", frF!!.getBCCost()) + "\n"
        strMessage += "Closing Cost %:\t\t\t\t " + String.format("%.0f", cemC!!.getBuyClosCost()) + "%\n"
        strMessage += "Sell Clos Cost:\t\t\t\t\t $" + String.format("%.0f", frF!!.getSCCost()) + "\n"
        strMessage += "Closing Cost %:\t\t\t\t " + String.format("%.0f", cemC!!.getSellClosCost()) + "%\n"
        strMessage += "Total Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getTotalCost()) + "\n"
        strMessage += "Out of Pocket Exp:\t\t\t $" + String.format("%.0f", frF!!.getOOPExp()) + "\n"
        strMessage += "FMV/ARV:\t\t\t\t\t\t\t $" + String.format("%.0f", cemC!!.getFMVARV()) + "\n"
        strMessage += "Comparables:\t\t\t\t\t $" + String.format("%.0f", cemC!!.getComparables()) + "\n"
        strMessage += "Selling Price:\t\t\t\t\t $" + String.format("%.0f", cemC!!.getSellingPrice()) + "\n"
        strMessage += "Buy + Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getTotalCost()) + "\n"
        strMessage += "Gross Profit:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getGrossProfit()) + "\n"
        strMessage += "Capital Gains:\t\t\t\t\t $" + String.format("%.0f", frF!!.getCapGains()) + "\n"
        strMessage += "Net Profit:\t\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getNetProfit()) + "\n"
        strMessage += "Money Out:\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getOOPExp()) + "\n"
        strMessage += "Money In:\t\t\t\t\t\t\t $" + String.format("%.0f", frF!!.getNetProfit()) + "\n"
        strMessage += "% Return:\t\t\t\t\t\t\t " + String.format("%.1f", frF!!.getROI()) + "%\n"
        strMessage += "Cash on Cash Return:\t " + String.format("%.1f", frF!!.getCashOnCash()) + "%\n"
        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + locL!!.getAddress() + " " + locL!!.getCity() + ", " + locL!!.getState() + " " + locL!!.getZIPCode())
        intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage)
        intEmailActivity.type = "plain/text"
        startActivity(intEmailActivity)
    }
*/

/*
    @Throws(FileNotFoundException::class, IOException::class, WriteException::class)
    fun saveFile(view: View) {
        // saves results to text file
        val myDir = File(applicationContext.getExternalFilesDir(null)!!.toString() + "/FlipulatorPremium")
        myDir.mkdirs()
        val strFileNameXls = calC.getAddress() + " " + locL!!.getCity() + " " + locL!!.getState() + " " + locL!!.getZIPCode() + ".xls"
        val file = File(myDir, strFileNameXls)

        // create Excel spreadsheet
        createSpreadsheet(myDir, strFileNameXls)

        val strSavedFile = "File saved as: $strFileNameXls"
        Toast.makeText(applicationContext, strSavedFile, Toast.LENGTH_SHORT).show()
    }
*/

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        var strBackMessage = "Press Previous to make changes, Save to save info to file, "
        strBackMessage += "Main Menu to return to main menu or Email Results to email."
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(applicationContext, strBackMessage, Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }

}
