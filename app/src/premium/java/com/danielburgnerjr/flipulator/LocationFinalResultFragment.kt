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
        intI = getActivity()!!.getIntent()

        calC = intI!!.getSerializableExtra("Calculate") as Calculate

        val tvFinanceValue = view.findViewById<View>(R.id.txtFinanceType) as TextView
        tvFinanceValue.setText(calC!!.getFinanceValue())
        tvFinanceValue.setEnabled(false)

        val tvFRAddress = view.findViewById<View>(R.id.txtAddressFR) as TextView
        val tvFRCityStZip = view.findViewById<View>(R.id.txtCityStZip) as TextView
        val tvSF = view.findViewById<View>(R.id.txtSq_FootageFR) as TextView
        val tvBedBath = view.findViewById<View>(R.id.txtBedroomsBathrooms) as TextView

        tvFRAddress.setText(calC!!.getAddress())
        tvFRCityStZip.setText(calC!!.getCity() + ", " + calC!!.getState() + " " + calC!!.getZipCode())
        tvSF.setText(calC!!.getSquareFootage().toString())
        tvBedBath.setText(calC!!.getBedrooms().toString() + " BR/" + calC!!.getBathrooms().toString() + " BA")
        tvFRAddress.setEnabled(false)
        tvFRCityStZip.setEnabled(false)
        tvSF.setEnabled(false)
        tvBedBath.setEnabled(false)

        return view
    }
}
