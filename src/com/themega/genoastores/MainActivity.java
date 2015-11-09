package com.themega.genoastores;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {
	EditText edtQty, edtNames, edtLoc;
	String [] products = {"Towels", "Blankets", "Mosquito Nets", "Bed Covers", "Curtains", "Sheets"};
	Spinner spinner;
	ArrayAdapter<String> adapter;
	String product;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		edtQty = (EditText) findViewById(R.id.edtQty);
		edtNames = (EditText) findViewById(R.id.edtNames);
		edtLoc = (EditText) findViewById(R.id.edtLoc);
		
		spinner = (Spinner) findViewById(R.id.spinnerProducts);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void save(View v){
		String quantity = edtQty.getText().toString();
		String names = edtNames.getText().toString();
		String loc = edtLoc.getText().toString();
		Date day = new Date();
		String date = day.toString();
		
		if(quantity.length()>0 && names.length()>0 && loc.length()>0){
			Integer qty = Integer.parseInt(quantity);
			Sale d = new Sale("", date, product, names, loc, "no", qty);
			Database db = new Database(this);
			db.save(d);
			edtQty.setText("");
			edtNames.setText("");
			edtLoc.setText("");
			
			Toast.makeText(this, "You have " +db.count()+ " Unsynced records", Toast.LENGTH_LONG).show();
			}
		else{
			Toast.makeText(this, "Invalid Values Entered", Toast.LENGTH_LONG).show();
		}
	}
	public void viewSales(View x){
    	startActivity(new Intent(this, SalesActivity.class));
	}
	
	public void unsynced(View x){
    	startActivity(new Intent(this, UnsyncedActivity.class));
	}

}
