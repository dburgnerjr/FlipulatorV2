package com.danielburgnerjr.flipulator

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Visibility
import android.util.Log;
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

class DonateActivity : AppCompatActivity(), PurchasesUpdatedListener {

    private val TAG = "InAppBilling"

    var mSharedPreferences: SharedPreferences? = null
    private val CATALOG_DEBUG = arrayOf("android.test.purchased", "android.test.canceled", "android.test.refunded", "android.test.item_unavailable")
    private val GOOGLE_CATALOG = arrayOf("donation1", "donation5", "donation10", "donation25", "donation50", "donation100")

    lateinit private var mBillingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_activity)

        val mGoogleSpinner: Spinner = findViewById(R.id.spnDonate)
        val strOptions = resources.getStringArray(R.array.donation_google_catalog_values)
        var strSelectedOption: String = ""
        var strPosition: String = ""

        val arAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strOptions)

        mGoogleSpinner.adapter = arAdapter
        mGoogleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                strSelectedOption = parent.getItemAtPosition(position).toString();
                strPosition = position.toString();
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Establish connection to billing client
        mBillingClient = BillingClient.newBuilder(this@DonateActivity).setListener(this).build()
        mBillingClient.startConnection(object : BillingClientStateListener {
            fun onBillingSetupFinished(@BillingClient.BillingResponseCode billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponseCode.OK) {
                    // The billing client is ready. You can query purchases here.

                }
            }

            override fun onBillingServiceDisconnected() {
                //TODO implement your own retry policy
                //Toast.makeText(this@DonateActivity, resources.getString(R.string.billing_connection_failure), Toast.LENGTH_SHORT)
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })

        val btnDonateNow: Button = findViewById(R.id.btnDonateNow)
        btnDonateNow.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                Toast.makeText(this@DonateActivity, "Thank you for your donation of: " + strSelectedOption + " " + strPosition, Toast.LENGTH_SHORT).show()
                val flowParams = BillingFlowParams.newBuilder()
                        .setSku(GOOGLE_CATALOG[Integer.parseInt(strPosition)])
                        .setType(BillingClient.SkuType.INAPP)
                        .build()
                val responseCode = mBillingClient.launchBillingFlow(this@DonateActivity, flowParams)
            }
        });

        val txtPayPalTitle: TextView = findViewById(R.id.txtPaypalTitle)
        val txtPayPalDescription: TextView = findViewById(R.id.txtPaypalDescription)
        val btnPayPal: Button = findViewById(R.id.btnDonatePaypal)
        btnPayPal.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Handler code here.
                val strPaypal = "http://www.paypal.me/dburgnerjr"
                val newActivity = Intent(Intent.ACTION_VIEW, Uri.parse(strPaypal))
                startActivity(newActivity)
            }
        });

        txtPayPalTitle.visibility = View.GONE
        txtPayPalDescription.visibility = View.GONE
        btnPayPal.visibility = View.GONE

/*


        mBuyButton = findViewById<View>(R.id.buyButton) as Button

        mBuyButton.setOnClickListener(View.OnClickListener {
            // If user clicks the buy button, launch the billing flow for an ad removal  purchase
            // Response is handled using onPurchasesUpdated listener
            val flowParams = BillingFlowParams.newBuilder()
                    .setSku(ITEM_SKU_ADREMOVAL)
                    .setType(BillingClient.SkuType.INAPP)
                    .build()
            val responseCode = mBillingClient.launchBillingFlow(this@DonateActivity, flowParams)
        })

        // Query purchases incase a user is connecting from a different device and they've already purchased the app
        queryPurchases()
        queryPrefPurchases()
*/
    }

    fun onPurchasesUpdated(responseCode: Int, @Nullable List<com.android.billingclient.api.Purchase> purchases) {

        //Handle the responseCode for the purchase
        //If response code is OK,  handle the purchase
        //If user already owns the item, then indicate in the shared prefs that item is owned
        //If cancelled/other code, log the error

        if (responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Log.d(TAG, "User Canceled" + responseCode);
        } else {
            Log.d(TAG, "Other code" + responseCode);
            // Handle any other error codes.
        }

    }
}