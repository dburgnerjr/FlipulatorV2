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
import android.widget.EditText
import android.widget.Button
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate

class ReservesActivity : Activity() {

    private val cntC: Context = this
    private var calC: Calculate? = null

    private var etInsurance: EditText? = null        // sales price
    private var etTaxes: EditText? = null            // property taxes
    private var etElectric: EditText? = null        // electricity
    private var etGas: EditText? = null                // gas
    private var etWater: EditText? = null            // water
    private var btnHelp: Button? = null                // help

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity_three)

        val intI = getIntent()
        calC = intI.getSerializableExtra("Calculate") as? Calculate

        etInsurance = findViewById<View>(R.id.txtInsurance) as EditText
        etTaxes = findViewById<View>(R.id.txtTaxes) as EditText
        etElectric = findViewById<View>(R.id.txtElectric) as EditText
        etGas = findViewById<View>(R.id.txtGas) as EditText
        etWater = findViewById<View>(R.id.txtWater) as EditText
        btnHelp = findViewById<View>(R.id.txtHelp) as Button

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Reserves Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the insurance costs, property taxes, electric, gas and water. " +
                    "Insurance and property taxes (annually) are divided by two while " +
                    "electric, gas and water (monthly) are multiplied by six.")
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

    }

    fun prevPage(view: View) {
        val intI = Intent(this, SalesMortgageActivity::class.java)
        if (calC != null) {
            intI.putExtra("Calculate", calC)
        }
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {
        if ("" == etInsurance!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Insurance For 6 Mos", Toast.LENGTH_SHORT).show()
        } else if ("" == etTaxes!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Property Taxes For 6 Mos", Toast.LENGTH_SHORT).show()
        } else if ("" == etWater!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Water Bill For 6 Mos", Toast.LENGTH_SHORT).show()
        } else if ("" == etGas!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Gas Bill For 6 Mos", Toast.LENGTH_SHORT).show()
        } else if ("" == etElectric!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Electric Bill For 6 Mos", Toast.LENGTH_SHORT).show()
        } else {
/*
            val intI = Intent(this, ClosExpPropMktInfoActivity::class.java)
*/
            calC!!.setMortgage(calC!!.getMonthlyPmt() * 6)
            calC!!.setInsurance(java.lang.Double.parseDouble(etInsurance!!.text.toString()))
            calC!!.setTaxes(java.lang.Double.parseDouble(etTaxes!!.text.toString()))
            calC!!.setWater(java.lang.Double.parseDouble(etWater!!.text.toString()))
            calC!!.setGas(java.lang.Double.parseDouble(etGas!!.text.toString()))
            calC!!.setElectric(java.lang.Double.parseDouble(etElectric!!.text.toString()))
            calC!!.setTotalExpenses()
            Toast.makeText(applicationContext,  calC.toString(), Toast.LENGTH_SHORT).show()
/*
            intI.putExtra("Calculate", calC)
            startActivity(intI)
            finish()
*/
        }
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        var strBackMessage = "Press Previous to return to Sales/Mortgage, Next to enter Closing Expenses/"
        strBackMessage += "Property Market info or Help for assistance."
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(applicationContext, strBackMessage, Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }
}
