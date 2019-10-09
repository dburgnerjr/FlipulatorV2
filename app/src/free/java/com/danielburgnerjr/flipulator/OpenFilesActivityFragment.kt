package com.danielburgnerjr.flipulator

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.danielburgnerjr.flipulator.model.Calculate
import com.danielburgnerjr.flipulator.util.ExcelSpreadsheet

import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Locale
import java.text.NumberFormat
import java.text.ParseException

import jxl.Cell
import jxl.Sheet
import jxl.Workbook
import jxl.read.biff.BiffException

class OpenFilesActivityFragment : Fragment() {
/*

    private var lvView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.openfiles)

        // get the files and place it in a file array
        val myDir = File(getActivity()?.getApplicationContext()?.getExternalFilesDir(null) + "/FlipulatorFree")
        val strPath = myDir.getPath()
        val fFileList = File(strPath)
        val fFileArray = fFileList.listFiles()
        val arrayList = ArrayList<String>()

        // Get ListView object from xml
        lvView = findViewById(R.id.filesList) as ListView

        // Defined Array values to show in ListView
        for (nI in fFileArray.indices) {
            arrayList.add(fFileArray[nI].getName().substring(0, fFileArray[nI].getName().length - 4))
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Fourth - the Array of data

        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList)


        // Assign adapter to ListView
        lvView.adapter = adapter

        // ListView Item Click Listener
        lvView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            // ListView Clicked item index

            // ListView Clicked item value
            val itemValue = lvView.getItemAtPosition(position) as String

            // Get file and open it
            //Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT).show();
            val newActivity: Intent
            var calC: Calculate? = null
            try {
                val fPath = File(getActivity()?.getApplicationContext()?.getExternalFilesDir(null) + "/FlipulatorFree")
                val strFilePathXLS = fPath.getPath() + "/" + itemValue + ".xls"
                try {
                    val wbExcelFile = Workbook.getWorkbook(File(strFilePathXLS))
                    val shWorkSheet = wbExcelFile.getSheet(0)

                    // Calculate
                    val celAddress = shWorkSheet.getCell(1, 1)
                    val celCityStZip = shWorkSheet.getCell(1, 2)
                    val celSqFootage = shWorkSheet.getCell(1, 4)
                    val celBedrooms = shWorkSheet.getCell(1, 5)
                    val celBathrooms = shWorkSheet.getCell(3, 5)
                    val celFMVARV = shWorkSheet.getCell(3, 7)
                    val celSalesPrice = shWorkSheet.getCell(3, 9)
                    val celBudget = shWorkSheet.getCell(3, 11)
                    val celBudgetItems = shWorkSheet.getCell(1, 21)
                    val celRehabType = shWorkSheet.getCell(3, 26)

                    val strAddress = celAddress.getContents()
                    val strCityStZip = celCityStZip.getContents()
                    val nSqFootage = Integer.parseInt(celSqFootage.getContents())
                    val nBedrooms = Integer.parseInt(celBedrooms.getContents())
                    val dBathrooms = java.lang.Double.parseDouble(celBathrooms.getContents())
                    val strBudgetItems = celBudgetItems.getContents()

                    val us = Locale("en", "US")
                    val nbfDollar = NumberFormat.getCurrencyInstance(us)

                    val numFMVARV = nbfDollar.parse(celFMVARV.getContents())
                    val dFMVARV = numFMVARV.toDouble()
                    val numSalesPrice = nbfDollar.parse(celSalesPrice.getContents())
                    val dSalesPrice = numSalesPrice.toDouble()
                    val numRehabBudget = nbfDollar.parse(celBudget.getContents())
                    val dRehabBudget = numRehabBudget.toDouble()

                    val strRehabType = celRehabType.getContents()

                    calC = Calculate()
                    calC!!.setAddress(strAddress)
                    calC!!.setCityStZip(strCityStZip)
                    calC!!.setSquareFootage(nSqFootage)
                    calC!!.setBedrooms(nBedrooms)
                    calC!!.setBathrooms(dBathrooms)
                    calC!!.setFMVARV(dFMVARV)
                    calC!!.setSalesPrice(dSalesPrice)
                    calC!!.setBudgetItems(strBudgetItems)
                    calC!!.setBudget(dRehabBudget)
                    calC!!.setRTSel(strRehabType)

                } catch (e: FileNotFoundException) {
                    Toast.makeText(getActivity()?.getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show()
                } catch (e: BiffException) {
                    Toast.makeText(getActivity()?.getApplicationContext(), "Biff Exception", Toast.LENGTH_SHORT).show()
                } catch (e: ParseException) {
                    Toast.makeText(getActivity()?.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show()
                }

            } catch (e: IOException) {
                Toast.makeText(getActivity()?.getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show()
            } finally {
                newActivity = Intent(getActivity()?.getApplicationContext(), CalculateActivity::class.java)
                newActivity.putExtra("Calculate", calC)
                startActivity(newActivity)
                finish()
            }
        }

    }
*/
}
