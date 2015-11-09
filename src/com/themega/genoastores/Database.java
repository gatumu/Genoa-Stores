package com.themega.genoastores;

import java.util.ArrayList;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{
	//sqlLite
	public Database(Context context) {
		super(context, "genoa_stores", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sales_tbl="CREATE TABLE IF NOT EXISTS sales " + 
			" (id INTEGER PRIMARY KEY AUTOINCREMENT," + 
			" uid TEXT NOT NULL, " +
			" date TEXT NOT NULL, " + 
			" product TEXT NOT NULL, " + 
			" qty INTEGER NOT NULL, " + 
			" customer_names TEXT NOT NULL, " +
			" customer_location TEXT NOT NULL, " +
			" status TEXT NOT NULL)";
		
		db.execSQL(sales_tbl);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String drop_sales="DROP TABLE IF EXISTS sales";
		db.execSQL(drop_sales);
	}

	public void save(Sale s){
		SQLiteDatabase database = this.getWritableDatabase(); //db access       
        ContentValues values = new ContentValues(); //prepare values
        UUID uid = UUID.randomUUID();
        values.put("uid", uid.toString());
        values.put("date", s.getDate());
        values.put("product", s.getProduct());
        values.put("qty", s.getQty());
        values.put("customer_names", s.getCustomer_names());
        values.put("customer_location", s.getCustomer_location());
        values.put("status", "no");
        database.insert("sales", null, values); //insert values
        database.close(); //close db
	}

	public ArrayList<Sale> fetch(String all){
		ArrayList<Sale> data = new ArrayList<Sale>();
		SQLiteDatabase database = this.getWritableDatabase();
		//select all or unsynced
		String state = (all=="all") ? "" : " WHERE status='no'";
		String sql = "SELECT * FROM sales"+state;
		Cursor cursor = database.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			do{
				String uid = cursor.getString(1);
				String date = cursor.getString(2);
				String product = cursor.getString(3);
				int qty = cursor.getInt(4);
				String customer_names = cursor.getString(5);
				String customer_location = cursor.getString(6);
				String status = cursor.getString(7);
				Sale x = new Sale(uid, date, product, customer_names, customer_location, status, qty);
				data.add(x);
			}while(cursor.moveToNext());
		}
		database.close();
		return data;
	}

	public int count(){
        int count = 0;
        String selectQuery = "SELECT  * FROM sales";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
	}
	
	public Sale fetchOne(String uid){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM sales WHERE uid='"+uid+"'";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			String date = cursor.getString(2);
			String product = cursor.getString(3);
			int qty = cursor.getInt(4);
			String customer_names = cursor.getString(5);
			String customer_location = cursor.getString(6);
			String status = cursor.getString(7);
			Sale x = new Sale(uid, date, product, customer_names, customer_location, status, qty);
			return x;
		}
		db.close();
		return null;
	}
	public void edit(ContentValues values, String id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.update("sales", values, "uid=?", new String[]{id});
		db.close();
	}
	public void update(String uid, String status){
		SQLiteDatabase database = this.getWritableDatabase(); //db access       
        String sql = "UPDATE sales SET status='"+status+"' WHERE uid='"+uid+"'";
        database.execSQL(sql);
        database.close();
	}
}
