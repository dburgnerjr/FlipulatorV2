package com.danielburgnerjr.flipulator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.KeyEvent
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate
import com.danielburgnerjr.flipulator.util.ExcelSpreadsheet

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import java.io.File
import java.io.IOException
import java.io.FileNotFoundException

import jxl.write.WriteException

class CalculateActivity : Activity() {

    private val cntC: Context = this
    private var calR: Calculate? = null                // Calculate object
    //private var intR: Intent? = null                   // Intent

    private var etAddress: EditText? = null            // address
    private var etCityStZip: EditText? = null        // city state zip code
    private var etSquareFootage: EditText? = null    // square footage
    private var etBedrooms: EditText? = null        // number of bedrooms
    private var etBathrooms: EditText? = null        // number of bathrooms
    private var etSalesPrice: EditText? = null        // sales price
    private var etFMVARV: EditText? = null            // fair mkt value/after repair value
    private var etBudgetItems: EditText? = null        // budget items
    private var tvRehabFlatRate: TextView? = null    // rehab budget flat rate textview
    private var etRehabBudget: EditText? = null        // rehab budget
    private var tvRehabType: TextView? = null        // rehab type textview
    private var spnRehabType: Spinner? = null        // rehab type
    private var btnHelp: Button? = null                // help
    private var tvClosHoldCosts: TextView? = null    // closing/holding costs textview
    private var etClosHoldCosts: EditText? = null    // closing/holding costs
    private var tvProfit: TextView? = null    // Profit textview
    private var etProfit: EditText? = null            // profit
    private var tvROI: TextView? = null    // ROI textview
    private var etROI: EditText? = null                // return on investment
    private var tvCashOnCash: TextView? = null    // cash on cash textview
    private var etCashOnCash: EditText? = null        // cash on cash return
    private var btnSave: Button? = null
    private var llEditInfo: LinearLayout? = null
    private var strRTSel: String? = null
    private var xlsSpreadsheet: ExcelSpreadsheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val mAdCalcView = findViewById<AdView>(R.id.adCalcView)
        val adRequest = AdRequest.Builder().build()
        mAdCalcView.loadAd(adRequest)

        calR = Calculate()
        xlsSpreadsheet = ExcelSpreadsheet()
        etAddress = findViewById(R.id.txtAddress)
        etCityStZip = findViewById(R.id.txtCityStZip)
        etSquareFootage = findViewById(R.id.txtSq_Footage)
        etBedrooms = findViewById(R.id.txtBedrooms)
        etBathrooms = findViewById(R.id.txtBathrooms)
        etSalesPrice = findViewById(R.id.txtSalePrice)
        etFMVARV = findViewById(R.id.txtFMVARV)
        etBudgetItems = findViewById(R.id.txtBudgetItems)
        btnSave = findViewById(R.id.btnSave)
        llEditInfo = findViewById(R.id.llEditInfo)

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.rehab_type_class, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tvRehabFlatRate = findViewById(R.id.tvRehabBudget)
        etRehabBudget = findViewById(R.id.txtRehabBudget)
        tvRehabType = findViewById(R.id.tvRehabType)
        spnRehabType = findViewById(R.id.spnRehabType)
        tvClosHoldCosts = findViewById(R.id.tvClosHoldCosts)
        etClosHoldCosts = findViewById(R.id.txtClosHoldCosts)
        tvProfit = findViewById(R.id.tvProfit)
        etProfit = findViewById(R.id.txtProfit)
        tvROI = findViewById(R.id.tvROI)
        etROI = findViewById(R.id.txtROI)
        tvCashOnCash = findViewById(R.id.tvCashOnCash)
        etCashOnCash = findViewById(R.id.txtCashOnCash)

        btnHelp = findViewById(R.id.txtHelp)
        spnRehabType!!.adapter = aradAdapter

        spnRehabType!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if (position >= 0) {
                    strRTSel = parentView.getItemAtPosition(position).toString()
                    if ("" != etSquareFootage!!.text.toString())
                        calR!!.setSquareFootage(etSquareFootage!!.text.toString().toInt())
                    calR!!.setRTSel(strRTSel!!)
                    when (strRTSel) {
                        "Low", "Medium", "High", "Super-High", "Bulldozer" -> calR!!.calcBudgetRehabType(strRTSel!!)
                    }
                    etRehabBudget!!.setText(String.format("$%.0f", calR!!.getBudget()))
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }

