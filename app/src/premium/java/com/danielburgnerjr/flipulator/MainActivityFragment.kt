package com.danielburgnerjr.flipulator

import java.io.File

import android.support.v4.app.Fragment
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_main.*;

class MainActivityFragment : Fragment(), View.OnClickListener {

    private var btnOpenFiles: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val btnAbout: Button = view.findViewById(R.id.btnAbout)
        btnAbout.setOnClickListener(this)
        val btnShare: Button = view.findViewById(R.id.btnShare)
        btnShare.setOnClickListener(this)
        val btnDonate: Button = view.findViewById(R.id.btnDonate)
        btnDonate.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAbout -> {
                val intI = Intent(getActivity()?.getApplicationContext(), AboutActivity::class.java)
                startActivity(intI)
            }

            R.id.btnDonate -> {
                val intI = Intent(getActivity()?.getApplicationContext(), DonateActivity::class.java)
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

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val mAdView = findViewById(R.id.adView) as AdView
        //val adRequest = AdRequest.Builder().build()
        //mAdView.loadAd(adRequest)
        //val myDir = File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorFree")
        //val strPath = myDir.getPath()
        //RateThisApp.onLaunch(this)
        val btnAbout = findViewById(R.id.btnAbout) as Button
        btnAbout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intI = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intI)
            }
        })
        val btnCalculate = findViewById(R.id.btnCalculate) as Button
        btnCalculate.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(this@MainActivity, CalculateActivity::class.java)
                startActivity(intI)
                finish()
            }
        })
        val btnUpgrade = findViewById(R.id.btnUpgrade) as Button
        btnUpgrade.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val uri = Uri.parse(getResources().getString(R.string.market_premium))
                val newActivity = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(newActivity)
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_store_premium))))
                }
            }
        })
        val btnDonate = findViewById(R.id.btnDonate) as Button
        btnDonate.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(this@MainActivity, DonateActivity::class.java)
                startActivity(intI)
            }
        })
        val btnShare = findViewById(R.id.btnShare) as Button
        btnShare.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(Intent.ACTION_SEND)
                intI.setType("text/plain")
                intI.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Free")
                var sAux = "\nLet me recommend you this application\n\n"
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + strPackName + "\n\n"
                intI.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(intI, "choose one"))
            }
        })
        btnOpenFiles = findViewById(R.id.btnOpenFiles) as Button
        val fFileList = File(strPath)
        val fFileArray = fFileList.listFiles()
        if (fFileArray == null) {
            btnOpenFiles!!.setVisibility(View.INVISIBLE)
        }
        btnOpenFiles!!.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(this@MainActivity, OpenFilesActivity::class.java)
                startActivity(intI)
                finish()
            }
        })
    }
*/
}
