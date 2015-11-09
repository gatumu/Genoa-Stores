package com.themega.genoastores;

/*Custom Adapter class*/
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<Sale> data;

	public CustomAdapter(Context context, ArrayList<Sale> data) {
		this.mContext = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size(); //no of items in array list
	}

	@Override
	public Object getItem(int position) {
		return data.get(position); //get the actual movie
	}

	@Override
	public long getItemId(int id) {
		return id; //get the item ID
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null)
		{
		  LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
		  convertView=inflater.inflate(R.layout.list_item_layout, parent,false);
		  viewHolder = new ViewHolder();
		  viewHolder.uuidTextView = (TextView) convertView.findViewById(R.id.tvUID);
		  viewHolder.dateTextView =(TextView) convertView.findViewById(R.id.tvDate);
		  viewHolder.productTextView =(TextView) convertView.findViewById(R.id.tvProduct);
		  viewHolder.qtyTextView =(TextView) convertView.findViewById(R.id.tvLocation);
		  viewHolder.namesTextView =(TextView) convertView.findViewById(R.id.tvNames);
		  viewHolder.locTextView =(TextView) convertView.findViewById(R.id.tvLocation);
		  convertView.setTag(viewHolder);
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Sale sale = data.get(position);
		viewHolder.uuidTextView.setText(sale.getUid());
		viewHolder.dateTextView.setText(sale.getDate());
		viewHolder.productTextView.setText(sale.getProduct());
		viewHolder.qtyTextView.setText(String.valueOf(sale.getQty()));
		viewHolder.namesTextView.setText(sale.getCustomer_names());
		viewHolder.locTextView.setText(sale.getCustomer_location());
		
		return convertView;
	}

	static class ViewHolder {
		TextView uuidTextView;
		TextView dateTextView;
		TextView productTextView;
		TextView qtyTextView;
		TextView namesTextView;
		TextView locTextView;
	}

}
/***********END OF CUSTOM ADAPTER*************/
