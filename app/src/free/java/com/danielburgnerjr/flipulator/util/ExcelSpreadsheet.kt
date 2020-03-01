package com.danielburgnerjr.flipulator.util

import com.danielburgnerjr.flipulator.model.Calculate
import java.io.File
import java.io.IOException
import java.util.Locale

import jxl.CellView
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Formula
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableFont.*
import jxl.write.WriteException

class ExcelSpreadsheet {

    private var timesBold: WritableCellFormat? = null
    private var times: WritableCellFormat? = null

    @Suppress("INACCESSIBLE_TYPE")
    @Throws(IOException::class, WriteException::class)
    fun createSpreadsheet(fDir: File, calC: Calculate, strXLSFile: String) {
        val fileXls = File(fDir, strXLSFile)
        val wbSettings = WorkbookSettings()

        wbSettings.locale = Locale("en", "EN")

        val workbook = Workbook.createWorkbook(fileXls, wbSettings)
        workbook.createSheet(calC.getAddress() + " " + calC.getCityStZip(), 0)
        val excelSheet = workbook.getSheet(0)

        // Lets create a times font
        val times18ptHeader = WritableFont(ARIAL, 18, BOLD)
        // Define the cell format
        times = WritableCellFormat(times18ptHeader)
        // Lets automatically wrap the cells
        times!!.wrap = true

        // create a bold font
        val times10ptBold = WritableFont(ARIAL, 10, BOLD, false)
        timesBold = WritableCellFormat(times10ptBold)
        // Lets automatically wrap the cells
        timesBold!!.wrap = true

        val cv = CellView()
        cv.format = times
        cv.format = timesBold
        cv.isAutosize = true

        // Number and writable cell formats
        val nbfDollar = NumberFormat("$###,##0")
        val wcfDollar = WritableCellFormat(nbfDollar)
        val wcfProfit = WritableCellFormat(times10ptBold, nbfDollar)

        val nbfPercent = NumberFormat("##.0%")
        val wcfPercent = WritableCellFormat(times10ptBold, nbfPercent)

        val nbfPercentTwoPlaces = NumberFormat("#0.00%")
        val wcfPercentTwoPlaces = WritableCellFormat(nbfPercentTwoPlaces)

        // Write a few headers
        val wrcResults = Label(0, 0, "Results", times)
        excelSheet.addCell(wrcResults)
        excelSheet.mergeCells(0, 0, 4, 0)

        // set up column and row view
        excelSheet.setColumnView(0, 25)
        excelSheet.setRowView(0, (1.5 * 14 * 20).toInt(), false)

        // location info - original
        val lblPropAddressOrig = Label(0, 1, "Property Address:", timesBold)
        excelSheet.addCell(lblPropAddressOrig)
        excelSheet.mergeCells(1, 1, 4, 1)
        val lblStreetAddress = Label(1, 1, calC.getAddress(), timesBold)
        excelSheet.addCell(lblStreetAddress)

        val lblCityOrig = Label(0, 2, "City, State ZIP:", timesBold)
        excelSheet.addCell(lblCityOrig)
        excelSheet.mergeCells(1, 2, 4, 1)
        val lblCity = Label(1, 2, calC.getCityStZip(), timesBold)
        excelSheet.addCell(lblCity)

        val lblSquareFootageOrig = Label(0, 4, "Square Footage:", timesBold)
        excelSheet.addCell(lblSquareFootageOrig)
        excelSheet.mergeCells(1, 4, 4, 1)
        val lblSquareFootage = Label(1, 4, calC.getSquareFootage().toString() + "", timesBold)
        excelSheet.addCell(lblSquareFootage)

        val lblBROrig = Label(0, 5, "BR:", timesBold)
        excelSheet.addCell(lblBROrig)
        val lblBR = Label(1, 5, calC.getBedrooms().toString() + "", timesBold)
        excelSheet.addCell(lblBR)

        val lblBAOrig = Label(2, 5, "BA:", timesBold)
        excelSheet.addCell(lblBAOrig)
        val lblBA = Label(3, 5, calC.getBathrooms().toString() + "", timesBold)
        excelSheet.addCell(lblBA)

        // results info
        val lblFMVARVOriginal = Label(0, 7, "FMV/ARV:", timesBold)
        excelSheet.addCell(lblFMVARVOriginal)
        val nbrFMVARVSixMonthO = Number(3, 7, calC.getFMVARV(), wcfDollar)
        excelSheet.addCell(nbrFMVARVSixMonthO)

        val lblSalesPriceOrig = Label(0, 9, "Sales Price:", timesBold)
        excelSheet.addCell(lblSalesPriceOrig)
        val nbrSalesPriceO = Number(3, 9, calC.getSalesPrice(), wcfDollar)
        excelSheet.addCell(nbrSalesPriceO)

        val lblRehabOrig = Label(0, 11, "Rehab Budget:", timesBold)
        excelSheet.addCell(lblRehabOrig)
        val nbrRehabO = Number(3, 11, calC.getBudget(), wcfDollar)
        excelSheet.addCell(nbrRehabO)

        val lblClosHoldCosts = Label(0, 13, "Clos/Hold Costs:", timesBold)
        excelSheet.addCell(lblClosHoldCosts)

        var buf = StringBuffer()
        buf.append("(D8*0.1)")
        val forClosHoldCosts = Formula(3, 13, buf.toString(), wcfDollar)
        excelSheet.addCell(forClosHoldCosts)

        val lblNetProfitOriginal = Label(0, 15, "Net Profit:", timesBold)
        excelSheet.addCell(lblNetProfitOriginal)

        buf = StringBuffer()
        buf.append("(D8-D10-D12-D14)")
        val forNetProfitSixMonthO = Formula(3, 15, buf.toString(), wcfProfit)
        excelSheet.addCell(forNetProfitSixMonthO)

        val lblRateOfReturnOriginal = Label(0, 17, "Rate of Return:", timesBold)
        excelSheet.addCell(lblRateOfReturnOriginal)

        buf = StringBuffer()
        buf.append("(D16/D8)")
        val forRateOfReturnSixMonthO = Formula(3, 17, buf.toString(), wcfPercent)
        excelSheet.addCell(forRateOfReturnSixMonthO)

        val lblCashCashReturnOriginal = Label(0, 19, "Cash on Cash Return:", timesBold)
        excelSheet.addCell(lblCashCashReturnOriginal)

        buf = StringBuffer()
        buf.append("(D16/(D12+D14))")
        val forCashCashReturnSixMonthO = Formula(3, 19, buf.toString(), wcfPercentTwoPlaces)
        excelSheet.addCell(forCashCashReturnSixMonthO)

        // budget items
        val lblBudgetItems = Label(0, 21, "Budget Items:", timesBold)
        excelSheet.addCell(lblBudgetItems)
        val lblBudgetItemsList = Label(1, 21, calC.getBudgetItems(), timesBold)
        excelSheet.addCell(lblBudgetItemsList)
        excelSheet.mergeCells(1, 21, 5, 24)

        // rehab type
        val lblRehabType = Label(0, 26, "Budget Based On:", timesBold)
        val strRehabType: String? = calC.getRTSel()
        excelSheet.addCell(lblRehabType)

        val lblRehabTypeValue = Label(3, 26, strRehabType, timesBold)
        excelSheet.addCell(lblRehabTypeValue)

        workbook.write()
        workbook.close()
    }
}
