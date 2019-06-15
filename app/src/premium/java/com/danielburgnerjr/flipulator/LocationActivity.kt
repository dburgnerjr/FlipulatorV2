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
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate

class LocationActivity : Activity() {

    private val cntC: Context = this
    private var calC: Calculate? = null
/*
    private var locL: Location? = null
    private var smSM: SalesMortgage? = null
    private var rR: Rehab? = null
    private var resR: Reserves? = null
    private var cemC: ClosExpPropMktInfo? = null
*/

    private var etAddress: EditText? = null            // address
    private var etCity: EditText? = null            // city
    private var etState: EditText? = null            // state
    private var etZIPCode: EditText? = null            // ZIP Code
    private var etSquareFootage: EditText? = null    // square footage
    private var etBedrooms: EditText? = null        // number of bedrooms
    private var etBathrooms: EditText? = null        // number of bathrooms
    private var spnFinanceType: Spinner? = null
    private var btnHelp: Button? = null                // help

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)

        val intI = Intent()

        etAddress = findViewById<EditText>(R.id.txtAddress)
        etCity = findViewById<EditText>(R.id.txtCity)
        etState = findViewById<EditText>(R.id.txtState)
        etZIPCode = findViewById<EditText>(R.id.txtZIP_Code)
        etSquareFootage = findViewById<EditText>(R.id.txtSq_Footage)
        etBedrooms = findViewById<EditText>(R.id.txtBedrooms)
        etBathrooms = findViewById<EditText>(R.id.txtBathrooms)
        spnFinanceType = findViewById(R.id.spnFinanceType)
        btnHelp = findViewById<Button>(R.id.txtHelp)

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.finance_type, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFinanceType!!.adapter = aradAdapter

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Location Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the address, city, state, ZIP code and square footage of the property, " +
                    "including the number of bedrooms and bathrooms.  Please include the finance type.")
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
/*
        locL = intI.getSerializableExtra("Location") as Location
        // if Location object is null, fields are blank
        if (locL == null) {
            etAddress!!.setText("")
            etCity!!.setText("")
            etState!!.setText("")
            etZIPCode!!.setText("")
            etSquareFootage!!.setText("")
            etBedrooms!!.setText("")
            etBathrooms!!.setText("")
        } else {
            // set fields to member variables of Location object
            etAddress!!.setText(locL!!.getAddress())
            etCity!!.setText(locL!!.getCity())
            etState!!.setText(locL!!.getState())
            etZIPCode!!.setText(locL!!.getZIPCode())
            etSquareFootage!!.setText(locL!!.getSquareFootage() + "")
            etBedrooms!!.setText(locL!!.getBedrooms() + "")
            etBathrooms!!.setText(locL!!.getBathrooms() + "")
        }
        smSM = intI.getSerializableExtra("SalesMortgage") as SalesMortgage
        rR = intI.getSerializableExtra("Rehab") as Rehab
        resR = intI.getSerializableExtra("Reserves") as Reserves
        cemC = intI.getSerializableExtra("ClosExpPropMktInfo") as ClosExpPropMktInfo
*/
    }

    fun prevPage(view: View) {
        val intI = Intent(this, MainActivity::class.java)
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {

        if ("" == etAddress!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Address", Toast.LENGTH_SHORT).show()
        } else if ("" == etCity!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter City", Toast.LENGTH_SHORT).show()
        } else if ("" == etState!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter State", Toast.LENGTH_SHORT).show()
        } else if ("" == etZIPCode!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter ZIP Code", Toast.LENGTH_SHORT).show()
        } else if ("" == etSquareFootage!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Square Footage", Toast.LENGTH_SHORT).show()
        } else if ("" == etBedrooms!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Bedrooms", Toast.LENGTH_SHORT).show()
        } else if ("" == etBathrooms!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Bathrooms", Toast.LENGTH_SHORT).show()
        } else {
            //val intI = Intent(this, SalesMortgageActivity::class.java)

            calC = Calculate()
            calC!!.setAddress(etAddress!!.text.toString())
            calC!!.setCity(etCity!!.text.toString())
            calC!!.setState(etState!!.text.toString())
            calC!!.setZipCode(etZIPCode!!.text.toString())
            calC!!.setSquareFootage(Integer.parseInt(etSquareFootage!!.text.toString()))
            calC!!.setBedrooms(Integer.parseInt(etBedrooms!!.text.toString()))
            calC!!.setBathrooms(java.lang.Double.parseDouble(etBathrooms!!.text.toString()))
            Toast.makeText(applicationContext, "All values saved to Calculate object.", Toast.LENGTH_SHORT).show()
/*
            intI.putExtra("Settings", setS)
            intI.putExtra("Location", locL)
            if (smSM != null) {
                intI.putExtra("SalesMortgage", smSM)
            }
            if (rR != null) {
                intI.putExtra("Rehab", rR)
            }
            if (resR != null) {
                intI.putExtra("Reserves", resR)
            }
            if (cemC != null) {
                intI.putExtra("ClosExpPropMktInfo", cemC)
            }
            startActivity(intI)
            finish()
*/
        }
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        var strBackMessage = "Press Previous to return to Settings, Next to enter Sales/Mortgage Info "
        strBackMessage += "or Help for assistance."
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(applicationContext, strBackMessage, Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }
}
