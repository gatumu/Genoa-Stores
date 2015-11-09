package com.themega.genoastores;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SalesActivity extends Activity {
	ListView list;
	CustomAdapter adapter;
	Database db;
	ArrayList<Sale> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sales);
		
		list = (ListView) findViewById(R.id.lvSales);
		db = new Database(this);
		data = db.fetch("all");
		adapter = new CustomAdapter(this, data);
		list.setAdapter(adapter);
		//list.setOnItemClick(this);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Sale x = data.get(position);
				Intent y = new Intent(getApplicationContext(), EditActivity.class);
		    	y.putExtra("id", x.getUid());
		    	startActivity(y);
			}
		});
		//registerForContextMenu(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales, menu);
		return true;
	} 
}
