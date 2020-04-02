package com.danielburgnerjr.flipulator.model

import java.io.Serializable

class Calculate : Serializable {

    private var strAddress: String? = null        // address
    private var strCityStZip: String? = null    // city state ZIP code
    private var nSquareFootage: Int = 0        // square footage
    private var nBedrooms: Int = 0            // number of bedrooms
    private var dBathrooms: Double = 0.toDouble()        // number of bathrooms
    private var dSalesPrice: Double = 0.toDouble()        // sales price
    private var dFMVARV: Double = 0.toDouble()            // FMV/ARV
    private var dBudget: Double = 0.toDouble()         // budget
    private var strBudgetItems: String? = null    // list of items that need repair
    private var dClosHoldCosts: Double = 0.toDouble()    // closing/holding costs
    private var dProfit: Double = 0.toDouble()            // profit
    private var dROI: Double = 0.toDouble()            // return on investment
    private var dCashOnCash: Double = 0.toDouble()        // cash on cash return
    private var strRTSel: String? = null

    fun getAddress(): String? {
        return strAddress
    }

    fun setAddress(strAdd: String) {
        this.strAddress = strAdd
    }

    fun getCityStZip(): String? {
        return strCityStZip
    }

    fun setCityStZip(strCSZ: String) {
        this.strCityStZip = strCSZ
    }

    fun getSquareFootage(): Int {
        return nSquareFootage
    }

    fun setSquareFootage(nSF: Int) {
        this.nSquareFootage = nSF
    }

    fun getBedrooms(): Int {
        return nBedrooms
    }

    fun setBedrooms(nBR: Int) {
        this.nBedrooms = nBR
    }

    fun getBathrooms(): Double {
        return dBathrooms
    }

    fun setBathrooms(dBA: Double) {
        this.dBathrooms = dBA
    }

    fun getSalesPrice(): Double {
        return dSalesPrice
    }

    fun setSalesPrice(dSP: Double) {
        this.dSalesPrice = dSP
    }

    fun getFMVARV(): Double {
        return dFMVARV
    }

    fun setFMVARV(dF: Double) {
        this.dFMVARV = dF
    }

    fun getBudget(): Double {
        return dBudget        // returns budget
    }

    fun setBudget(dB: Double) {
        this.dBudget = dB
    }

    fun getBudgetItems(): String? {
        return strBudgetItems
    }

    fun setBudgetItems(strBI: String) {
        this.strBudgetItems = strBI
    }

    fun getClosHoldCosts(): Double {
        return dClosHoldCosts
    }

    fun setClosHoldCosts(dRV: Double) {
        this.dClosHoldCosts = dRV * .1
    }

    fun getProfit(): Double {
        return dProfit
    }

    fun setProfit(dSP: Double, dRV: Double, dB: Double) {
        this.dProfit = dRV - dSP - dB - this.dClosHoldCosts
    }

    fun getROI(): Double {
        return dROI        // returns ROI
    }

    fun setROI(dRV: Double) {
        this.dROI = this.dProfit / dRV * 100
    }

    fun getCashOnCash(): Double {
        return dCashOnCash        // returns cash on cash
    }

    fun setCashOnCash(dB: Double) {
        this.dCashOnCash = this.dProfit / (dB + this.dClosHoldCosts) * 100
    }

    fun getRTSel(): String? {
        return strRTSel
    }

    fun setRTSel(strSel: String) {
        this.strRTSel = strSel
    }
    
    fun calcBudgetRehabType(strT: String) {
        // determines budget based on type and square footage
        if (strT == "Low") {
            this.dBudget = (15 * this.nSquareFootage).toDouble()
        } else if (strT == "Medium") {
            if (this.nSquareFootage < 1500)
                this.dBudget = (25 * this.nSquareFootage).toDouble()
            else
                this.dBudget = (20 * this.nSquareFootage).toDouble()
        } else if (strT == "High") {
            this.dBudget = (30 * this.nSquareFootage).toDouble()
        } else if (strT == "Super-High") {
            this.dBudget = (40 * this.nSquareFootage).toDouble()
        } else {
            this.dBudget = (125 * this.nSquareFootage).toDouble()
        }
    }

    override fun toString(): String {
        return "Location\nAddress: " + strAddress + "\nCity, State ZIP: " + strCityStZip +
                "\nSquare Footage: " + nSquareFootage + "\nBedrooms: " + nBedrooms + "\nBathrooms: " + dBathrooms +
                "\nBudget: " + dBudget + "\nBudget Items: " + strBudgetItems
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
