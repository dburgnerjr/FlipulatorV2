package com.danielburgnerjr.flipulator

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

                var dMortgage = calC!!.getMortgage()!!.times(dTimeFrameFactor)
                tvMortPmt.setText(dMortgage.toString())
                var dInsurance = calC!!.getInsurance()!!.times(dTimeFrameFactor)
                tvInsurance.setText(dInsurance.toString())
                var dTaxes = calC!!.getTaxes()!!.times(dTimeFrameFactor)
                tvTaxes.setText(dTaxes.toString())
                var dWater = calC!!.getWater()!!.times(dTimeFrameFactor)
                tvWater.setText(dWater.toString())
                var dGas = calC!!.getGas()!!.times(dTimeFrameFactor)
                tvGas.setText(dGas.toString())
                var dElectric = calC!!.getElectric()!!.times(dTimeFrameFactor)
                tvElectric.setText(dElectric.toString())
                var dTotalExpenses = calC!!.getTotalExpenses()!!.times(dTimeFrameFactor)
                tvTotalExpenses.setText(dTotalExpenses.toString())
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

        return view
    }
}

