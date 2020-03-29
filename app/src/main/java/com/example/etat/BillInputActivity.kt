package com.example.etat

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.*
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class BillInputActivity : Activity() {
    private val dateFormat = "dd/MM/yy"

    private val datePicker: DatePicker? = null
    private var calendar = Calendar.getInstance()
    private var year = 0
    private var month = 0
    private var day = 0
    private var dateView = EditText(this@BillInputActivity)
    private var priceView = EditText(this@BillInputActivity)
    private var commentView: EditText? = null
    private var payeeView: AutoCompleteTextView? = null
    private var paymentView: AutoCompleteTextView? = null
    private var articleNameView: AutoCompleteTextView? = null
    private var newBill = Bill()
    private var newArticle = Article()


    protected fun onCreate() {
        // articles = new ArrayList<Articles>();
        val app = applicationContext as EtatApp
        setContentView(R.layout.activity_bill_input)

        connectReactiveTextInput(R.id.payee_input, app.payees)
        connectReactiveTextInput(R.id.payment_input, app.paymentMethods)
        connectReactiveTextInput(R.id.article_name_input, app.articleNames)
        //  connectReactiveTextInput(android.R.id.tag_input,          app.getTags());

        dateView = findViewById<View>(R.id.date_input) as EditText
        priceView = findViewById<View>(R.id.price_input) as EditText
        commentView = findViewById<View>(R.id.article_comment_input) as EditText
        payeeView = findViewById<View>(R.id.payee_input) as AutoCompleteTextView
        paymentView = findViewById<View>(R.id.payment_input) as AutoCompleteTextView
        articleNameView = findViewById<View>(R.id.article_name_input) as AutoCompleteTextView

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        showDate(year, month + 1, day)

        dateView.setOnClickListener {
            DatePickerDialog(
                this@BillInputActivity, myDateListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val separator =
            DecimalFormatSymbols.getInstance().decimalSeparator
        priceView.keyListener = DigitsKeyListener.getInstance("0123456789$separator")
        priceView.addTextChangedListener(object : TextWatcher {
            var isEditing = false
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) { // for price field
//	double doubleValue = 0;
                    val sep = DecimalFormatSymbols.getInstance().decimalSeparator
                    try {
                        val euro = s.toString().replace(sep, '.').toDouble() // test!!!
                        newArticle.price = euro
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            applicationContext,
                            "Ungültiges Format! Dezimaltrennung erfolgt durch '" + sep + "' für " + Locale.getDefault().displayLanguage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        })
    }

    private fun connectReactiveTextInput(
        viewID: Int,
        proposals: HashSet<String>
    ) {
        val proposalItemView = R.layout.simple_dropdown_item
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@BillInputActivity,
            proposalItemView,
            proposals as List<String>
        )
        val textView = findViewById<AutoCompleteTextView>(viewID)
        textView.setAdapter(adapter)
    }

    /*
    fun setDate(view: View?) { //???
        showDialog(999)
        Toast.makeText(
            applicationContext, "ca",
            Toast.LENGTH_SHORT
        )
            .show()
    }*/

    /*
    override fun onCreateDialog(id: Int): Dialog { // TODO Auto-generated method stub
        return if (id == 999) {
            DatePickerDialog(
                this,
                myDateListener, year, month, day
            )
        } else null
    }*/

    private val myDateListener = OnDateSetListener { view, year, month, day ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        updateLabel()
        newBill.setDate(year, month, day) // test if  off by one
        // showDate(arg1, arg2+1, arg3);
    }

    private fun updateLabel() {
        dateView.setText(dateFormat.format(calendar.time))
    }

    private fun showDate(year: Int, month: Int, day: Int) { // delete?
        dateView.setText(
            StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)
        )
    }

    fun addBill(view: View?) { //newBill.setDate(calendar);
        newBill.payee = payeeView!!.text.toString()
        newBill.paymentMethod = paymentView!!.text.toString()
        newBill.payee = payeeView!!.text.toString()
        val app = applicationContext as EtatApp
        app.bills.add(newBill)
        app.saveData()

        newBill = Bill()
    }

    fun addItem(view: View?) { // price already set
        newArticle.name = articleNameView!!.text.toString()
        newArticle.comment = commentView!!.text.toString()
        //newArticle.setTags(paymentView.getText());
        newBill.add(newArticle)

        newArticle = Article()
    }
}