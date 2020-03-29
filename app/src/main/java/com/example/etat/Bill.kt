package com.example.etat

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Bill {
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
   // var dateFormat = DateFormat.getDateInstance()
    var tag = "Bill"

    @JvmField
    var date: Date = Date()
    @JvmField
    var payee: String = ""
    @JvmField
    var paymentMethod: String = ""
    @JvmField
    var currencySymbol: String = ""
    var items: ArrayList<Article> = ArrayList()

    var billNumber: Int

    constructor(obj: JSONObject) {
        billNumber = count
        ++count

        try {
            date = dateFormat.parse(obj.getString("date"))!!
            payee = obj.getString("payee")
            currencySymbol = obj.getString("currency")
            paymentMethod = obj.getString("payment")
            val jsonItems = obj.getJSONArray("items")

            Log.i(tag, currencySymbol)
          //  var s = currencySymbol.toByteArray("UTF-8")
            // String(currencySymbol.getBytes("UTF-8"), "ISO-8859-1")
            var s2 = URLEncoder.encode(currencySymbol, "UTF-8")
            var s3 = URLEncoder.encode(currencySymbol, "ISO-8859-1")
            Log.i(tag, s2)
            Log.i(tag, s3)

            for (i in 0 until jsonItems.length()) {
                items.add(Article(jsonItems.getJSONObject(i), this))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    constructor(
        payee: String,
        paymentMethod: String,
        paymentDate: Date,
        items: ArrayList<Article>
    ) {
        billNumber = count
        ++count

        date = paymentDate
        this.payee = payee
        this.paymentMethod = paymentMethod
        this.items = items
    }

    constructor() {
        billNumber = count
        ++count
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val calendar = GregorianCalendar(year, month, day)
        date = calendar.getTime()
    }

    fun setDate(calendar: Calendar) {
        date = calendar.getTime()
    }

    fun add(item: Article) {
        items.add(item)
    }

    val itemTags: HashSet<String>
        get() {
            val tags: HashSet<String> = HashSet()
            for (item in items) {
                tags.addAll(item.items)
            }
            return tags
        }

    val itemNames: HashSet<String>
        get() {
            val names: HashSet<String> = HashSet()
            for (item in items) {
                names.add(item.name)
            }
            return names
        }

    // TODO: test!!!
    //String totalStr = System.out.format(Locale.getDefault().getDisplayLanguage(), "%-10.2f%n%n", total);
    val totalString: String
        get() {
            var total = 0.0
            for (item in items) {
                total += item.price
            }
            val totalStr =
                NumberFormat.getNumberInstance().format(total) // TODO: test!!!
            //String totalStr = System.out.format(Locale.getDefault().getDisplayLanguage(), "%-10.2f%n%n", total);
            return totalStr + " " + currencySymbol
        }


    val itemPreview: String
        get() {
            Log.i(tag, "${toString()}")
            var itemsStr = ""
            for (item in items) {
                itemsStr += item.name + ", "
            }
            Log.i(tag, "$itemsStr")
            return itemsStr.substring(0, itemsStr.length - 2)
        }

    override fun toString(): String {
        var itemsStr = ""
        for (item in items) {
            itemsStr += item.toString() + ","
        }
        itemsStr = itemsStr.substring(0, itemsStr.length - 1)
        return "{\"date\":\"" + dateFormat.format(date) +
                "\", \"payee\":\"" + payee +
                "\", \"payment\":\"" + paymentMethod +
                "\", \"currency\":\"" + currencySymbol +
                "\", \"items\":[" + itemsStr + "]}"
    }

    companion object {
        private var count = 0
    }
}