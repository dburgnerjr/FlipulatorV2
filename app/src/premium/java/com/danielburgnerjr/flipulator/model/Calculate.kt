package com.danielburgnerjr.flipulator.model

class Calculate {

    // location/settings info
    private var address: String? = null        // address
    private var city: String? = null            // city
    private var state: String? = null        // state
    private var zipCode: String? = null        // ZIP Code
    private var squareFootage: Int = 0        // square footage
    private var bedrooms: Int = 0            // number of bedrooms
    private var bathrooms: Double = 0.toDouble()        // number of bathrooms
    private var finance: Int = 0    // finance class

    // sales/mortgage activity info
    private var salesPrice: Double = 0.toDouble()        // sales price
    private var percentDown: Double = 0.toDouble()    // percentage down
    private var offerBid: Double = 0.toDouble()        // offer/bid price
    private var downPayment: Double = 0.toDouble()
        private set    // down payment
    var loanAmount: Double = 0.toDouble()
        private set        // loan amount
    var interestRate: Double = 0.toDouble()    // interest rate
    var term: Int = 0                // term
    private var dMonthlyPmt: Double = 0.toDouble()        // monthly payment

    // reserves info
    var mortgage: Double = 0.toDouble()        // mortgage
    var insurance: Double = 0.toDouble()        // insurance
    var taxes: Double = 0.toDouble()            // taxes
    var water: Double = 0.toDouble()            // water
    var gas: Double = 0.toDouble()            // gas
    var electric: Double = 0.toDouble()        // electric
    var totalExpenses: Double = 0.toDouble()
        private set    // total expenses

    // closing costs/market info
    var realEstComm: Double = 0.toDouble()    // real estate commission
    var buyClosCost: Double = 0.toDouble()    // buyer's closing costs
    var sellClosCost: Double = 0.toDouble()    // seller's closing costs
    var fmvarv: Double = 0.toDouble()            // FMV/ARV
    var comparables: Double = 0.toDouble()    // comparables
    var sellingPrice: Double = 0.toDouble()    // selling price

    // final result info
    var reCost: Double = 0.toDouble()
        private set            // real estate commission ($)
    var bcCost: Double = 0.toDouble()
        private set            // buyer's closing costs ($)
    var scCost: Double = 0.toDouble()
        private set            // seller's closing costs ($)
    var totalCost: Double = 0.toDouble()
        private set        // total costs
    var oopExp: Double = 0.toDouble()
        private set            // out of pocket expenses
    var grossProfit: Double = 0.toDouble()
        set(dSP) {
            field = dSP - this.totalCost
        }    // gross profit
    var capGains: Double = 0.toDouble()
        private set        // capital gains
    var netProfit: Double = 0.toDouble()
        private set        // net profit
    var roi: Double = 0.toDouble()
        set(dSP) {
            field = this.netProfit / dSP * 100
        }            // return on investment
    var cashOnCash: Double = 0.toDouble()
        private set        // cash on cash return

    var monthlyPmt: Double
        get() = dMonthlyPmt
        set(dRehab) {
            this.dMonthlyPmt = if (percentDown == 100.0) 0 else (this.loanAmount + dRehab) * (this.interestRate / 12 / 100)
        }

    fun setDownPayment(dPD: Double, dOB: Double) {
        this.downPayment = dPD / 100 * dOB
    }

    fun setLoanAmount() {
        this.loanAmount = this.offerBid - this.downPayment
    }

    fun setMonthlyPmt() {
        this.dMonthlyPmt = if (percentDown == 100.0) 0 else this.loanAmount * (this.interestRate / 12 / 100)
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
