package com.danielburgnerjr.flipulator

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class DonateActivity : Activity() {

    private var mGoogleSpinner: Spinner? = null
    private var btnDonateNow: Button? = null                // donate google
    private var btnPayPal: Button? = null                    // donate paypal
    private var txtPayPalTitle: TextView? = null            // Paypal title
    private var txtPayPalDesc: TextView? = null                // Paypal description

    // Google Play helper object
    private var mHelper: IabHelper? = null

    protected var mDebug = false

    protected var mGoogleEnabled = false
    protected var mGooglePubkey = ""
    protected var mGoogleCatalog = arrayOf<String>()
    protected var mGoogleCatalogValues = arrayOf<String>()

    protected var mPaypalEnabled = false
    protected var mPaypalUser = ""
    protected var mPaypalCurrencyCode = ""
    protected var mPaypalItemName = ""

    // Callback for when a purchase is finished
    internal var mPurchaseFinishedListener: IabHelper.OnIabPurchaseFinishedListener = object : IabHelper.OnIabPurchaseFinishedListener {
        override fun onIabPurchaseFinished(result: IabResult?, purchase: Purchase?) {
            if (mDebug)
                Log.d(TAG, "Purchase finished: $result, purchase: $purchase")

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return

            if (result.isSuccess) {
                if (mDebug)
                    Log.d(TAG, "Purchase successful.")

                // directly consume in-app purchase, so that people can donate multiple times
                mHelper!!.consumeAsync(purchase, mConsumeFinishedListener)

                // show thanks openDialog
                openDialog(android.R.drawable.ic_dialog_info, R.string.donations__thanks_dialog_title,
                        getString(R.string.donations__thanks_dialog))
            }
        }
    }

    // Called when consumption is complete
    internal var mConsumeFinishedListener: IabHelper.OnConsumeFinishedListener = object : IabHelper.OnConsumeFinishedListener {
        override fun onConsumeFinished(purchase: Purchase, result: IabResult) {
            if (mDebug)
                Log.d(TAG, "Consumption finished. Purchase: $purchase, result: $result")

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return

            if (result.isSuccess) {
                if (mDebug)
                    Log.d(TAG, "Consumption successful. Provisioning.")
            }
            if (mDebug)
                Log.d(TAG, "End consumption flow.")
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_activity)

        mGooglePubkey = GOOGLE_PUBKEY
        mGoogleCatalog = GOOGLE_CATALOG
        mGoogleCatalogValues = resources.getStringArray(R.array.donation_google_catalog_values)

        txtPayPalTitle = findViewById(R.id.txtPaypalTitle) as TextView
        txtPayPalDesc = findViewById(R.id.txtPaypalDescription) as TextView

        // choose donation amount
        mGoogleSpinner = findViewById(R.id.spnDonate) as Spinner
        val adapter: ArrayAdapter<CharSequence>
        if (mDebug) {
            adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, CATALOG_DEBUG)
        } else {
            adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mGoogleCatalogValues)
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mGoogleSpinner!!.adapter = adapter
        btnDonateNow = findViewById(R.id.btnDonateNow) as Button

        btnDonateNow!!.setOnClickListener { v -> donateGoogleOnClick(v) }

        // Create the helper, passing it our context and the public key to verify signatures with
        if (mDebug)
            Log.d(TAG, "Creating IAB helper.")
        mHelper = IabHelper(this, mGooglePubkey)

        // enable debug logging (for a production application, you should set this to false).
        mHelper!!.enableDebugLogging(mDebug)

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        if (mDebug)
            Log.d(TAG, "Starting setup.")
        mHelper!!.startSetup(object : IabHelper.OnIabSetupFinishedListener {
            override fun onIabSetupFinished(result: IabResult) {
                if (mDebug)
                    Log.d(TAG, "Setup finished.")

                if (!result.isSuccess) {
                    // Oh noes, there was a problem.
                    openDialog(android.R.drawable.ic_dialog_alert, R.string.donations__google_android_market_not_supported_title,
                            getString(R.string.donations__google_android_market_not_supported))
                    return
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return
            }
        })

        btnPayPal = findViewById(R.id.btnDonatePaypal) as Button

        // set PayPal invisible for Google Play
        txtPayPalTitle!!.visibility = View.INVISIBLE
        txtPayPalDesc!!.visibility = View.INVISIBLE
        btnPayPal!!.visibility = View.INVISIBLE

        btnPayPal!!.setOnClickListener { v -> donatePayPalOnClick(v) }

    }

    /**
     * Open dialog
     */
    internal fun openDialog(icon: Int, title: Int, message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setIcon(icon)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setCancelable(true)
        dialog.setNeutralButton(R.string.donations__button_close
        ) { dialog, which -> dialog.dismiss() }
        dialog.show()
    }

    /**
     * Donate button executes donations based on selection in spinner
     */
    fun donateGoogleOnClick(view: View) {
        val index: Int
        index = mGoogleSpinner!!.selectedItemPosition
        if (mDebug)
            Log.d(TAG, "selected item in spinner: $index")

        if (mDebug) {
            // when debugging, choose android.test.x item
            //Toast.makeText(getApplicationContext(), CATALOG_DEBUG[index], Toast.LENGTH_LONG).show();
            mHelper!!.launchPurchaseFlow(this,
                    CATALOG_DEBUG[index], IabHelper.ITEM_TYPE_INAPP,
                    0, mPurchaseFinishedListener, null)
        } else {
            //Toast.makeText(getApplicationContext(), mGoogleCatalog[index], Toast.LENGTH_LONG).show();
            mHelper!!.launchPurchaseFlow(this,
                    mGoogleCatalog[index], IabHelper.ITEM_TYPE_INAPP,
                    0, mPurchaseFinishedListener, null)
        }
    }

    /**
     * Donate Paypal button executes link to Paypal donation page
     */
    fun donatePayPalOnClick(view: View) {
        val strPaypal = "http://www.paypal.me/dburgnerjr"
        val newActivity = Intent(Intent.ACTION_VIEW, Uri.parse(strPaypal))
        startActivity(newActivity)

    }

    /**
     * Needed for Google Play In-app Billing. It uses startIntentSenderForResult(). The result is not propagated to
     * the Fragment like in startActivityForResult(). Thus we need to propagate manually to our Fragment.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (mDebug)
            Log.d(TAG, "onActivityResult($requestCode,$resultCode,$data")
        if (mHelper == null) return

        // Pass on the fragment result to the helper for handling
        if (!mHelper!!.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            if (mDebug)
                Log.d(TAG, "onActivityResult handled by IABUtil.")
        }
    }

    companion object {

        private val TAG = "Donations Library"

        private val CATALOG_DEBUG = arrayOf("android.test.purchased", "android.test.canceled", "android.test.refunded", "android.test.item_unavailable")
        private val GOOGLE_CATALOG = arrayOf("donation1", "donation5", "donation10", "donation25", "donation50", "donation100")
        /**
         * Google
         */
        private val GOOGLE_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhUoMs4c4RcEIRckCir+wNCDZk8Zy3I4w7UjrPvKWIh8tl71HrEE+6BQiBCxseGfpuVEZntaHhzyQj5gMLSI5JBRboOlpItj7SyvupoHszsSh28VdJQiD3AWXB1LNeS9Z6W9RffSKYfEKs8v+dMwqzi+C2M+fPg9o7IcAXsCnJrVqS+vIhYrfyVX4oG3DQ28wfcWVPgGnNLP82y5VaP+xlJYdBZBQJrDBlQq1QaecSiR+wRG8ZBMv5V1x6w/QM4yKhoolz2Pc6Zt8YVkCVpQhf8usGcAy0d6ysW5YlkhFz2PbVoi553OkH3T5lZ5LO3cFXYG0g1ttsfE/8WiPYRlGxwIDAQAB"
    }
}
