package com.example.etat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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
        var convertView = convertView
        val bill = getItem(position) as Bill?
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.bill_preview, parent, false)
        }
        // Lookup view for data population
        val payee =
            convertView!!.findViewById<View>(R.id.preview_payee) as TextView
        val total =
            convertView.findViewById<View>(R.id.preview_price) as TextView
        val date =
            convertView.findViewById<View>(R.id.preview_date) as TextView
        val productList =
            convertView.findViewById<View>(R.id.preview_items) as TextView
        // Populate the data into the template view using the data object
        payee.text = bill!!.payee
        total.text = bill.totalString
        date.text = dateFormat.format(bill.date)
        productList.text = bill.itemPreview
        return convertView
    }

}