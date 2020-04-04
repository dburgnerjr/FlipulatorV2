package com.danielburgnerjr.flipulator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate

class LocationActivity : Activity() {

    private val cntC: Context = this
    private var calC: Calculate? = null

    private var etAddress: EditText? = null            // address
    private var etCity: EditText? = null            // city
    private var etState: EditText? = null            // state
    private var etZIPCode: EditText? = null            // ZIP Code
    private var etSquareFootage: EditText? = null    // square footage
    private var etBedrooms: EditText? = null        // number of bedrooms
    private var etBathrooms: EditText? = null        // number of bathrooms
    private var spnFinanceType: Spinner? = null
    private var nFinance: Int = 0
    private var strFinance: String = ""
    private var btnHelp: Button? = null                // help

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity)

        val intI = intent

        etAddress = findViewById(R.id.txtAddress)
        etCity = findViewById(R.id.txtCity)
        etState = findViewById(R.id.txtState)
        etZIPCode = findViewById(R.id.txtZIP_Code)
        etSquareFootage = findViewById(R.id.txtSq_Footage)
        etBedrooms = findViewById(R.id.txtBedrooms)
        etBathrooms = findViewById(R.id.txtBathrooms)
        spnFinanceType = findViewById(R.id.spnFinanceType)
        btnHelp = findViewById(R.id.txtHelp)

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.finance_type, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFinanceType!!.adapter = aradAdapter

        spnFinanceType!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                nFinance = position
                strFinance = parentView.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Location Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the address, city, state, ZIP code and square footage of the property, " +
                    "including the number of bedrooms and bathrooms.  Please include the finance type.")
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

        if (intI.getSerializableExtra("Calculate") != null)
            calC = intI.getSerializableExtra("Calculate") as Calculate

        // if Calculate object is null, fields are blank
        if (calC == null) {
            etAddress!!.setText("")
            etCity!!.setText("")
            etState!!.setText("")
            etZIPCode!!.setText("")
            etSquareFootage!!.setText("")
            etBedrooms!!.setText("")
            etBathrooms!!.setText("")
        } else {
            // set fields to member variables of Calculate object
            etAddress!!.setText(calC!!.getAddress())
            etCity!!.setText(calC!!.getCity())
            etState!!.setText(calC!!.getState())
            etZIPCode!!.setText(calC!!.getZipCode())
            etSquareFootage!!.setText(calC!!.getSquareFootage().toString())
            etBedrooms!!.setText(calC!!.getBedrooms().toString())
            etBathrooms!!.setText(calC!!.getBathrooms().toString())
        }
    }

    fun prevPage(view: View) {
        val intI = Intent(this, MainActivity::class.java)
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {
        when {
            "" == etAddress!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter Address", Toast.LENGTH_SHORT).show()
            }
            "" == etCity!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter City", Toast.LENGTH_SHORT).show()
            }
            "" == etState!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter State", Toast.LENGTH_SHORT).show()
            }
            "" == etZIPCode!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter ZIP Code", Toast.LENGTH_SHORT).show()
            }
            "" == etSquareFootage!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter Square Footage", Toast.LENGTH_SHORT).show()
            }
            "" == etBedrooms!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter Bedrooms", Toast.LENGTH_SHORT).show()
            }
            "" == etBathrooms!!.text.toString() -> {
                Toast.makeText(applicationContext, "Must Enter Bathrooms", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val intI = Intent(this, SalesMortgageActivity::class.java)

                if (calC == null)
                    calC = Calculate()

                calC!!.setAddress(etAddress!!.text.toString())
                calC!!.setCity(etCity!!.text.toString())
                calC!!.setState(etState!!.text.toString())
                calC!!.setZipCode(etZIPCode!!.text.toString())
                calC!!.setSquareFootage(Integer.parseInt(etSquareFootage!!.text.toString()))
                calC!!.setBedrooms(Integer.parseInt(etBedrooms!!.text.toString()))
                calC!!.setBathrooms(java.lang.Double.parseDouble(etBathrooms!!.text.toString()))
                calC!!.setFinance(nFinance)
                calC!!.setFinanceValue(strFinance)

                intI.putExtra("Calculate", calC)
                startActivity(intI)
                finish()
            }
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
