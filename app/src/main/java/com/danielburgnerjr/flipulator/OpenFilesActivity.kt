package com.danielburgnerjr.flipulator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent

class OpenFilesActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_files_activity)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intI = Intent(this@OpenFilesActivity, MainActivity::class.java)
            startActivity(intI)
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
