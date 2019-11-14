package com.danielburgnerjr.flipulator;

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import java.io.File

class MainActivity : FragmentActivity() {
    protected var strPackName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDir = File(getApplicationContext()?.getExternalFilesDir(null)?.toString() + "/")
        // below line displays the path
        Toast.makeText(applicationContext, "MainActivity: " + myDir.toString(), Toast.LENGTH_LONG).show()

        val bundle = Bundle()
        bundle.putString("path", myDir.toString())
        // set MainActivityFragment Arguments
        val fragobj = MainActivityFragment()
        fragobj.setArguments(bundle)

        val manager = supportFragmentManager

        // Begin the fragment transition using support fragment manager
        val transaction = manager.beginTransaction()

        // add the fragment on container
        transaction.add(R.id.main_fragment, fragobj)

        // Finishing the transition
        transaction.commit()

        strPackName = getApplicationContext().getPackageName()
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
