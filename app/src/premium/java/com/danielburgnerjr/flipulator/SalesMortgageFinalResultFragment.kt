package com.danielburgnerjr.flipulator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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

        tvSalesPrice.setText(String.format("$%.0f", calC!!.getSalesPrice()))
        val strPercentDown = String.format("%.0f", calC!!.getPercentDown()) + "%"
        tvPercentDown.setText(strPercentDown)
        tvOfferBid.setText(String.format("$%.0f", calC!!.getOfferBid()))
        tvRehabBudget.setText(String.format("$%.0f", calC!!.getBudget()))
        tvRehabBudgetItems.setText(calC!!.getBudgetItems())
        tvDownPayment.setText(String.format("$%.0f", calC!!.getDownPayment()))
        val strInterestRate = String.format("%.0f", calC!!.getInterestRate()) + "%"
        tvInterestRate.setText(strInterestRate)
        tvTerm.setText(calC!!.getTerm().toString())
        tvMonthlyPmt.setText(String.format("$%.0f", calC!!.getMonthlyPmt()))

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

