package com.danielburgnerjr.flipulator.model

import java.io.Serializable

public class Calculate : Serializable {

    // location/settings info
    private var address: String? = null                // address
    private var city: String? = null                   // city
    private var state: String? = null                  // state
    private var zipCode: String? = null                // ZIP Code
    private var squareFootage: Int = 0                 // square footage
    private var bedrooms: Int = 0                      // number of bedrooms
    private var bathrooms: Double = 0.toDouble()       // number of bathrooms
    private var finance: Int = 0                       // finance class
    private var strFinance: String = ""                // String value finance

    // sales/mortgage activity info
    private var salesPrice: Double = 0.toDouble()      // sales price
    private var percentDown: Double = 0.toDouble()     // percentage down
    private var offerBid: Double = 0.toDouble()        // offer/bid price
    private var downPayment: Double = 0.toDouble()     // down payment
    private var loanAmount: Double = 0.toDouble()      // loan amount
    private var interestRate: Double = 0.toDouble()    // interest rate
    private var term: Int = 0                          // term
    private var strBudgetItems: String? = null         // budget items
    private var dMonthlyPmt: Double = 0.toDouble()     // monthly payment
    private var dBudget: Double = 0.toDouble()         // budget
    private var nRehab: Int = 0                        // rehab type

    // reserves info
    private var mortgage: Double = 0.toDouble()        // mortgage
    private var insurance: Double = 0.toDouble()       // insurance
    private var taxes: Double = 0.toDouble()           // taxes
    private var water: Double = 0.toDouble()           // water
    private var gas: Double = 0.toDouble()             // gas
    private var electric: Double = 0.toDouble()        // electric
    private var totalExpenses: Double = 0.toDouble()   // total expenses
    private var timeFrameFactor: Double = 1.0          // time frame factor

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

    fun getFinanceValue(): String? {
        return this.strFinance
    }

    fun setFinanceValue(strF: String) {
        this.strFinance = strF
    }

    fun getSalesPrice(): Double {
        return this.salesPrice
    }

    fun setSalesPrice(dSP: Double) {
        this.salesPrice = dSP
    }

    fun getPercentDown(): Double? {
        return this.percentDown
    }

    fun setPercentDown(dPD: Double) {
        this.percentDown = dPD
    }

    fun getOfferBid(): Double {
        return this.offerBid
    }

    fun setOfferBid(dOB: Double) {
        this.offerBid = dOB
    }

    fun getInterestRate(): Double? {
        return this.interestRate
    }

    fun setInterestRate(dIR: Double) {
        this.interestRate = dIR
    }

    fun getTerm(): Int? {
        return this.term
    }

    fun setTerm(nT: Int) {
        this.term = nT
    }

    fun getBudgetItems(): String? {
        return this.strBudgetItems
    }

    fun setBudgetItems(strBI: String) {
        this.strBudgetItems = strBI
    }

    fun getRehab(): Int {
        return this.nRehab
    }

    fun setRehab(nR: Int) {
        this.nRehab = nR
    }

    fun getMonthlyPmt(): Double {
        return this.dMonthlyPmt
    }

    fun setMonthlyPmt(dR: Double) {
        if (this.percentDown == 100.0)
            this.dMonthlyPmt = 0.0
        else
            this.dMonthlyPmt = ((this.loanAmount+dR)*((this.interestRate/12)/100))
    }

    fun setDownPayment() {
        this.downPayment = (this.percentDown/100) * this.offerBid
    }

