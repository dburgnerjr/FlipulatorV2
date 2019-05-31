package com.danielburgnerjr.flipulator.model

import java.io.Serializable

class Calculate : Serializable {

    var address: String? = null        // address
    var cityStZip: String? = null    // city state ZIP code
    var squareFootage: Int = 0        // square footage
    var bedrooms: Int = 0            // number of bedrooms
    var bathrooms: Double = 0.toDouble()        // number of bathrooms
    var salesPrice: Double = 0.toDouble()        // sales price
    var fmvarv: Double = 0.toDouble()            // FMV/ARV
    // returns budget
    var budget: Double = 0.toDouble()         // budget
    // returns rehab flag
    var rehabFlag: Int = 0            // 0 = rehab flat rate, 1 = rehab class
    var budgetItems: String? = null    // list of items that need repair
    var dClosHoldCosts: Double = 0.toDouble()    // closing/holding costs
    var dProfit: Double = 0.toDouble()            // profit
    var dROI: Double = 0.toDouble()            // return on investment
    var dCashOnCash: Double = 0.toDouble()        // cash on cash return


    fun calcBudgetRehabType(strT: String) {

        // determines budget based on type and square footage
        if (strT == "Low") {
            this.budget = (15 * this.squareFootage).toDouble()
        } else if (strT == "Medium") {
            if (this.squareFootage < 1500)
                this.budget = (25 * this.squareFootage).toDouble()
            else
                this.budget = (20 * this.squareFootage).toDouble()
        } else if (strT == "High") {
            this.budget = (30 * this.squareFootage).toDouble()
        } else if (strT == "Super-High") {
            this.budget = (40 * this.squareFootage).toDouble()
        } else {
            this.budget = (125 * this.squareFootage).toDouble()
        }
    }

    override fun toString(): String {
        return "Location\nAddress: " + address + "\nCity, State ZIP: " + cityStZip +
                "\nSquare Footage: " + squareFootage + "\nBedrooms: " + bedrooms + "\nBathrooms: " + bathrooms +
                "\nBudget: " + budget + "\nBudget Items: " + budgetItems
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}