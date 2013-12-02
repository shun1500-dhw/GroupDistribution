package com.imai.groupdistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GroupActivity extends Activity {

	private static final int GROUP_MAX_NUM = 4;
	private String snsStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		ListView listView = (ListView) findViewById(R.id.groupList);
		Button snsBtn = (Button) findViewById(R.id.snsBtn);

		Intent intent = getIntent();
		ArrayList<String> allNameList = intent.getStringArrayListExtra("list");

		SampleAdapter addNameList = new SampleAdapter(this);

		Collections.shuffle(allNameList);

		//出席人数からグループ数をもとめて、グループNoをハッシュキーにして
		//ひとりずつ別グループに振り分けていく
		HashMap<Integer, ArrayList<String>> nameMap = new HashMap<Integer, ArrayList<String>>();

		//グループ最大人数(デフォは4名)からグループ数を求める
		int groupNum = 0;
		if ((allNameList.size() % GROUP_MAX_NUM) == 0) {
			//グループ最大人数で割り切れた場合は商がそのままグループ数に
			groupNum = allNameList.size() / GROUP_MAX_NUM;
		} else {
			//グループ最大人数で割り切れない場合は商+1がグループ数に
			groupNum = allNameList.size() / GROUP_MAX_NUM + 1;
		}

		//HashMapにグループ数分のArrayListをつないでおく
		for (int i = 0; i < groupNum; i++) {
			//keyは0からグループ数-1
			nameMap.put(i, new ArrayList<String>());
		}

		//出席者をグループに振り分ける処理
		//グループ1←出席者1, グループ2←出席者2, グループ3←出席者3, , グループ4←出席者4
		//グループ1←出席者5, グループ2←出席者6, グループ3←出席者7, , グループ4←出席者8
		//という振り分け方
		ArrayList<String> tempList;
		int groupIndex = 0;
		for (int i = 0; i < allNameList.size(); i++) {
			tempList = nameMap.get(groupIndex);
			tempList.add(allNameList.get(i));

			groupIndex++;
			if (groupIndex >= groupNum) {
				groupIndex = 0;
			}
		}

		//グループ番号順にリストに表示する
		//同時にSNS投稿用テキスト作成
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < groupNum; i++) {
			tempList = nameMap.get(i);
			addNameList.add(new BindData("[グループ" + (i + 1) + "]", false));
			sb.append("[グループ" + (i + 1) + "]\n");

			for (int j = 0; j < tempList.size(); j++) {
				addNameList.add(new BindData(tempList.get(j), true));
				sb.append(tempList.get(j) + "\n");
			}

		}
		listView.setAdapter(addNameList);
		snsStr = new String(sb); 

		snsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, snsStr);
				startActivity(intent);
			}
		});
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
