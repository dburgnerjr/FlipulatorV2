package com.danielburgnerjr.flipulator

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

class DonateActivity : AppCompatActivity() {

    var mBuyButton: Button? = null
    var mAdRemovalPrice: String? = null
    var mSharedPreferences: SharedPreferences? = null

    lateinit private var mBillingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_activity)

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Establish connection to billing client
        mBillingClient = BillingClient.newBuilder(this@DonateActivity).setListener(this).build()
        mBillingClient.startConnection(object : BillingClientStateListener {
            fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.

                }
            }

            override fun onBillingServiceDisconnected() {
                //TODO implement your own retry policy
                Toast.makeText(this@DonateActivity, resources.getString(R.string.billing_connection_failure), Toast.LENGTH_SHORT)
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })


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
    }

}