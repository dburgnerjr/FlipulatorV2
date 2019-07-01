package com.danielburgnerjr.flipulator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import com.danielburgnerjr.flipulator.model.Calculate

class ReservesFinalResultFragment : Fragment() {

    private var calC: Calculate? = null
    private var intI: Intent? = null
    private var spnTimeFrame: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_reserves_final_result, container, false)
        intI = getActivity()!!.getIntent()

        calC = intI!!.getSerializableExtra("Calculate") as Calculate

        val aradAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.time_frame, android.R.layout.simple_spinner_item)
        aradAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnTimeFrame = view.findViewById<View>(R.id.spnTimeFrame) as Spinner
        spnTimeFrame!!.adapter = aradAdapter

        val tvMortPmt = view.findViewById<View>(R.id.txtMortPmtFR) as TextView
        val tvInsurance = view.findViewById<View>(R.id.txtInsuranceFR) as TextView
        val tvTaxes = view.findViewById<View>(R.id.txtTaxesFR) as TextView
        val tvWater = view.findViewById<View>(R.id.txtWaterFR) as TextView
        val tvGas = view.findViewById<View>(R.id.txtGasFR) as TextView
        val tvElectric = view.findViewById<View>(R.id.txtElectricFR) as TextView
        val tvTotalExpenses = view.findViewById<View>(R.id.txtTotalReserves) as TextView
        val txtTotalCostsFR = view.findViewById<View>(R.id.txtTotalCostsFR) as TextView
        val txtOOPExpFR = view.findViewById<View>(R.id.txtOOPExpFR) as TextView
        val txtBuyerCostsFR = view.findViewById<View>(R.id.txtBuyerCostsFR) as TextView
        val txtGrossProfitFR = view.findViewById<View>(R.id.txtGrossProfitFR) as TextView
        val txtCapGainsFR = view.findViewById<View>(R.id.txtCapGainsFR) as TextView
        val txtNetProfitFR = view.findViewById<View>(R.id.txtNetProfitFR) as TextView
        val txtMoneyOutFR = view.findViewById<View>(R.id.txtMoneyOutFR) as TextView
        val txtMoneyInFR = view.findViewById<View>(R.id.txtMoneyInFR) as TextView
        val txtPercReturnFR = view.findViewById<View>(R.id.txtPercReturnFR) as TextView
        val txtCashCashRetFR = view.findViewById<View>(R.id.txtCashCashRetFR) as TextView

        spnTimeFrame!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                spnTimeFrame!!.setSelection(i, true)
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
                calC!!.setTimeFrameFactor(dTimeFrameFactor)

                tvMortPmt.setText(String.format("$%.0f", calC!!.getMortgage()!!.times(dTimeFrameFactor)))
                tvInsurance.setText(String.format("$%.0f", calC!!.getInsurance()!!.times(dTimeFrameFactor)))
                tvTaxes.setText(String.format("$%.0f", calC!!.getTaxes()!!.times(dTimeFrameFactor)))
                tvWater.setText(String.format("$%.0f", calC!!.getWater()!!.times(dTimeFrameFactor)))
                tvGas.setText(String.format("$%.0f", calC!!.getGas()!!.times(dTimeFrameFactor)))
                tvElectric.setText(String.format("$%.0f", calC!!.getElectric()!!.times(dTimeFrameFactor)))
                tvTotalExpenses.setText(String.format("$%.0f", calC!!.getTotalExpenses()!!.times(dTimeFrameFactor)))
                calC!!.setTotalCost(calC!!.getOfferBid(), calC!!.getBudget(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()))
                txtTotalCostsFR.setText(String.format("$%.0f", calC!!.getTotalCost()))

                if (calC!!.getFinance()!!.equals(2)) calC!!.setOOPExp(calC!!.getDownPayment(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()), 0.0)
                // if finance rehab flag is selected, set out of pocket expenses as follows
                else calC!!.setOOPExp(calC!!.getDownPayment(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()), calC!!.getBudget())

                txtOOPExpFR.setText(String.format("$%.0f", calC!!.getOOPExp()))
                calC!!.setTotalCost(calC!!.getOfferBid(), calC!!.getBudget(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()))
                calC!!.setGrossProfit(calC!!.getSellingPrice())
                calC!!.setCapGains()
                calC!!.setNetProfit()
                calC!!.setROI(calC!!.getSellingPrice())
                calC!!.setCashOnCash()
                txtBuyerCostsFR.setText(String.format("$%.0f", calC!!.getTotalCost()))
                txtGrossProfitFR.setText(String.format("$%.0f", calC!!.getGrossProfit()))
                txtCapGainsFR.setText(String.format("$%.0f", calC!!.getCapGains()))
                txtNetProfitFR.setText(String.format("$%.0f", calC!!.getNetProfit()))
                txtMoneyOutFR.setText(String.format("$%.0f", calC!!.getOOPExp()))
                txtMoneyInFR.setText(String.format("$%.0f", calC!!.getNetProfit()))
                val strPercReturn = String.format("%.1f", calC!!.getROI()) + "%"
                txtPercReturnFR.setText(strPercReturn)
                val strCashCashRet = String.format("%.1f", calC!!.getCashOnCash()) + "%"
                txtCashCashRetFR.setText(strCashCashRet)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // sometimes you need nothing here
            }
        }

        tvMortPmt.setEnabled(false)
        tvInsurance.setEnabled(false)
        tvTaxes.setEnabled(false)
        tvWater.setEnabled(false)
        tvGas.setEnabled(false)
        tvElectric.setEnabled(false)
        tvTotalExpenses.setEnabled(false)
        txtTotalCostsFR.setEnabled(false)
        txtOOPExpFR.setEnabled(false)
        txtBuyerCostsFR.setEnabled(false)
        txtGrossProfitFR.setEnabled(false)
        txtCapGainsFR.setEnabled(false)
        txtNetProfitFR.setEnabled(false)
        txtMoneyOutFR.setEnabled(false)
        txtMoneyInFR.setEnabled(false)
        txtPercReturnFR.setEnabled(false)
        txtCashCashRetFR.setEnabled(false)

        return view
    }
}
