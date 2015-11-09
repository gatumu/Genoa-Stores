package com.themega.genoastores;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditActivity extends Activity {
	EditText edtQty, edtNames, edtLoc;
	String [] products = {"Towels", "Blankets", "Mosquito Nets", "Bed Covers", "Curtains", "Sheets"};
	Spinner spinner;
	ArrayAdapter<String> adapter;
	String product, uid;
	Database db;
	Sale data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		uid = getIntent().getExtras().getString("id");
		edtQty = (EditText) findViewById(R.id.edtEditQty);
		edtNames = (EditText) findViewById(R.id.edtEditNames);
		edtLoc = (EditText) findViewById(R.id.edtEditLoc);
		spinner = (Spinner) findViewById(R.id.spinnerEdit);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				product = products[arg2];				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        db = new Database(this);
        data = db.fetchOne(uid);
        edtQty.setText(data.getQty());
        edtNames.setText(data.getCustomer_names());
        edtLoc.setText(data.getCustomer_location());
        spinner.setSelection(adapter.getPosition(data.getProduct()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public void update(View v){
		String quantity = edtQty.getText().toString();
		String names = edtNames.getText().toString();
		String loc = edtLoc.getText().toString();
		
		if(quantity.length()>0 && names.length()>0 && loc.length()>0){
			Integer qty = Integer.parseInt(quantity);
			Database database = new Database(this);
			ContentValues cv = new ContentValues();
			cv.put("product", product);
			cv.put("customer_names", names);
			cv.put("customer_location", loc);
			cv.put("qty", qty);
			cv.put("status", "edited");
			database.edit(cv, uid);
			edtQty.setText("");
			edtNames.setText("");
			edtLoc.setText("");
			
			Toast.makeText(this, "Record Updated", Toast.LENGTH_LONG).show();
			}
		else{
			Toast.makeText(this, "Invalid Values Entered", Toast.LENGTH_LONG).show();
		}
	}

}
