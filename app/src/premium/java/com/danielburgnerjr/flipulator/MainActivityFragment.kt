package com.danielburgnerjr.flipulator

import java.io.File

import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class MainActivityFragment : Fragment(), View.OnClickListener {
    private var strPath: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        strPath.let { arguments?.getString("path") }    // path is not showing up at all
        val myDir = File(strPath)

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val btnAbout: Button = view.findViewById(R.id.btnAbout)
        btnAbout.setOnClickListener(this)
        val btnCalculate: Button = view.findViewById(R.id.btnCalculate)
        btnCalculate.setOnClickListener(this)
        val btnDonate: Button = view.findViewById(R.id.btnDonate)
        btnDonate.setOnClickListener(this)
        val btnOpenFiles: Button = view.findViewById(R.id.btnOpenFiles)
        Toast.makeText(activity?.applicationContext, "MainActivityFragment: $strPath", Toast.LENGTH_LONG).show()
/*
        val fFileArray = myDir.listFiles()
        if (fFileArray == null) {
*/
            btnOpenFiles.visibility = View.INVISIBLE
/*
        } else {
            btnOpenFiles.setOnClickListener(this)
        }
*/
        val btnShare: Button = view.findViewById(R.id.btnShare)
        btnShare.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.btnAbout -> {
            val intI = Intent(activity?.applicationContext, AboutActivity::class.java)
            startActivity(intI)
        }

        R.id.btnCalculate -> {
            val intI = Intent(activity?.applicationContext, LocationActivity::class.java)
            startActivity(intI)
        }

        R.id.btnDonate -> {
            val intI = Intent(activity?.applicationContext, DonateActivity::class.java)
            startActivity(intI)
        }

        R.id.btnOpenFiles -> {
            val intI = Intent(activity?.applicationContext, OpenFilesActivity::class.java)
            startActivity(intI)
        }

        R.id.btnShare -> {
            val strPackName: String = resources.getString(R.string.package_name)
            val intI = Intent(Intent.ACTION_SEND)
            intI.type = "text/plain"
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
