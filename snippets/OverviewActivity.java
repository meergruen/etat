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

public class OverviewActivity extends Activity {
	
	 
     protected void onCreate() {
         super.onCreate();
         
         newBill = Bill();
         newArticle = Article();
         
         EtatApp app = (EtatApp) getApplicationContext();
         
         ArrayList bills = app.getBills();
         
         setContentView(R.layout.Overview);
        
         
         ListView billPreview = (ListView) findViewById(R.id.bill_preview_list);
         
         BillArrayAdapter adapter = new BillArrayAdapter(this, R.layout.BillPreview, bills);
         
         billPreview.setAdapter(adapter);
         
         billPreview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				Toast.makeText(getApplicationContext(),
					"Click ListItem Number " + position, Toast.LENGTH_LONG)
					.show();
			}
		});
         
	}
   
}

