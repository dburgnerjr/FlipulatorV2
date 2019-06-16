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
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate

class SalesMortgageActivity : Activity() {

    private val cntC: Context = this
    private var calC: Calculate? = null

    private var etSalesPrice: EditText? = null        // sales price
    private var etPercentDown: EditText? = null        // percent down
    private var etOfferBid: EditText? = null        // offer/bid price
    private var etInterestRate: EditText? = null    // interest rate
    private var etTerm: EditText? = null            // term
    private var tvRehabFlatRate: TextView? = null
    private var etRehabBudget: EditText? = null
    private var etBudgetItems: EditText? = null        // budget items
    private var tvRehabType: TextView? = null
    private var spnRehabType: Spinner? = null
    private var btnHelp: Button? = null                // help
    private var strRTSel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_activity_two)

        val intI = getIntent()
        calC = intI.getSerializableExtra("Calculate") as? Calculate

        etSalesPrice = findViewById(R.id.txtSalePrice) as EditText
        etPercentDown = findViewById(R.id.txtPercentDown) as EditText
        etOfferBid = findViewById(R.id.txtOfferBid) as EditText
        etInterestRate = findViewById(R.id.txtInterestRate) as EditText
        etTerm = findViewById(R.id.txtTerm) as EditText
        etBudgetItems = findViewById(R.id.txtBudgetItems) as EditText

        val aradAdapter = ArrayAdapter.createFromResource(
                this, R.array.rehab_type, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tvRehabFlatRate = findViewById(R.id.tvRehabBudget) as TextView
        etRehabBudget = findViewById(R.id.txtRehabBudget) as EditText
        tvRehabType = findViewById(R.id.tvRehabType) as TextView
        spnRehabType = findViewById(R.id.spnRehabType) as Spinner
        btnHelp = findViewById<View>(R.id.txtHelp) as Button
        spnRehabType!!.adapter = aradAdapter
        Toast.makeText(applicationContext, "Sales/Mortgage Activity: " + calC.toString(), Toast.LENGTH_SHORT).show()

        spnRehabType!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if (position > 0) {
                    strRTSel = parentView.getItemAtPosition(position).toString()
                    when (strRTSel) {
                        "Low", "Medium", "High", "Super-High", "Bulldozer" -> calC!!.calcBudgetRehabType(strRTSel!!)
                    }
                    etRehabBudget!!.setText("$" + calC!!.getBudget().toString())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        })

        // add button listener
        btnHelp!!.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(cntC)

            // set title
            alertDialogBuilder.setTitle("Sales/Mortgage Help")

            // set dialog message
            alertDialogBuilder.setMessage("Enter the sales price, percentage down, offer or bid, interest rate, " +
                    "term of mortgage, budget items and rehab budget.  Rehab budget can be a flat rate or " +
                    "a rehab type. Rehab types are classified as:  Low ($15/sf, yard work and " +
                    "painting), Medium ($20/sf > 1500 sf or $25/sf < 1500 sf, Low + kitchen and " +
                    "bathrooms, High ($30/sf, Medium + new roof), Super-High ($40/sf, complete " +
                    "gut job), Bulldozer ($125/sf, demolition and rebuild).")
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
        smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
        // if SalesMortgage object is null, fields are blank
        if (smSM == null) {
            etSalesPrice.setText("");
            etPercentDown.setText("");
            etOfferBid.setText("");
            etInterestRate.setText("");
            etTerm.setText("");
        } else {
            // set fields to member variables of SalesMortgage object
            etSalesPrice.setText((int)smSM.getSalesPrice() + "");
            etPercentDown.setText((int)smSM.getPercentDown() + "");
            etOfferBid.setText((int)smSM.getOfferBid() + "");
            etInterestRate.setText((int)smSM.getInterestRate() + "");
            etTerm.setText(smSM.getTerm() + "");
        }

        setS = (Settings) intI.getSerializableExtra("Settings");
        // if Settings rehab flag is 0, show rehab budget flat rate
        if (setS.getRehab() == 0) {
            tvRehabFlatRate.setVisibility(View.VISIBLE);
            etRehabBudget.setVisibility(View.VISIBLE);
            tvRehabType.setVisibility(View.INVISIBLE);
            spnRehabType.setVisibility(View.INVISIBLE);
        } else {
            // show rehab class
            tvRehabFlatRate.setVisibility(View.INVISIBLE);
            etRehabBudget.setVisibility(View.INVISIBLE);
            tvRehabType.setVisibility(View.VISIBLE);
            spnRehabType.setVisibility(View.VISIBLE);
        }

        rR = (Rehab) intI.getSerializableExtra("Rehab");
        // if Rehab object is null, fields are blank
        if (rR == null) {
            etBudgetItems.setText("");
            if (setS.getRehab() == 0) {
                etRehabBudget.setText("");
            } else {
                spnRehabType.setSelection(0);
            }
        } else {
            etBudgetItems.setText(rR.getBudgetItems());
            if (setS.getRehab() == 0) {
                etRehabBudget.setText((int)rR.getBudget() + "");
            } else {
                int nCostSF = (int)(rR.getBudget()/locL.getSquareFootage());
                switch (nCostSF) {
                    case 15:
                        spnRehabType.setSelection(0);
                        break;
                    case 20:
                    case 25:
                        spnRehabType.setSelection(1);
                        break;
                    case 30:
                        spnRehabType.setSelection(2);
                        break;
                    case 40:
                        spnRehabType.setSelection(3);
                        break;
                    case 125:
                        spnRehabType.setSelection(4);
                        break;
                }
            }
        }
        resR = (Reserves) intI.getSerializableExtra("Reserves");
        cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");