        tvClosHoldCosts!!.visibility = View.GONE
        etClosHoldCosts!!.visibility = View.GONE
        tvProfit!!.visibility = View.GONE
        etProfit!!.visibility = View.GONE
        tvROI!!.visibility = View.GONE
        etROI!!.visibility = View.GONE
        tvCashOnCash!!.visibility = View.GONE
        etCashOnCash!!.visibility = View.GONE
        llEditInfo!!.visibility = View.GONE
        btnSave!!.visibility = View.GONE

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Calculate Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the address and square footage of the property, " +
                    "including the number of bedrooms and bathrooms, sale price, ARV, budget items " +
                    "and rehab budget. Rehab budget can be a flat rate or a rehab type. " +
                    "Rehab types are classified as:  Low ($15/sf, yard work and painting), " +
                    "Medium ($20/sf > 1500 sf or $25/sf < 1500 sf, Low + kitchen and bathrooms, " +
                    "High ($30/sf, Medium + new roof), Super-High ($40/sf, complete gut job), " +
                    "Bulldozer ($125/sf, demolition and rebuild).")
                    .setCancelable(false)
                    .setNeutralButton("OK") { dialog, _ ->
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel()
                    }

            // create alert dialog
            val alertDialog = alertDialogBuilder.create()

            // show it
            alertDialog.show()
        }

        // gets Intent and Calculate object
