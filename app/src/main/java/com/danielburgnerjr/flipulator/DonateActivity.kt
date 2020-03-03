package com.danielburgnerjr.flipulator

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import android.transition.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.donate_activity.*

class DonateActivity : AppCompatActivity() {

//}, PurchasesUpdatedListener {

/*
    private lateinit var mBillingClient: BillingClient
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_activity)
        setupBillingClient()
*/

/*
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
*/

/*
    }

    private fun setupBillingClient() {
        mBillingClient = BillingClient
                .newBuilder(this)
                .setListener(this)
                .build()

        mBillingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    println("BILLING | startConnection | RESULT OK")
                } else {
                    println("BILLING | startConnection | RESULT: $billingResponseCode")
                }
            }

            override fun onBillingServiceDisconnected() {
                println("BILLING | onBillingServiceDisconnected | DISCONNECTED")
            }
        })
    }

    fun onLoadProductsClicked(view: View) {
        if (mBillingClient.isReady) {
            val params = SkuDetailsParams
                    .newBuilder()
                    .setSkusList(CATALOG_DEBUG)
                    .setType(BillingClient.SkuType.INAPP)
                    .build()
            mBillingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
                if (responseCode == BillingClient.BillingResponse.OK) {
                    println("querySkuDetailsAsync, responseCode: $responseCode")
                    initProductAdapter(skuDetailsList)
                } else {
                    println("Can't querySkuDetailsAsync, responseCode: $responseCode")
                }
            }
        } else {
            println("Billing Client not ready")
        }
    }

    private fun initProductAdapter(skuDetailsList: List<SkuDetails>) {
        productAdapter = ProductAdapter(skuDetailsList) {
            val billingFlowParams = BillingFlowParams
                    .newBuilder()
                    .setSkuDetails(it)
                    .build()
            mBillingClient.launchBillingFlow(this, billingFlowParams)
        }
        products.adapter = productAdapter
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        println("onPurchasesUpdated: $responseCode")
        allowMultiplePurchases(purchases)
    }

    private fun allowMultiplePurchases(purchases: MutableList<Purchase>?) {
        val purchase = purchases?.first()
        if (purchase != null) {
            mBillingClient.consumeAsync(purchase.purchaseToken) { responseCode, purchaseToken ->
                if (responseCode == BillingClient.BillingResponse.OK && purchaseToken != null) {
                    println("AllowMultiplePurchases success, responseCode: $responseCode")
                } else {
                    println("Can't allowMultiplePurchases, responseCode: $responseCode")
                }
            }
        }
    }

    private fun clearHistory() {
        mBillingClient.queryPurchases(BillingClient.SkuType.INAPP).purchasesList
                .forEach {
                    mBillingClient.consumeAsync(it.purchaseToken) { responseCode, purchaseToken ->
                        if (responseCode == BillingClient.BillingResponse.OK && purchaseToken != null) {
                            println("onPurchases Updated consumeAsync, purchases token removed: $purchaseToken")
                        } else {
                            println("onPurchases some troubles happened: $responseCode")
                        }
                    }
                }
    }

    companion object {
        private val CATALOG_DEBUG = listOf("android.test.purchased", "android.test.canceled", "android.test.refunded", "android.test.item_unavailable")
        private val GOOGLE_CATALOG = listOf("donation1", "donation5", "donation10", "donation25", "donation50", "donation100")
    }
*/
}