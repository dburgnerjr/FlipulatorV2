package com.danielburgnerjr.flipulator

import java.io.File

import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class MainActivityFragment : Fragment(), View.OnClickListener {

    //val myDir = Environment.getDataDirectory().toString() + "/FlipulatorPremium"
    //val strPath = File(myDir)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val btnAbout: Button = view.findViewById(R.id.btnAbout)
        btnAbout.setOnClickListener(this)
        val btnCalculate: Button = view.findViewById(R.id.btnCalculate)
        btnCalculate.setOnClickListener(this)
        val btnDonate: Button = view.findViewById(R.id.btnDonate)
        btnDonate.setOnClickListener(this)
        val btnOpenFiles: Button = view.findViewById(R.id.btnOpenFiles)
        //Toast.makeText(getActivity(), myDir, Toast.LENGTH_SHORT).show()
        //val fFileArray = strPath.listFiles()
        //if (fFileArray == null) {
            btnOpenFiles!!.setVisibility(View.INVISIBLE)
        //} else {
          //  btnOpenFiles.setOnClickListener(this)
        //}
        val btnShare: Button = view.findViewById(R.id.btnShare)
        btnShare.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAbout -> {
                val intI = Intent(getActivity()?.getApplicationContext(), AboutActivity::class.java)
                startActivity(intI)
            }

            R.id.btnCalculate -> {
                val intI = Intent(getActivity()?.getApplicationContext(), LocationActivity::class.java)
                startActivity(intI)
            }

            R.id.btnDonate -> {
                val intI = Intent(getActivity()?.getApplicationContext(), DonateActivity::class.java)
                startActivity(intI)
            }

            R.id.btnOpenFiles -> {
                val intI = Intent(getActivity()?.getApplicationContext(), OpenFilesActivity::class.java)
                startActivity(intI)
            }

            R.id.btnShare -> {
                val strPackName: String = getResources().getString(R.string.package_name)
                val intI = Intent(Intent.ACTION_SEND)
                intI.setType("text/plain")
                intI.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Premium")
                var sAux = "\nLet me recommend you this application\n\n"
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + strPackName + "\n\n"
                intI.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(intI, "choose one"))
            }

            else -> {
            }
        }
    }
}
