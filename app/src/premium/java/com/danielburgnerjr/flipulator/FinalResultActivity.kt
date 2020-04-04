package com.danielburgnerjr.flipulator

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle

import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.content.Intent
import androidx.fragment.app.FragmentActivity

import com.danielburgnerjr.flipulator.model.Calculate
import com.danielburgnerjr.flipulator.util.ExcelSpreadsheet

import java.io.*

import jxl.write.WriteException

class FinalResultActivity : FragmentActivity() {

    private var spnTimeFrame: Spinner? = null
    private var calC: Calculate? = null
    private var xlsSpreadsheet: ExcelSpreadsheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_result_activity)

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.time_frame, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnTimeFrame = findViewById<View>(R.id.spnTimeFrame) as Spinner
        spnTimeFrame!!.adapter = aradAdapter

        var intI = intent

        xlsSpreadsheet = ExcelSpreadsheet()
        calC = intI.getSerializableExtra("Calculate") as Calculate

        if (calC!!.getGrossProfit() < 30000.0) {
            val adAlertBox = AlertDialog.Builder(this@FinalResultActivity)
            adAlertBox.setMessage("Your gross profit is below $30K! Would you like to make changes now?")
            adAlertBox.setPositiveButton("Yes") { _, _ ->
                // do something when the button is clicked
                intI = Intent(this@FinalResultActivity, LocationActivity::class.java)
                if (intI != null)
                    intI!!.putExtra("Calculate", calC)
                startActivity(intI)
                finish()
            }
            adAlertBox.setNegativeButton("No") { _, _ -> }
            adAlertBox.show()
        }
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
        adAlertBox.setMessage("Do you want to email results as text or as an Excel spreadsheet?")
        adAlertBox.setPositiveButton("Text") { _, _ ->
            // do something when the button is clicked
            emailPlainText()
        }
        adAlertBox.setNegativeButton("Excel") { _, _ ->
            // do something when the button is clicked
            try {
                emailExcelSpreadsheet()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            } catch (e: WriteException) {
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        adAlertBox.show()
    }

    @Throws(IOException::class, WriteException::class)
    fun emailExcelSpreadsheet() {
        val myDir = File(applicationContext.getExternalFilesDir(null)!!.toString() + "/FlipulatorPremium")
        myDir.mkdirs()
        val strFileNameXls = calC!!.getAddress() + " " + calC!!.getCity() + " " + calC!!.getState() + " " + calC!!.getZipCode() + ".xls"
        val file = File(myDir, strFileNameXls)

        if (!file.exists()) {
            xlsSpreadsheet!!.createSpreadsheet(myDir, calC!!, strFileNameXls)
        }

        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + calC!!.getAddress() + " " + calC!!.getCity() + ", " + calC!!.getState() + " " + calC!!.getZipCode())
        intEmailActivity.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///$file"))
        intEmailActivity.type = "application/excel"
        startActivity(intEmailActivity)

    }

    private fun emailPlainText() {
        // email results of calculate to those parties concerned
        var strMessage = "Address:\t\t\t\t\t\t\t   " + calC!!.getAddress() + "\n"
        strMessage += "City/State/ZIP Code:\t\t " + calC!!.getCity() + ", " + calC!!.getState() + " " + calC!!.getZipCode() + "\n"
        strMessage += "Square Footage:\t\t\t\t  " + calC!!.getSquareFootage() + "\n"
        strMessage += "Bedrooms/Bathrooms:\t\t  " + calC!!.getBedrooms() + " BR " + calC!!.getBathrooms() + " BA\n"
        strMessage += "Sale Price:\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getSalesPrice()) + "\n"
        strMessage += "Percent Down %:\t\t\t\t  " + String.format("%.0f", calC!!.getPercentDown()) + "%\n"
        strMessage += "Offer/Bid Price:\t\t\t\t$" + String.format("%.0f", calC!!.getOfferBid()) + "\n"
        strMessage += "Rehab Budget:\t\t\t\t\t $" + String.format("%.0f", calC!!.getBudget()) + "\n"
        strMessage += "Budget Items:\t\t\t\t\t " + calC!!.getBudgetItems() + "\n"
        strMessage += "Down Payment:\t\t\t\t   $" + String.format("%.0f", calC!!.getDownPayment()) + "\n"
        strMessage += "Loan Amount:\t\t\t\t\t  $" + String.format("%.0f", calC!!.getLoanAmount()) + "\n"
        strMessage += "Interest Rate %:\t\t\t\t " + String.format("%.0f", calC!!.getInterestRate()) + "%\n"
        strMessage += "Term (months):\t\t\t\t   " + calC!!.getTerm() + "\n"
        strMessage += "Monthly Pmt:\t\t\t\t\t  $" + String.format("%.0f", calC!!.getMonthlyPmt()) + "\n"
        strMessage += "Mortgage:\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getMortgage()) + "\n"
        strMessage += "Property Taxes:\t\t\t\t $" + String.format("%.0f", calC!!.getTaxes()) + "\n"
        strMessage += "Insurance:\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getInsurance()) + "\n"
        strMessage += "Electric:\t\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getElectric()) + "\n"
        strMessage += "Water:\t\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getWater()) + "\n"
        strMessage += "Gas:\t\t\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getGas()) + "\n"
        strMessage += "Total Reserves:\t\t\t\t $" + String.format("%.0f", calC!!.getTotalExpenses()) + "\n"
        strMessage += "Real Estate Comm:\t\t\t $" + String.format("%.0f", calC!!.getRECost()) + "\n"
        strMessage += "Commission %:\t\t\t\t " + String.format("%.0f", calC!!.getRealEstComm()) + "%\n"
        strMessage += "Buyer Clos Cost:\t\t\t\t $" + String.format("%.0f", calC!!.getBCCost()) + "\n"
        strMessage += "Closing Cost %:\t\t\t\t " + String.format("%.0f", calC!!.getBuyClosCost()) + "%\n"
        strMessage += "Sell Clos Cost:\t\t\t\t\t $" + String.format("%.0f", calC!!.getSCCost()) + "\n"
        strMessage += "Closing Cost %:\t\t\t\t " + String.format("%.0f", calC!!.getSellClosCost()) + "%\n"
        strMessage += "Total Costs:\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getTotalCost()) + "\n"
        strMessage += "Out of Pocket Exp:\t\t\t $" + String.format("%.0f", calC!!.getOOPExp()) + "\n"
        strMessage += "FMV/ARV:\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getFMVARV()) + "\n"
        strMessage += "Comparables:\t\t\t\t\t $" + String.format("%.0f", calC!!.getComparables()) + "\n"
        strMessage += "Selling Price:\t\t\t\t\t $" + String.format("%.0f", calC!!.getSellingPrice()) + "\n"
        strMessage += "Buy + Costs:\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getTotalCost()) + "\n"
        strMessage += "Gross Profit:\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getGrossProfit()) + "\n"
        strMessage += "Capital Gains:\t\t\t\t\t $" + String.format("%.0f", calC!!.getCapGains()) + "\n"
        strMessage += "Net Profit:\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getNetProfit()) + "\n"
        strMessage += "Money Out:\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getOOPExp()) + "\n"
        strMessage += "Money In:\t\t\t\t\t\t\t $" + String.format("%.0f", calC!!.getNetProfit()) + "\n"
        strMessage += "% Return:\t\t\t\t\t\t\t " + String.format("%.1f", calC!!.getROI()) + "%\n"
        strMessage += "Cash on Cash Return:\t " + String.format("%.1f", calC!!.getCashOnCash()) + "%\n"
        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + calC!!.getAddress() + " " + calC!!.getCity() + ", " + calC!!.getState() + " " + calC!!.getZipCode())
        intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage)
        intEmailActivity.type = "plain/text"
        startActivity(intEmailActivity)
    }

    @Throws(FileNotFoundException::class, IOException::class, WriteException::class)
    fun saveFile(view: View) {
        // saves results to text file
        val myDir = File(applicationContext.getExternalFilesDir(null)!!.toString() + "/FlipulatorPremium")
        myDir.mkdirs()
        val strFileNameXls = calC!!.getAddress() + " " + calC!!.getCity() + " " + calC!!.getState() + " " + calC!!.getZipCode() + ".xls"
        val file = File(myDir, strFileNameXls)
        file.createNewFile()
        // create Excel spreadsheet
        xlsSpreadsheet!!.createSpreadsheet(myDir, calC!!, strFileNameXls)

        val strSavedFile = "File saved as: $strFileNameXls"
        Toast.makeText(applicationContext, strSavedFile, Toast.LENGTH_SHORT).show()
    }

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