    fun getDownPayment(): Double {
        return this.downPayment
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

    fun getBudget(): Double {
        return dBudget        // returns budget
    }

    fun setBudget(dB: Double) {
        this.dBudget = dB
    }

    fun getMortgage(): Double? {
        return this.mortgage
    }

    fun setMortgage(dM: Double) {
        this.mortgage = dM
    }

    fun getInsurance(): Double? {
        return this.insurance
    }

    fun setInsurance(dI: Double) {
        this.insurance = dI
    }

    fun getTaxes(): Double? {
        return this.taxes
    }

    fun setTaxes(dT: Double) {
        this.taxes = dT
    }

    fun getWater(): Double? {
        return this.water
    }

    fun setWater(dW: Double) {
        this.water = dW
    }

    fun getGas(): Double? {
        return this.gas
    }

    fun setGas(dG: Double) {
        this.gas = dG
    }

    fun getElectric(): Double? {
        return this.electric
    }

    fun setElectric(dE: Double) {
        this.electric = dE
    }

    fun setTotalExpenses() {
        this.totalExpenses = this.mortgage + this.insurance + this.taxes + this.water + this.gas + this.electric
    }

    fun getTotalExpenses(): Double? {
        return this.totalExpenses
    }

    fun getRealEstComm(): Double {
        return realEstComm
    }

    fun setRealEstComm(dREC: Double) {
        this.realEstComm = dREC
    }

    fun getBuyClosCost(): Double {
        return buyClosCost
    }

    fun setBuyClosCost(dBCC: Double) {
        this.buyClosCost = dBCC
    }

    fun getSellClosCost(): Double {
        return sellClosCost
    }

    fun setSellClosCost(dSCC: Double) {
        this.sellClosCost = dSCC
    }

    fun getFMVARV(): Double {
        return fmvarv
    }

    fun setFMVARV(dF: Double) {
        this.fmvarv = dF
    }

    fun getComparables(): Double {
        return comparables
    }

    fun setComparables(dC: Double) {
        this.comparables = dC
    }

    fun getSellingPrice(): Double {
        return sellingPrice
    }

    fun setSellingPrice(dSP: Double) {
        this.sellingPrice = dSP
    }

    fun getTimeFrameFactor(): Double {
        return timeFrameFactor
    }

    fun setTimeFrameFactor(dTFF: Double) {
        this.timeFrameFactor = dTFF
    }

    fun setRECost(dSP: Double, dCP: Double) {
        this.reCost = dSP * (dCP / 100)
    }

    fun getRECost(): Double {
        return reCost
    }

    fun setBCCost(dSP: Double, dCP: Double) {
        this.bcCost = dSP * (dCP / 100)
    }

    fun getBCCost(): Double {
        return bcCost
    }

    fun setSCCost(dSP: Double, dCP: Double) {
        this.scCost = dSP * (dCP / 100)
    }

    fun getSCCost(): Double {
        return scCost
    }

    fun setTotalCost(dO: Double, dRH: Double, dRS: Double) {
        this.totalCost = dO + dRH + dRS + this.reCost + this.bcCost
    }

    fun getTotalCost(): Double {
        return totalCost
    }

    fun setOOPExp(dDP: Double, dRS: Double, dRH: Double) {
        this.oopExp = dDP + dRH + dRS + this.bcCost
    }

    fun getOOPExp(): Double {
        return oopExp
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

    fun calcBudgetRehabType(strT: String) {
        // determines budget based on type and square footage
        if (strT == "Low") {
            this.dBudget = (15 * this.squareFootage).toDouble()
        } else if (strT == "Medium") {
            if (this.squareFootage < 1500)
                this.dBudget = (25 * this.squareFootage).toDouble()
            else
                this.dBudget = (20 * this.squareFootage).toDouble()
        } else if (strT == "High") {
            this.dBudget = (30 * this.squareFootage).toDouble()
        } else if (strT == "Super-High") {
            this.dBudget = (40 * this.squareFootage).toDouble()
        } else {
            this.dBudget = (125 * this.squareFootage).toDouble()
        }
    }

    override fun toString(): String {
        return "Location\nAddress: " + address + "\nCity, State ZIP: " + city  + ", " + state + " " + zipCode +
                "\nSquare Footage: " + squareFootage + "\nBedrooms: " + bedrooms + "\nBathrooms: " + bathrooms +
                "\nBudget: " + dBudget + "\nSales Price: " + salesPrice + "\nPercent Down: " + percentDown +
                "\nOffer Bid: " + offerBid + "\nInterest Rate: " + interestRate + "\nTern: " + term +
                "\nBudget Items: " + strBudgetItems + "\nFinance Type: " + finance
    }

}
