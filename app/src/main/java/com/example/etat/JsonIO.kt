package com.example.etat

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.util.*

object JsonIO {

    @JvmStatic
	fun importFinanceData(fileName: String): ArrayList<Bill> {
        var bills = ArrayList<Bill>()
        val jsonString = readStringFile(fileName)
        try {
            val json = JSONObject(jsonString)
            val expensesObj = json.getJSONArray("expenses")
            bills = ArrayList(expensesObj.length())
            for (i in 0 until expensesObj.length()) {
                val expense = expensesObj.getJSONObject(i)
                bills.add(Bill(expense))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return bills
    }

    @JvmStatic
	fun saveFinanceData(
        bills: ArrayList<Bill>,
        filename: String
    ) { //String path = this.filesDir.absolutePath + "/" + dataFileName;
        var billsStr = "["
        for (bill in bills) {
            billsStr += "$bill,"
        }
        billsStr = billsStr.substring(0, billsStr.length - 2) + "]"
        billsStr = "{\"expenses\": [$billsStr]}"
        try {
            val file = FileWriter(filename)
            file.write(billsStr)
            file.flush()
            file.close()
        } catch (e: IOException) {
            Log.e("JSONIO", "Writing to \$path failed!")
        }
    }

    private fun readStringFile(fileName: String): String {
        var fileContent = ""
        try {
            val buffer = StringBuffer("")
            val fis = FileInputStream(fileName)
            while (true) {
                val ch = fis.read()
                if (ch == -1) {
                    break
                } else {
                    buffer.append(ch.toChar())
                }
            }
            fileContent = String(buffer)
        } catch (e: Exception) {
            throw java.lang.Exception("Failure while reading file $fileName!");
        }
        return fileContent
    }
}