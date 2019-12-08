import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.view.Menu;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class BillInputActivity extends Activity {
	
	private String dateFormat = "dd/MM/yy";
		
	private DatePicker datePicker;
	
	private Calendar calendar;
	private int year, month, day;
	
	private EditText dateView;
	private EditText priceView;
	private EditText commentView;
	
	private AutoCompleteTextView payeeView;
	private AutoCompleteTextView paymentView;
	private AutoCompleteTextView articleNameView;
	
	private Bill newBill;
	private Article newArticle;
	 
     protected void onCreate() {
         super.onCreate();
         
         newBill = new Bill();
         newArticle = new Article();
        // articles = new ArrayList<Articles>();
         
         EtatApp app = (EtatApp) getApplicationContext();
         
         setContentView(R.layout.BillInput);
         
         connectReactiveTextInput(android.R.layout.payee_input,        app.getPayees());
         connectReactiveTextInput(android.R.layout.payment_input,      app.getPaymentMethods());
         connectReactiveTextInput(android.R.layout.article_name_input, app.getArticleNames());
       //  connectReactiveTextInput(android.R.layout.tag_input,          app.getTags());
         
         dateView = (EditText) findViewById(R.id.date_input);
         priceView = (EditText) findViewById(R.id.price_input);
         commentView = (EditText) findViewById(R.id.article_comment_input);
         
         payeeView = (AutoCompleteTextView) findViewById(R.id.payee_input);
         paymentView = (AutoCompleteTextView) findViewById(R.id.payment_input);
         articleNameView = (AutoCompleteTextView) findViewById(R.id.article_name_input);
         
         
		  calendar = Calendar.getInstance();
		  year = calendar.get(Calendar.YEAR);
		  month = calendar.get(Calendar.MONTH);
		  day = calendar.get(Calendar.DAY_OF_MONTH);
		  
		  showDate(year, month+1, day);
		  
		  dateView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new DatePickerDialog(classname.this, date, calendar
							.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
							calendar.get(Calendar.DAY_OF_MONTH)).show();
				}
			});
			
			
         char separator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
		  priceView.setKeyListener(DigitsKeyListener.getInstance("0123456789" + separator));
		  
			priceView.addTextChangedListener( new TextWatcher() {
					boolean isEditing;
					@Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
					@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

				public void afterTextChanged(Editable s) { // for price field
					double doubleValue = 0;
					if (s != null) {
						try {
							
							char sep = DecimalFormatSymbols.getInstance().getDecimalSeparator();
							
							double euro = Double.parseDouble(s.toString().replace(sep, '.')); // test!!!
							
							newArticle.setPrice(euro);
							
							
						} catch (NumberFormatException e) {
							Toast.makeText(getApplicationContext(), "Ungüliges Format! Dezimaltrennung erfolgt durch '" + sep + "' für " + Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
    }
	
	
	private void connectReactiveTextInput(int viewID, Set<String> proposals) {
		
		int proposalItemView = android.R.layout.simple_dropdown_item; 
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, proposalItemView, proposals);
         AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(viewID);
         textView.setAdapter(adapter);
	}
	
	
	 @SuppressWarnings("deprecation")
   public void setDate(View view) {  //???
      showDialog(999);
      Toast.makeText(getApplicationContext(), "ca", 
         Toast.LENGTH_SHORT)
      .show();
   }

   @Override
   protected Dialog onCreateDialog(int id) {
      // TODO Auto-generated method stub
      if (id == 999) {
         return new DatePickerDialog(this, 
            myDateListener, year, month, day);
      }
      return null;
   }
   
   private DatePickerDialog.OnDateSetListener myDateListener = new 
      DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet((DatePicker view, int year, int month, int day) {
         
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        updateLabel();
		
		newBill.setDate(year, month, day); // test if  off by one
        
        // showDate(arg1, arg2+1, arg3);
      }
   };


	private void updateLabel() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.DE);
		dateView.setText(sdf.format(calendar.getTime()));
	}

   private void showDate(int year, int month, int day) {
      dateView.setText(new StringBuilder().append(day).append("/")
      .append(month).append("/").append(year));
   }
   
   
	public void addBill(View view) {
		
		//newBill.setDate(calendar);
		newBill.setPayee(payeeView.getText());
		newBill.setPaymentMethod(paymentView.getText());
		newBill.setPayee(payeeView.getText());
		
		EtatApp app = (EtatApp) getApplicationContext();
		
		app.getBills().addBill(newBill);
		app.saveData();
		
		newBill = new Bill();
		
	}
	
	   
	public void addItem(View view) {
		
		// price already set
		newArticle.setName(articleNameView.getText());
		newArticle.setComment(commentView.getText());
		//newArticle.setTags(paymentView.getText());
		
		newBill.add(newArticle);
		
		newArticle = new Article();
		
		
	}

   
}
