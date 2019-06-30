package com.danielburgnerjr.flipulator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val txtFMVARVFR = view.findViewById<View>(R.id.txtFMVARVFR) as TextView
        val txtComparablesFR = view.findViewById<View>(R.id.txtComparablesFR) as TextView
        val txtSellingPriceFR = view.findViewById<View>(R.id.txtSellingPriceFR) as TextView

        calC!!.setRECost(calC!!.getSellingPrice(), calC!!.getRealEstComm())
        var strRealEstComm = String.format("$%.0f", calC!!.getRECost())
        txtRealEstComm.setText(strRealEstComm)
        var strRealEstCommPer = String.format("%.0f", calC!!.getRealEstComm()) + "%"
        txtRealEstCommPer.setText(strRealEstCommPer)
        calC!!.setBCCost(calC!!.getSalesPrice(), calC!!.getBuyClosCost())
        var strBuyerClosCost = String.format("$%.0f", calC!!.getBCCost())
        txtBuyerClosCost.setText(strBuyerClosCost)
        var strBuyerClosCostPer = String.format("%.0f", calC!!.getBuyClosCost()) + "%"
        txtBuyerClosCostPer.setText(strBuyerClosCostPer)
        calC!!.setSCCost(calC!!.getSalesPrice(), calC!!.getSellClosCost())
        var strSellerClosCost = String.format("$%.0f", calC!!.getSCCost())
        txtSellerClosCost.setText(strSellerClosCost)
        var strSellerClosCostPer = String.format("%.0f", calC!!.getSellClosCost() + "%"
        txtSellerClosCostPer.setText(strSellerClosCostPer)
        var strFMVARV = String.format("$%.0f", calC!!.getFMVARV())
        txtFMVARVFR.setText(strFMVARV)
        var strComparables = String.format("$%.0f", calC!!.getComparables())
        txtComparablesFR.setText(strComparables)
        var strSellingPrice = String.format("$%.0f", calC!!.getSellingPrice())
        txtSellingPriceFR.setText(strSellingPrice)

        txtRealEstComm.setEnabled(false)
        txtRealEstCommPer.setEnabled(false)
        txtBuyerClosCost.setEnabled(false)
        txtBuyerClosCostPer.setEnabled(false)
        txtSellerClosCost.setEnabled(false)
        txtSellerClosCostPer.setEnabled(false)
        txtFMVARVFR.setEnabled(false)
        txtComparablesFR.setEnabled(false)
        txtSellingPriceFR.setEnabled(false)

        return view
    }
}
