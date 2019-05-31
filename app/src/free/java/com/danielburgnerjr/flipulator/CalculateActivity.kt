package com.danielburgnerjr.flipulator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.RadioGroup.OnCheckedChangeListener

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

class CalculateActivity : Activity() {

    internal val cntC: Context = this
    private var calR: Calculate? = null                // Calculate object from ResultsActivity
    private var intR: Intent? = null                // Intent object from ResultsActivity

    private var etAddress: EditText? = null            // address
    private var etCityStZip: EditText? = null        // city state zip code
    private var etSquareFootage: EditText? = null    // square footage
    private var etBedrooms: EditText? = null        // number of bedrooms
    private var etBathrooms: EditText? = null        // number of bathrooms
    private var etSalesPrice: EditText? = null        // sales price
    private var etFMVARV: EditText? = null            // fair mkt value/after repair value
    private var etBudgetItems: EditText? = null        // budget items
    private var rgRehab: RadioGroup? = null            // radio group rehab
    private var rbRehab: RadioButton? = null        // rehab button id
    private var rbRehab1: RadioButton? = null        // rehab number
    private var rbRehab2: RadioButton? = null        // rehab type
    private var tvRehabFlatRate: TextView? = null    // rehab budget flat rate textview
    private var etRehabBudget: EditText? = null        // rehab budget
    private var tvRehabType: TextView? = null        // rehab type textview
    private var spnRehabType: Spinner? = null        // rehab type
    private var btnHelp: Button? = null                // help
    private var dB: Double = 0.toDouble()                    // budget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)
/*
        val mAdCalcView = findViewById(R.id.adCalcView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdCalcView.loadAd(adRequest)
*/

        etAddress = findViewById(R.id.txtAddress) as EditText
        etCityStZip = findViewById(R.id.txtCityStZip) as EditText
        etSquareFootage = findViewById(R.id.txtSq_Footage) as EditText
        etBedrooms = findViewById(R.id.txtBedrooms) as EditText
        etBathrooms = findViewById(R.id.txtBathrooms) as EditText
        etSalesPrice = findViewById(R.id.txtSalePrice) as EditText
        etFMVARV = findViewById(R.id.txtFMVARV) as EditText
        etBudgetItems = findViewById(R.id.txtBudgetItems) as EditText
        btnHelp = findViewById(R.id.txtHelp) as Button

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.rehab_type, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val aradRehabType = ArrayAdapter<String>(this, R.layout.rehab_type, R.array.rehab_type)

        rgRehab = findViewById(R.id.rdoRehab) as RadioGroup
        rbRehab1 = findViewById(R.id.rdoRehabNumber) as RadioButton
        rbRehab2 = findViewById(R.id.rdoRehabType) as RadioButton
        tvRehabFlatRate = findViewById(R.id.tvRehabBudget) as TextView
        etRehabBudget = findViewById(R.id.txtRehabBudget) as EditText
        tvRehabType = findViewById(R.id.tvRehabType) as TextView
        spnRehabType = findViewById(R.id.spnRehabType) as Spinner
        btnHelp = findViewById(R.id.txtHelp) as Button
        spnRehabType!!.adapter = aradAdapter

        tvRehabFlatRate!!.visibility = View.GONE
        etRehabBudget!!.visibility = View.GONE
        tvRehabType!!.visibility = View.GONE
        spnRehabType!!.visibility = View.GONE

        rgRehab!!.setOnCheckedChangeListener { rgG, nChecked ->
            if (nChecked == R.id.rdoRehabNumber) {
                tvRehabFlatRate!!.visibility = View.VISIBLE
                etRehabBudget!!.visibility = View.VISIBLE
                tvRehabType!!.visibility = View.INVISIBLE
                spnRehabType!!.visibility = View.INVISIBLE
            } else if (nChecked == R.id.rdoRehabType) {
                tvRehabFlatRate!!.visibility = View.INVISIBLE
                etRehabBudget!!.visibility = View.INVISIBLE
                tvRehabType!!.visibility = View.VISIBLE
                spnRehabType!!.visibility = View.VISIBLE
            } else {
                tvRehabFlatRate!!.visibility = View.INVISIBLE
                etRehabBudget!!.visibility = View.INVISIBLE
                tvRehabType!!.visibility = View.INVISIBLE
                spnRehabType!!.visibility = View.INVISIBLE
            }
        }


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
                    .setNeutralButton("OK") { dialog, id ->
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
        } else {
            val intI = Intent(this@CalculateActivity, ResultsActivity::class.java)

            // creates new Calculate object and sets info from fields into object variables
            val calC = Calculate()
            calC.setAddress(etAddress!!.text.toString())
            calC.setCityStZip(etCityStZip!!.text.toString())
            calC.setSquareFootage(Integer.parseInt(etSquareFootage!!.text.toString()))
            calC.setBedrooms(Integer.parseInt(etBedrooms!!.text.toString()))
            calC.setBathrooms(java.lang.Double.parseDouble(etBathrooms!!.text.toString()))
            calC.setFMVARV(Integer.parseInt(etFMVARV!!.text.toString()))
            calC.setSalesPrice(Integer.parseInt(etSalesPrice!!.text.toString()))
            calC.setBudgetItems(etBudgetItems!!.text.toString())
            val nSelected = rgRehab!!.checkedRadioButtonId
            rbRehab = findViewById<View>(nSelected) as RadioButton

            // determines Rehab object by radio button input
            when (nSelected) {

                R.id.rdoRehabNumber -> {
                    if ("" == etRehabBudget!!.text.toString()) {
                        Toast.makeText(applicationContext, "Must Enter Rehab Budget", Toast.LENGTH_SHORT).show()
                    } else {
                        dB = java.lang.Double.parseDouble(etRehabBudget!!.text.toString())
                        calC.setBudget(dB)
                    }
                    calC.setRehabFlag(0)
                }

                R.id.rdoRehabType -> {
                    val strRTSel = spnRehabType!!.selectedItem.toString()
                    calC.calcBudgetRehabType(strRTSel)
                    calC.setRehabFlag(1)
                }
            }

            // stores Calculate object in Intent
            intI.putExtra("Calculate", calC)
            startActivity(intI)
            finish()
        }
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }

    protected fun exitByBackKey() {
        val adAlertBox = AlertDialog.Builder(this)
                .setMessage("Do you want to go back to main menu?")
                .setPositiveButton("Yes") { arg0, arg1 ->
                    // do something when the button is clicked
                    val intB = Intent(this@CalculateActivity, MainActivity::class.java)
                    startActivity(intB)
                    finish()
                    //close();
                }
                .setNegativeButton("No") { arg0, arg1 -> }
                .show()
    }
}