*/
    }

    fun prevPage(view: View) {
        val intI = Intent(this, LocationActivity::class.java)
        if (calC != null) {
            intI.putExtra("Calculate", calC)
        }
        startActivity(intI)
        finish()
    }

    fun nextPage(view: View) {
        if ("" == etSalesPrice!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Sales Price", Toast.LENGTH_SHORT).show()
        } else if ("" == etPercentDown!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Percent Down", Toast.LENGTH_SHORT).show()
        } else if ("" == etOfferBid!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Offer Bid", Toast.LENGTH_SHORT).show()
        } else if ("" == etInterestRate!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Interest Rate", Toast.LENGTH_SHORT).show()
        } else if ("" == etTerm!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Term", Toast.LENGTH_SHORT).show()
        } else if ("" == etBudgetItems!!.text.toString()) {
            Toast.makeText(applicationContext, "Must Enter Budget Items", Toast.LENGTH_SHORT).show()
        } else if (("Flat Rate" == spnRehabType!!.getSelectedItem().toString()) && ("" == etRehabBudget!!.text.toString())) {
            Toast.makeText(applicationContext, "Must Enter Flat Rate Budget or Rehab Type", Toast.LENGTH_SHORT).show()
        } else {
/*
            val intI = Intent(this, ReservesActivity::class.java)
            intI.putExtra("Location", locL)
            intI.putExtra("Settings", setS)
*/

/*
            val smSM = SalesMortgage()
            smSM.setSalesPrice(java.lang.Double.parseDouble(etSalesPrice!!.text.toString()))
            smSM.setPercentDown(java.lang.Double.parseDouble(etPercentDown!!.text.toString()))
            smSM.setOfferBid(java.lang.Double.parseDouble(etOfferBid!!.text.toString()))
            smSM.setDownPayment(java.lang.Double.parseDouble(etPercentDown!!.text.toString()), java.lang.Double.parseDouble(etOfferBid!!.text.toString()))
            smSM.setLoanAmount()
            smSM.setInterestRate(java.lang.Double.parseDouble(etInterestRate!!.text.toString()))
            smSM.setTerm(Integer.parseInt(etTerm!!.text.toString()))
            // if finance rehab flag is not selected, set monthly payment as follows
            if (setS.getFinance() !== 2) {
                smSM.setMonthlyPmt()
                intI.putExtra("SalesMortgage", smSM)
            }
*/

/*
            val rR: Rehab
            val strBI = etBudgetItems!!.text.toString()
            if (setS.getRehab() === 0) {
                if ("" == etRehabBudget!!.text.toString()) {
                    Toast.makeText(applicationContext, "Must Enter Rehab Budget", Toast.LENGTH_SHORT).show()
                } else {
                    val dB = java.lang.Double.parseDouble(etRehabBudget!!.text.toString())
                    rR = RehabFlatRate(dB, strBI)
                    // if finance rehab flag is selected, set monthly payment as follows
                    if (setS.getFinance() === 2) {
                        smSM.setMonthlyPmt(rR.getBudget())
                        intI.putExtra("SalesMortgage", smSM)
                    }
                    intI.putExtra("Rehab", rR)
                    if (resR != null) {
                        intI.putExtra("Reserves", resR)
                    }
                    if (cemC != null) {
                        intI.putExtra("ClosExpPropMktInfo", cemC)
                    }
                    startActivity(intI)
                    finish()
                }
            } else {
                val strRTSel = spnRehabType!!.selectedItem.toString()
                rR = RehabType(locL.getSquareFootage(), strRTSel, strBI)
                // if finance rehab flag is selected, set monthly payment as follows
                if (setS.getFinance() === 2) {
                    smSM.setMonthlyPmt(rR.getBudget())
                    intI.putExtra("SalesMortgage", smSM)
                }
                intI.putExtra("Rehab", rR)
                if (resR != null) {
                    intI.putExtra("Reserves", resR)
                }
                if (cemC != null) {
                    intI.putExtra("ClosExpPropMktInfo", cemC)
                }
                startActivity(intI)
                finish()
            }
*/
        }
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        var strBackMessage = "Press Previous to return to Location, Next to enter Reserves info "
        strBackMessage += "or Help for assistance."
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(applicationContext, strBackMessage, Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }
}