package com.danielburgnerjr.flipulator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.danielburgnerjr.flipulator.model.Calculate

class SalesMortgageFinalResultFragment : Fragment() {

    private var calC: Calculate? = null
    private var intI: Intent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_sales_mortgage_final_result, container, false)
        intI = getActivity()!!.getIntent()

        calC = intI!!.getSerializableExtra("Calculate") as Calculate

        val tvSalesPrice = view.findViewById<View>(R.id.txtSalePriceFR) as TextView
        val tvPercentDown = view.findViewById<View>(R.id.txtPercentDownFR) as TextView
        val tvOfferBid = view.findViewById<View>(R.id.txtOfferBidFR) as TextView
        val tvRehabBudget = view.findViewById<View>(R.id.txtRehabBudgetFR) as TextView
        val tvRehabBudgetItems = view.findViewById<View>(R.id.txtBudgetItemsFR) as TextView
        val tvDownPayment = view.findViewById<View>(R.id.txtDownPayment) as TextView
        val tvInterestRate = view.findViewById<View>(R.id.txtInterestRateFR) as TextView
        val tvTerm = view.findViewById<View>(R.id.txtTermFR) as TextView
        val tvMonthlyPmt = view.findViewById<View>(R.id.txtMonthlyPmt) as TextView

        tvSalesPrice.setText(calC!!.getSalesPrice().toString())
        tvPercentDown.setText(calC!!.getPercentDown().toString())
        tvOfferBid.setText(calC!!.getOfferBid().toString())
        tvRehabBudget.setText(calC!!.getBudget().toString())
        tvRehabBudgetItems.setText(calC!!.getBudgetItems())
        tvDownPayment.setText(calC!!.getDownPayment().toString())
        tvInterestRate.setText(calC!!.getInterestRate().toString())
        tvTerm.setText(calC!!.getTerm().toString())
        tvMonthlyPmt.setText(calC!!.getMonthlyPmt().toString())

        tvSalesPrice.setEnabled(false)
        tvPercentDown.setEnabled(false)
        tvOfferBid.setEnabled(false)
        tvRehabBudget.setEnabled(false)
        tvRehabBudgetItems.setEnabled(false)
        tvDownPayment.setEnabled(false)
        tvInterestRate.setEnabled(false)
        tvTerm.setEnabled(false)
        tvMonthlyPmt.setEnabled(false)

        return view
    }
}

