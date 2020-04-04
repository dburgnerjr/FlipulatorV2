package com.danielburgnerjr.flipulator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.danielburgnerjr.flipulator.model.Calculate

class LocationFinalResultFragment : Fragment() {

    private var calC: Calculate? = null
    private var intI: Intent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_location_fin_result, container, false)
        intI = activity!!.intent

        calC = intI!!.getSerializableExtra("Calculate") as Calculate

        val tvFinanceValue = view.findViewById<View>(R.id.txtFinanceType) as TextView
        tvFinanceValue.text = calC!!.getFinanceValue()
        tvFinanceValue.isEnabled = false

        val tvFRAddress = view.findViewById<View>(R.id.txtAddressFR) as TextView
        val tvFRCityStZip = view.findViewById<View>(R.id.txtCityStZip) as TextView
        val tvSF = view.findViewById<View>(R.id.txtSq_FootageFR) as TextView
        val tvBedBath = view.findViewById<View>(R.id.txtBedroomsBathrooms) as TextView

        tvFRAddress.text = calC!!.getAddress()
        val strCityStZip = calC!!.getCity() + ", " + calC!!.getState() + " " + calC!!.getZipCode()
        tvFRCityStZip.text = strCityStZip
        tvSF.text = calC!!.getSquareFootage().toString()
        val strBedBath = calC!!.getBedrooms().toString() + " BR/" + calC!!.getBathrooms().toString() + " BA"
        tvBedBath.text = strBedBath
        tvFRAddress.isEnabled = false
        tvFRCityStZip.isEnabled = false
        tvSF.isEnabled = false
        tvBedBath.isEnabled = false

        return view
    }
}
