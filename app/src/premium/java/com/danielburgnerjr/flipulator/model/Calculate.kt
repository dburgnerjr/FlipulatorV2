package com.danielburgnerjr.flipulator.model

public class Calculate {

    // location/settings info
    private var address: String? = null                // address
    private var city: String? = null                   // city
    private var state: String? = null                  // state
    private var zipCode: String? = null                // ZIP Code
    private var squareFootage: Int = 0                 // square footage
    private var bedrooms: Int = 0                      // number of bedrooms
    private var bathrooms: Double = 0.toDouble()       // number of bathrooms
    private var finance: Int = 0                       // finance class

    // sales/mortgage activity info
    private var salesPrice: Double = 0.toDouble()      // sales price
    private var percentDown: Double = 0.toDouble()     // percentage down
    private var offerBid: Double = 0.toDouble()        // offer/bid price
    private var downPayment: Double = 0.toDouble()     // down payment
    private var loanAmount: Double = 0.toDouble()      // loan amount
    private var interestRate: Double = 0.toDouble()    // interest rate
    private var term: Int = 0                          // term
    private var dMonthlyPmt: Double = 0.toDouble()     // monthly payment

    // reserves info
    private var mortgage: Double = 0.toDouble()        // mortgage
    private var insurance: Double = 0.toDouble()       // insurance
    private var taxes: Double = 0.toDouble()           // taxes
    private var water: Double = 0.toDouble()           // water
    private var gas: Double = 0.toDouble()             // gas
    private var electric: Double = 0.toDouble()        // electric
    private var totalExpenses: Double = 0.toDouble()   // total expenses

    // closing costs/market info
    private var realEstComm: Double = 0.toDouble()     // real estate commission
    private var buyClosCost: Double = 0.toDouble()     // buyer's closing costs
    private var sellClosCost: Double = 0.toDouble()    // seller's closing costs
    private var fmvarv: Double = 0.toDouble()          // FMV/ARV
    private var comparables: Double = 0.toDouble()     // comparables
    private var sellingPrice: Double = 0.toDouble()    // selling price

    // final result info
    private var reCost: Double = 0.toDouble()          // real estate commission ($)
    private var bcCost: Double = 0.toDouble()          // buyer's closing costs ($)
    private var scCost: Double = 0.toDouble()          // seller's closing costs ($)
    private var totalCost: Double = 0.toDouble()       // total costs
    private var oopExp: Double = 0.toDouble()          // out of pocket expenses
    private var grossProfit: Double = 0.toDouble()     // gross profit
        set(dSP) {
            field = dSP - this.totalCost
        }
    private var capGains: Double = 0.toDouble()        // capital gains
    private var netProfit: Double = 0.toDouble()       // net profit
    private var roi: Double = 0.toDouble()             // return on investment
        set(dSP) {
            field = this.netProfit / dSP * 100
        }
    private var cashOnCash: Double = 0.toDouble()      // cash on cash return

    fun getAddress(): String? {
        return this.address
    }

    fun setAddress(strA: String) {
        this.address = strA
    }

    fun getCity(): String? {
        return this.city
    }

    fun setCity(strC: String) {
        this.city = strC
    }

    fun getState(): String? {
        return this.state
    }

    fun setState(strS: String) {
        this.state = strS
    }

    fun getZipCode(): String? {
        return this.zipCode
    }

    fun setZipCode(strZ: String) {
        this.zipCode = strZ
    }

    fun getSquareFootage(): Int? {
        return this.squareFootage
    }

    fun setSquareFootage(nSF: Int) {
        this.squareFootage = nSF
    }

    fun getBedrooms(): Int? {
        return this.bedrooms
    }

    fun setBedrooms(nBR: Int) {
        this.bedrooms = nBR
    }

    fun getBathrooms(): Double? {
        return this.bathrooms
    }

    fun setBathrooms(dBA: Double) {
        this.bathrooms = dBA
    }

    fun getFinance(): Int? {
        return this.finance
    }

    fun setFinance(nF: Int) {
        this.finance = nF
    }

    fun getMonthlyPmt(): Double {
        return this.dMonthlyPmt
    }

    fun setDownPayment(dPD: Double, dOB: Double) {
        this.downPayment = dPD / 100 * dOB
    }

    fun setLoanAmount() {
        this.loanAmount = this.offerBid - this.downPayment
    }

    fun setMonthlyPmt() {
        if (this.percentDown == 100.0) {
            this.dMonthlyPmt = 0.toDouble()
        } else {
            this.dMonthlyPmt = this.loanAmount * ((this.interestRate / 12) / 100)
        }
    }

    fun setTotalExpenses() {
        this.totalExpenses = this.mortgage + this.insurance + this.taxes + this.water + this.gas + this.electric
    }

    fun setRECost(dSP: Double, dCP: Double) {
        this.reCost = dSP * (dCP / 100)
    }

    fun setBCCost(dSP: Double, dCP: Double) {
        this.bcCost = dSP * (dCP / 100)
    }

    fun setSCCost(dSP: Double, dCP: Double) {
        this.scCost = dSP * (dCP / 100)
    }

    fun setTotalCost(dO: Double, dRH: Double, dRS: Double) {
        this.totalCost = dO + dRH + dRS + this.reCost + this.bcCost
    }

    fun setOOPExp(dDP: Double, dRS: Double, dRH: Double) {
        this.oopExp = dDP + dRH + dRS + this.bcCost
    }

    fun setCapGains() {
        this.capGains = this.grossProfit * .3
    }

    fun setNetProfit() {
        this.netProfit = this.grossProfit - this.capGains
    }

    fun setCashOnCash() {
        this.cashOnCash = this.netProfit / this.oopExp * 100
    }

}