/*
        intR = intent
        calR = intR!!.getSerializableExtra("Calculate") as Calculate
        // if Calculate object is null, fields are blank
        if (calR == null) {
            etAddress!!.setText("")
            etCityStZip!!.setText("")
            etSquareFootage!!.setText("")
            etBedrooms!!.setText("")
            etBathrooms!!.setText("")
            etSalesPrice!!.setText("")
            etFMVARV!!.setText("")
            etBudgetItems!!.setText("")
        } else {
            // set fields to member variables of Calculate object
            etAddress!!.setText(calR!!.getAddress())
            etCityStZip!!.setText(calR!!.getCityStZip())
            etSquareFootage!!.setText(calR!!.getSquareFootage() + "")
            etBedrooms!!.setText(calR!!.getBedrooms() + "")
            etBathrooms!!.setText(calR!!.getBathrooms() + "")
            etSalesPrice!!.setText((calR!!.getSalesPrice() as Int).toString() + "")
            etFMVARV!!.setText((calR!!.getFMVARV() as Int).toString() + "")
            etBudgetItems!!.setText(calR!!.getBudgetItems())
            if (calR!!.getRehabFlag() === 0) {
                rbRehab1!!.isChecked = true
                rbRehab2!!.isChecked = false
                tvRehabFlatRate!!.visibility = View.VISIBLE
                etRehabBudget!!.visibility = View.VISIBLE
                tvRehabType!!.visibility = View.INVISIBLE
                spnRehabType!!.visibility = View.INVISIBLE
                etRehabBudget!!.setText((calR!!.getBudget() as Int).toString() + "")
            } else {
                rbRehab1!!.isChecked = false
                rbRehab2!!.isChecked = true
                tvRehabFlatRate!!.visibility = View.INVISIBLE
                etRehabBudget!!.visibility = View.INVISIBLE
                tvRehabType!!.visibility = View.VISIBLE
                spnRehabType!!.visibility = View.VISIBLE
                val nCostSF = (calR!!.getBudget() / calR!!.getSquareFootage()) as Int
                when (nCostSF) {
                    15 -> spnRehabType!!.setSelection(0)
                    20, 25 -> spnRehabType!!.setSelection(1)
                    30 -> spnRehabType!!.setSelection(2)
                    40 -> spnRehabType!!.setSelection(3)
                    125 -> spnRehabType!!.setSelection(4)
                }
            }
        }
*/
    }

    fun nextPage(view: View) {
        // checks if all fields are filled in, prompts user to fill in fields if any are missing
        if ("" == etAddress!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Address", Toast.LENGTH_SHORT).show()
        } else if ("" == etCityStZip!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter City/State/Zip", Toast.LENGTH_SHORT).show()
        } else if ("" == etSquareFootage!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Square Footage", Toast.LENGTH_SHORT).show()
        } else if ("" == etBedrooms!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Bedrooms", Toast.LENGTH_SHORT).show()
        } else if ("" == etBathrooms!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Bathrooms", Toast.LENGTH_SHORT).show()
        } else if ("" == etSalesPrice!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Sales Price", Toast.LENGTH_SHORT).show()
        } else if ("" == etFMVARV!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Fair Market Value or After Repair Value", Toast.LENGTH_SHORT).show()
        } else if ("" == etBudgetItems!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Budget Items", Toast.LENGTH_SHORT).show()
        } else if (("Flat Rate" == spnRehabType!!.selectedItem.toString()) && ("" == etRehabBudget!!.text.toString())) {
            Toast.makeText(applicationContext, "Must Enter Flat Rate Budget or Rehab Type", Toast.LENGTH_SHORT).show()
        } else {
            tvClosHoldCosts!!.visibility = View.VISIBLE
            etClosHoldCosts!!.visibility = View.VISIBLE
            tvProfit!!.visibility = View.VISIBLE
            etProfit!!.visibility = View.VISIBLE
            tvROI!!.visibility = View.VISIBLE
            etROI!!.visibility = View.VISIBLE
            tvCashOnCash!!.visibility = View.VISIBLE
            etCashOnCash!!.visibility = View.VISIBLE
            llEditInfo!!.visibility = View.VISIBLE
            btnSave!!.visibility = View.VISIBLE

            // sets info from fields into object variables
            calR!!.setAddress(etAddress!!.text.toString())
            calR!!.setCityStZip(etCityStZip!!.text.toString())
            calR!!.setSquareFootage(Integer.parseInt(etSquareFootage!!.text.toString()))
            calR!!.setBedrooms(Integer.parseInt(etBedrooms!!.text.toString()))
            calR!!.setBathrooms(java.lang.Double.parseDouble(etBathrooms!!.text.toString()))
            calR!!.setFMVARV(java.lang.Double.parseDouble(etFMVARV!!.text.toString()))
            calR!!.setSalesPrice(java.lang.Double.parseDouble(etSalesPrice!!.text.toString()))
            calR!!.setBudgetItems(etBudgetItems!!.text.toString())
            calR!!.setClosHoldCosts(calR!!.getFMVARV())
            calR!!.setProfit(calR!!.getSalesPrice(), calR!!.getFMVARV(), calR!!.getBudget())
            calR!!.setROI(calR!!.getFMVARV())
            calR!!.setCashOnCash(calR!!.getBudget())
            if (calR!!.getProfit() < 30000.0) {
                val adAlertBox = AlertDialog.Builder(this).create()
                adAlertBox.setMessage("Your profit is below $30K! Would you like to make changes now?")
                adAlertBox.setButton(DialogInterface.BUTTON_POSITIVE,"Yes") { _, _ -> }
                adAlertBox.setButton(DialogInterface.BUTTON_NEGATIVE,"No") { _, _ -> }
                adAlertBox.show()
            }

            etClosHoldCosts!!.setText(String.format("$%.0f", calR!!.getClosHoldCosts()))
            etClosHoldCosts!!.isEnabled = false
            etProfit!!.setText(String.format("$%.0f", calR!!.getProfit()))
            etProfit!!.isEnabled = false
            val strROI = String.format("%.1f", calR!!.getROI()) + "%"
            etROI!!.setText(strROI)
            etROI!!.isEnabled = false
            val strCashOnCash = String.format("%.1f", calR!!.getCashOnCash()) + "%"
            etCashOnCash!!.setText(strCashOnCash)
            etCashOnCash!!.isEnabled = false
        }
    }

    @Throws(FileNotFoundException::class, IOException::class, WriteException::class)
    fun saveFile(view: View) {
        val myDir = File(applicationContext?.getExternalFilesDir(null)?.toString() + "/")
        if (!myDir.exists())
            myDir.mkdirs()

        val strFileNameXls = calR!!.getAddress() + " " + calR!!.getCityStZip() + ".xls"
        val file = File(myDir, strFileNameXls)
        if (!file.exists())
            file.createNewFile()

        // create Excel spreadsheet
        xlsSpreadsheet!!.createSpreadsheet(myDir, calR!!, strFileNameXls)

        val strSavedFile = "File saved as: $strFileNameXls"
        Toast.makeText(applicationContext, strSavedFile, Toast.LENGTH_SHORT).show()
    }

    fun emailFile(view: View) {
        val adAlertBox = AlertDialog.Builder(this).create()
        adAlertBox.setMessage("Do you want to email results as text or as an Excel spreadsheet?")
        adAlertBox.setButton(DialogInterface.BUTTON_POSITIVE,"Text") { _, _ ->
                    // do something when the button is clicked
                    emailPlainText()
                }
        adAlertBox.setButton(DialogInterface.BUTTON_NEGATIVE,"Excel") { _, _ ->
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

    private fun emailPlainText() {
        // email results of calculate to those parties concerned
        var strMessage = "Address:                " + calR!!.getAddress() + "\n"
        strMessage += "City, State ZIP:        " + calR!!.getCityStZip() + "\n"
        strMessage += "Square Footage:         " + calR!!.getSquareFootage() + "\n"
        strMessage += "Bedrooms/Bathrooms:     " + calR!!.getBedrooms() + " BR " + calR!!.getBathrooms() + " BA\n"
        strMessage += "After Repair Value:    $" + String.format("%.0f", calR!!.getFMVARV()) + "\n"
        strMessage += "Sales Price:           $" + String.format("%.0f", calR!!.getSalesPrice()) + "\n"
        strMessage += "Estimated Budget:      $" + String.format("%.0f", calR!!.getBudget()) + "\n"
        strMessage += "Budget Items:           " + calR!!.getBudgetItems() + "\n"
        strMessage += "Closing/Holding Costs: $" + String.format("%.0f", calR!!.getClosHoldCosts()) + "\n"
        strMessage += "Profit:                $" + String.format("%.0f", calR!!.getProfit()) + "\n"
        strMessage += "ROI:                    " + String.format("%.1f", calR!!.getROI()) + "%\n"
        strMessage += "Cash on Cash Return:    " + String.format("%.1f", calR!!.getCashOnCash()) + "%\n"
        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Free results for: " + calR!!.getAddress() + " " + calR!!.getCityStZip())
        intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage)
        intEmailActivity.type = "plain/text"
        startActivity(intEmailActivity)
    }

    @Throws(IOException::class, WriteException::class)
    fun emailExcelSpreadsheet() {
        val myDir = File(applicationContext.getExternalFilesDir(null)!!.toString() + "/FlipulatorFree")
        myDir.mkdirs()
        val strFileNameXls = calR!!.getAddress() + " " + calR!!.getCityStZip() + ".xls"
        val file = File(myDir, strFileNameXls)

        if (!file.exists()) {
            xlsSpreadsheet!!.createSpreadsheet(myDir, calR!!, strFileNameXls)
        }

        val intEmailActivity = Intent(Intent.ACTION_SEND)
        intEmailActivity.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>())
        intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Free results for: " + calR!!.getAddress() + " " + calR!!.getCityStZip())
        intEmailActivity.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///$file"))
        intEmailActivity.type = "application/excel"
        startActivity(intEmailActivity)
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }

    private fun exitByBackKey() {
        val adAlertBox = AlertDialog.Builder(this).create()
        adAlertBox.setMessage("Do you want to go back to main menu?")
        adAlertBox.setButton(DialogInterface.BUTTON_POSITIVE,"Yes") { _, _ ->
            // do something when the button is clicked
            val intB = Intent(this@CalculateActivity, MainActivity::class.java)
            startActivity(intB)
            finish()
        }
        adAlertBox.setButton(DialogInterface.BUTTON_NEGATIVE,"No") { _, _ -> }
        adAlertBox.show()
    }
}
