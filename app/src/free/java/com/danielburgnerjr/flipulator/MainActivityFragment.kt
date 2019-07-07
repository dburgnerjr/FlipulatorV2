package com.danielburgnerjr.flipulator

import java.io.File

import android.support.v4.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivityFragment : Fragment(), View.OnClickListener {

    //private val myDir = File(getActivity()?.getApplicationContext()?.getExternalFilesDir(null)?.toString() + "/FlipulatorFree")
    //private val strPath = File(myDir)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val strPath = getArguments()!!.getString("path")

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        MobileAds.initialize(getActivity(), getString(R.string.admob_app_id))
        val mAdView = view.findViewById(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val btnAbout: Button = view.findViewById(R.id.btnAbout)
        btnAbout.setOnClickListener(this)
        val btnCalculate: Button = view.findViewById(R.id.btnCalculate)
        btnCalculate.setOnClickListener(this)
        val btnDonate: Button = view.findViewById(R.id.btnDonate)
        btnDonate.setOnClickListener(this)
        val btnOpenFiles: Button = view.findViewById(R.id.btnOpenFiles)
        Toast.makeText(getActivity()?.getApplicationContext(), strPath, Toast.LENGTH_SHORT).show()
        //val fFileArray = myDir.listFiles()
        //if (fFileArray == null) {
            btnOpenFiles.setVisibility(View.INVISIBLE)
        //} else {
            //btnOpenFiles.setOnClickListener(this)
        //}
        val btnShare: Button = view.findViewById(R.id.btnShare)
        btnShare.setOnClickListener(this)
        val btnUpgrade: Button = view.findViewById(R.id.btnUpgrade)
        btnUpgrade.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAbout -> {
                val intI = Intent(getActivity()?.getApplicationContext(), AboutActivity::class.java)
                startActivity(intI)
            }

            R.id.btnCalculate -> {
                val intI = Intent(getActivity()?.getApplicationContext(), CalculateActivity::class.java)
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
                intI.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Free")
                var sAux = "\nLet me recommend you this application\n\n"
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + strPackName + "\n\n"
                intI.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(intI, "choose one"))
            }

            R.id.btnUpgrade -> {
                val uri = Uri.parse(getResources().getString(R.string.market_premium))
                val newActivity = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(newActivity)
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_premium))))
                }
            }

            else -> {
            }
        }
    }
}
