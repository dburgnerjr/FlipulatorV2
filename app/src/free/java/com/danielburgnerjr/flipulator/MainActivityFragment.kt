package com.danielburgnerjr.flipulator

import java.io.File

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView

class MainActivityFragment : Activity() {

    protected var strPackName: String? = null
    private var btnOpenFiles: Button? = null

    override fun onCreate(savedInstanceState: Bundle) {
        strPackName = getApplicationContext().getPackageName()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

/*
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
*/

    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }

    protected fun exitByBackKey() {
        val adAlertBox = AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    // do something when the button is clicked
                    override fun onClick(arg0: DialogInterface, arg1: Int) {
                        finish()
                        //close();
                    }
                })
                .setNegativeButton("No", object : DialogInterface.OnClickListener {
                    // do something when the button is clicked
                    override fun onClick(arg0: DialogInterface, arg1: Int) {}
                })
                .show()
    }
}
