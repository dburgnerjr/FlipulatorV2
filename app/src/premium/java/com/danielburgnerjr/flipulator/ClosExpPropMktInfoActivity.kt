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

class ClosExpPropMktInfoActivity : Activity() {

    private val cntC: Context = this
    private var calC: Calculate? = null

    private var etRealEstComm: EditText? = null        // real estate commission
    private var etBuyClosCost: EditText? = null        // buyer's closing costs
    private var etSellClosCost: EditText? = null    // seller's closing costs
    private var etFMVARV: EditText? = null            // fair mkt value/after repair value
    private var etComparables: EditText? = null        // comparables
    private var etSellingPrice: EditText? = null    // selling price
    private var btnHelp: Button? = null                // help

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity_four)

        val intI = getIntent()

        etRealEstComm = findViewById<View>(R.id.txtCommission) as EditText
        etBuyClosCost = findViewById<View>(R.id.txtBuyerClosCost) as EditText
        etSellClosCost = findViewById<View>(R.id.txtSellClosCost) as EditText
        etFMVARV = findViewById<View>(R.id.txtFMVARV) as EditText
        etComparables = findViewById<View>(R.id.txtComparables) as EditText
        etSellingPrice = findViewById<View>(R.id.txtSellingPrice) as EditText
        btnHelp = findViewById<View>(R.id.txtHelp) as Button

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Closing Expenses/Property Market Info Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the real estate commission percentage, buyer and seller closing " +
                    "cost percentage, fair market value/after repair value, comparables and " +
                    "selling price.")
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

        calC = intI.getSerializableExtra("Calculate") as Calculate
        // if ClosExpPropMktInfo object is null, fields are blank
        if (calC == null) {
            etRealEstComm!!.setText("")
            etBuyClosCost!!.setText("")
            etSellClosCost!!.setText("")
            etFMVARV!!.setText("")
            etComparables!!.setText("")
            etSellingPrice!!.setText("")
        } else {
            // set fields to member variables of ClosExpPropMktInfo object
            etRealEstComm!!.setText(calC!!.getRealEstComm().toString())
            etBuyClosCost!!.setText(calC!!.getBuyClosCost().toString())
            etSellClosCost!!.setText(calC!!.getSellClosCost().toString())
            etFMVARV!!.setText(calC!!.getFMVARV().toString())
            etComparables!!.setText(calC!!.getComparables().toString())
            etSellingPrice!!.setText(calC!!.getSellingPrice().toString())
        }

    }

    fun prevPage(view: View) {
        val intI = Intent(this, ReservesActivity::class.java)
        if (calC != null) {
            intI.putExtra("Calculate", calC)
        }
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {
        if ("" == etRealEstComm!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Real Estate Commission", Toast.LENGTH_SHORT).show()
        } else if ("" == etBuyClosCost!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Buyer's Closing Cost", Toast.LENGTH_SHORT).show()
        } else if ("" == etSellClosCost!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Seller's Closing Cost", Toast.LENGTH_SHORT).show()
        } else if ("" == etFMVARV!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Fair Market Value or After Repair Value", Toast.LENGTH_SHORT).show()
        } else if ("" == etComparables!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Comparables", Toast.LENGTH_SHORT).show()
        } else if ("" == etSellingPrice!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Selling Price", Toast.LENGTH_SHORT).show()
        } else {
            //val intI = Intent(this, FinalResultActivity::class.java)

            calC!!.setRealEstComm(java.lang.Double.parseDouble(etRealEstComm!!.text.toString()))
            calC!!.setBuyClosCost(java.lang.Double.parseDouble(etBuyClosCost!!.text.toString()))
            calC!!.setSellClosCost(java.lang.Double.parseDouble(etSellClosCost!!.text.toString()))
            calC!!.setFMVARV(java.lang.Double.parseDouble(etFMVARV!!.text.toString()))
            calC!!.setComparables(java.lang.Double.parseDouble(etComparables!!.text.toString()))
            calC!!.setSellingPrice(java.lang.Double.parseDouble(etSellingPrice!!.text.toString()))
            Toast.makeText(applicationContext,  calC.toString(), Toast.LENGTH_SHORT).show()

            //intI.putExtra("Calculate", calC)
            //startActivity(intI)
            //finish()
        }
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        var strBackMessage = "Press Previous to return to Reserves, Next to see Final Results "
        strBackMessage += "or Help for assistance."
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(applicationContext, strBackMessage, Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }
}
