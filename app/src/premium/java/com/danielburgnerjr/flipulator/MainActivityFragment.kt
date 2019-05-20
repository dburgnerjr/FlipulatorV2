package com.danielburgnerjr.flipulator

import java.io.File
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import android.content.Context
import android.content.DialogInterface
import android.content.Intent

class MainActivityFragment : Activity() {
    internal var strPackName: String
    private val setS: Settings? = null
    private var btnOpenFiles: Button? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        strPackName = getApplicationContext().getPackageName()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDir = File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium")
        val strPath = myDir.getPath()
        RateThisApp.onLaunch(this)
        //Toast.makeText(getApplicationContext(), strPath, Toast.LENGTH_SHORT).show();

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
                val intL = Intent(this@MainActivityFragment, SettingsActivity::class.java)
                startActivity(intL)
                finish()
            }
        })

        val btnDonate = findViewById(R.id.btnDonate) as Button
        btnDonate.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(this@MainActivityFragment, DonateActivity::class.java)
                startActivity(intI)
            }
        })

        val btnShare = findViewById(R.id.btnShare) as Button
        btnShare.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View) {
                val intI = Intent(Intent.ACTION_SEND)
                intI.setType("text/plain")
                intI.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Premium")
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
                val intI = Intent(this@MainActivityFragment, OpenFilesActivity::class.java)
                startActivity(intI)
                finish()
            }
        })


    }

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    protected fun exitByBackKey() {
        val adAlertBox = AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    fun onClick(arg0: DialogInterface, arg1: Int) {
                        finish()
                        //close();
                    }
                })
                .setNegativeButton("No", object : DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    fun onClick(arg0: DialogInterface, arg1: Int) {}
                })
                .show()
    }

}
