package com.example.etat

import android.app.Application
import android.util.Log
import com.example.etat.JsonIO.importFinanceData
import com.example.etat.JsonIO.saveFinanceData
import java.io.FileOutputStream
import java.util.*

class EtatApp : Application() {
    val dataFileName = "mockdata.json"

    var bills: ArrayList<Bill> = ArrayList()
    var articles: ArrayList<Article> = ArrayList()

    val tags: HashSet<String> = HashSet()
    val payees: HashSet<String> = HashSet()
    val paymentMethods: HashSet<String> = HashSet()
    val articleNames: HashSet<String> = HashSet()

    fun initializeData() {

        copyFileFromAssets(dataFileName, this.filesDir.absolutePath)

        bills = importFinanceData("${this.filesDir.absolutePath}/$dataFileName")
        articles = ArrayList()
        for (bill in bills) {
            articles.addAll(bill.items)
            paymentMethods.add(bill.paymentMethod)
            payees.add(bill.payee)

            tags.addAll(bill.itemTags)
            articleNames.addAll(bill.itemNames)
        }
    }

    private fun copyFileFromAssets(fileName: String, targetDir:String ) {

        try {
            val inStream = this.assets.open(fileName)
            val newFileName =  "$targetDir/$fileName"
            val outStream = FileOutputStream(newFileName)

            val buffer = ByteArray(1024)
            while (true) {
                val bf = inStream.read(buffer)
                if (bf == -1) break
                outStream.write(buffer, 0, bf)
            }
            inStream.close()
            outStream.flush()
            outStream.close()
        } catch (e: Exception) {
            Log.e(JsonIO.tag, "Assets could not be copied", e)
        }

    }


    fun saveData() {
        saveFinanceData(bills, dataFileName)
    }
}