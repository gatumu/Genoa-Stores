package com.themega.genoastores;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class UnsyncedActivity extends Activity {
	ListView list;
	CustomAdapter adapter;
	Database db;
	ArrayList<Sale> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unsynced);
		
		list = (ListView) findViewById(R.id.lvUnsynced);
		db = new Database(this);
		data = db.fetch("unsynced");
		adapter = new CustomAdapter(this, data);
		list.setAdapter(adapter);
		registerForContextMenu(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unsynced, menu);
		return true;
	}
	
	public void sync(View v){
		String url = "http://192.168.1.82/genoa/sync.php";
		final Database db = new Database(this);
		ArrayList<Sale> array = db.fetch("unsynced");
		Gson gson = new Gson();
		String data = gson.toJson(array);
		RequestParams params = new RequestParams();
		params.put("json", data);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(url, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				String resp = new String(response);
				try {
					JSONArray jArray = new JSONArray(resp);
					for (int i = 0; i < jArray.length(); i++) {
						String uid = jArray.getJSONObject(i).getString("uid");
						String status = jArray.getJSONObject(i).getString("status");
						db.update(uid, status);
					}
				} catch (JSONException e) {
					Log.e("JSON", "JSON error");
				}
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
