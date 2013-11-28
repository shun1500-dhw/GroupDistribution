package com.imai.groupdistribution;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		ListView listView = (ListView) findViewById(R.id.listView1);

		Intent intent = getIntent();
		ArrayList<String> allNameList = new ArrayList<String>();
		SampleAdapter addNameList = new SampleAdapter(this);

		allNameList = intent.getStringArrayListExtra("list");

		Collections.shuffle(allNameList);

		int groupNum = 0;
		for (int i = 0; i < allNameList.size(); i++) {
			if (i % 4 == 0) {
				groupNum++;
				addNameList.add(new BindData("グループ" + groupNum, false));
			}
			addNameList.add(new BindData(allNameList.get(i), true));
			Log.i("debug", allNameList.get(i));
		}
		listView.setAdapter(addNameList);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		return true;
	}

	/**
	 * ﾋﾞｭｰﾎﾙﾀﾞｰ
	 */
	private static class ViewHolder {
		TextView mTextView;
	}

	/**
	  * ｱﾀﾞﾌﾟﾀｰ
	  */
	private class SampleAdapter extends ArrayAdapter {

		/** ﾚｲｱｳﾄ */
		final static int LAYOUT = R.layout.next;

		/** ｲﾝﾌﾚｰﾀｰ */
		private LayoutInflater mInflater;

		/**
		 * ｺﾝｽﾄﾗｸﾀ
		 * @param context  ｺﾝﾃｷｽﾄ
		 */
		public SampleAdapter(Context context) {
			super(context, LAYOUT);
			mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		/**
		 *  getView
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			// ﾋﾞｭｰ確保
			if (convertView == null) {
				convertView = mInflater.inflate(LAYOUT, null);
				holder = new ViewHolder();
				holder.mTextView = (TextView) convertView.findViewById(R.id.groupTitle);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// ﾃﾞｰﾀ確保
			BindData bindData = (BindData) getItem(position);

			// 共通処理
			holder.mTextView.setText(bindData.mMessage);

			// ｺﾝﾃﾝﾂﾉ処理
			if (bindData.mIsContent) {
				holder.mTextView.setBackgroundColor(Color.BLACK);
			}

			// ﾗﾍﾞﾙﾉ処理
			else {
				holder.mTextView.setBackgroundColor(Color.DKGRAY);
			}
			return convertView;
		}
	}

	/**
	 * ﾃﾞｰﾀ
	 */
	private class BindData {

		/** 表示ｻｾﾙﾒｯｾｰｼﾞ */
		private String mMessage;

		/** ｺﾝﾃﾝﾂｶ否ｶ */
		private boolean mIsContent;

		/**
		 * ｺﾝｽﾄﾗｸﾀ
		 * @param message    表示ｻｾﾙﾒｯｾｰｼﾞ
		 * @param isContent    ｺﾝﾃﾝﾂｶ否ｶ
		 */
		public BindData(String message, boolean isContent) {
			mMessage = message;
			mIsContent = isContent;
		}

	}

}
