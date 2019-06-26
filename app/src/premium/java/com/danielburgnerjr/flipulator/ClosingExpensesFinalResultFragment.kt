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

class ClosingExpensesFinalResultFragment : Fragment() {

    private var calC: Calculate? = null
    private var intI: Intent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_closing_expenses_final_result, container, false)
        intI = getActivity()!!.getIntent()

        calC = intI!!.getSerializableExtra("Calculate") as Calculate

        val txtRealEstComm = view.findViewById<View>(R.id.txtRECommFR) as TextView
        val txtRealEstCommPer = view.findViewById<View>(R.id.txtRECommPercFR) as TextView
        val txtBuyerClosCost = view.findViewById<View>(R.id.txtBuyerClosCostFR) as TextView
        val txtBuyerClosCostPer = view.findViewById<View>(R.id.txtBuyerClosCostPerFR) as TextView
        val txtSellerClosCost = view.findViewById<View>(R.id.txtSellerClosCostFR) as TextView
        val txtSellerClosCostPer = view.findViewById<View>(R.id.txtSellerClosCostPerFR) as TextView
        val txtTotalCostsFR = view.findViewById<View>(R.id.txtTotalCostsFR) as TextView
        val txtOOPExpFR = view.findViewById<View>(R.id.txtOOPExpFR) as TextView

        calC!!.setRECost(calC!!.getSellingPrice(), calC!!.getRealEstComm())
        txtRealEstComm.setText(calC!!.getRECost().toString())
        txtRealEstCommPer.setText(calC!!.getRealEstComm().toString())
        calC!!.setBCCost(calC!!.getSalesPrice(), calC!!.getBuyClosCost())
        txtBuyerClosCost.setText(calC!!.getBCCost().toString())
        txtBuyerClosCostPer.setText(calC!!.getBuyClosCost().toString())
        calC!!.setSCCost(calC!!.getSalesPrice(), calC!!.getSellClosCost())
        txtSellerClosCost.setText(calC!!.getSCCost().toString())
        txtSellerClosCostPer.setText(calC!!.getSellClosCost().toString())
        calC!!.setTotalCost(calC!!.getOfferBid(), calC!!.getBudget(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()))
        txtTotalCostsFR.setText(calC!!.getTotalCost().toString())
        if (calC!!.getFinance() !== 2) {
            calC!!.setOOPExp(calC!!.getDownPayment(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()), calC!!.getBudget())
        }
        // if finance rehab flag is selected, set out of pocket expenses as follows
        if (calC!!.getFinance() === 2) {
            calC!!.setOOPExp(calC!!.getDownPayment(), calC!!.getTotalExpenses()!!.times(calC!!.getTimeFrameFactor()), 0.0)
        }
        txtOOPExpFR.setText(calC!!.getOOPExp().toString())

        txtRealEstComm.setEnabled(false)
        txtRealEstCommPer.setEnabled(false)
        txtBuyerClosCost.setEnabled(false)
        txtBuyerClosCostPer.setEnabled(false)
        txtTotalCostsFR.setEnabled(false)
        txtOOPExpFR.setEnabled(false)

        return view
    }
}
