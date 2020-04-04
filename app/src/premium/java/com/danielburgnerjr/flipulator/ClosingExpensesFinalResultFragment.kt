package com.danielburgnerjr.flipulator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
        intI = activity!!.intent

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
        txtRealEstComm.text = String.format("$%.0f", calC!!.getRECost())
        val strRealEstCommPer = String.format("%.0f", calC!!.getRealEstComm()) + "%"
        txtRealEstCommPer.text = strRealEstCommPer
        calC!!.setBCCost(calC!!.getSalesPrice(), calC!!.getBuyClosCost())
        txtBuyerClosCost.text = String.format("$%.0f", calC!!.getBCCost())
        val strBuyerClosCostPer = String.format("%.0f", calC!!.getBuyClosCost()) + "%"
        txtBuyerClosCostPer.text = strBuyerClosCostPer
        calC!!.setSCCost(calC!!.getSalesPrice(), calC!!.getSellClosCost())
        txtSellerClosCost.text = String.format("$%.0f", calC!!.getSCCost())
        val strSellerClosCostPer = String.format("%.0f", calC!!.getSellClosCost()) + "%"
        txtSellerClosCostPer.text = strSellerClosCostPer
        txtFMVARVFR.text = String.format("$%.0f", calC!!.getFMVARV())
        txtComparablesFR.text = String.format("$%.0f", calC!!.getComparables())
        txtSellingPriceFR.text = String.format("$%.0f", calC!!.getSellingPrice())

        txtRealEstComm.isEnabled = false
        txtRealEstCommPer.isEnabled = false
        txtBuyerClosCost.isEnabled = false
        txtBuyerClosCostPer.isEnabled = false
        txtSellerClosCost.isEnabled = false
        txtSellerClosCostPer.isEnabled = false
        txtFMVARVFR.isEnabled = false
        txtComparablesFR.isEnabled = false
        txtSellingPriceFR.isEnabled = false

        return view
    }
}
