package com.danielburgnerjr.flipulator

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.KeyEvent
import android.widget.Toast

import java.io.File

class MainActivity : FragmentActivity() {
    private var strPackName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDir = File(applicationContext?.getExternalFilesDir(null)?.toString() + "/")
        // below line displays the path
        Toast.makeText(applicationContext, "MainActivity: $myDir", Toast.LENGTH_LONG).show()

        val bundle = Bundle()
        bundle.putString("path", myDir.toString())
        // set MainActivityFragment Arguments
        val fragobj = MainActivityFragment()
        fragobj.arguments = bundle

        val manager = supportFragmentManager

        // Begin the fragment transition using support fragment manager
        val transaction = manager.beginTransaction()

        // add the fragment on container
        transaction.add(R.id.main_fragment, fragobj)

        // Finishing the transition
        transaction.commit()

        strPackName = applicationContext.packageName
    }

    override fun onKeyDown(nKeyCode: Int, keEvent: KeyEvent): Boolean {
        if (nKeyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(nKeyCode, keEvent)
    }

    private fun exitByBackKey() {
        val adAlertBox = AlertDialog.Builder(this).create()
        adAlertBox.setMessage("Do you want to exit application?")
        adAlertBox.setButton(DialogInterface.BUTTON_POSITIVE, "Yes") { _, _ ->
            // do something when the button is clicked
            finish()
        }
        adAlertBox.setButton(DialogInterface.BUTTON_NEGATIVE, "No" ) { _, _ -> }
        adAlertBox.show()
    }
}
