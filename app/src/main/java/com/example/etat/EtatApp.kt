package com.example.etat

import android.app.Application
import com.example.etat.JsonIO.importFinanceData
import com.example.etat.JsonIO.saveFinanceData
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
        bills = importFinanceData(dataFileName)
        articles = ArrayList()
        for (bill in bills) {
            articles.addAll(bill.items)
            paymentMethods.add(bill.paymentMethod)
            payees.add(bill.payee)

            tags.addAll(bill.itemTags)
            articleNames.addAll(bill.itemNames)
        }
    }

    fun saveData() {
        saveFinanceData(bills, dataFileName)
    }
}