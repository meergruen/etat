package com.example.etat

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.text.HtmlCompat
import java.net.URLEncoder
import java.text.DateFormat
import java.text.SimpleDateFormat

class BillArrayAdapter(
    context: Context?,
    private val mResource: Int,
    objects: List<*>?
) : ArrayAdapter<Any?>(context!!, mResource, objects!!) {
    //var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yy")
    private var dateFormat: DateFormat = DateFormat.getDateInstance() //SimpleDateFormat("dd/MM/yy")


    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var cView = convertView
        val bill = getItem(position) as Bill
        if (cView == null) {
            cView =
                LayoutInflater.from(context).inflate(R.layout.bill_preview, parent, false)
        }
        // Lookup view for data population
        val payee =
            cView!!.findViewById<View>(R.id.preview_payee) as TextView
        val total =
            cView.findViewById<View>(R.id.preview_price) as TextView
        val date =
            cView.findViewById<View>(R.id.preview_date) as TextView
        val productList =
            cView.findViewById<View>(R.id.preview_items) as TextView
        // Populate the data into the template view using the data object
        payee.text = bill.payee
        total.text = HtmlCompat.fromHtml(URLEncoder.encode(bill.totalString, "UTF8"), HtmlCompat.FROM_HTML_MODE_LEGACY)
        date.text = dateFormat.format(bill.date)
        productList.text = bill.itemPreview
        return cView
    }

}