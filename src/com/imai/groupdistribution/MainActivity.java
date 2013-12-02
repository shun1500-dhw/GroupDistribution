package com.imai.groupdistribution;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	private SharedPreferences checkBoxPref;
	private SharedPreferences.Editor editor;

	private static final String[] allNameTxt = {
			"安達",
			"一瀬",
			"今井",
			"岩崎(大)",
			"岩崎(拓)",
			"岩塚",
			"大司",
			"大津",
			"嘉村",
			"神田",
			"楠元",
			"桑原",
			"小西",
			"佐藤",
			"柴田",
			"大力",
			"高倉",
			"壇",
			"能島",
			"野田",
			"松島",
			"松本",
			"山口",
			"山本"
	};

	private CustomAdapter adapter;
	private List<ListViewArrayAdapterData> allNameList = new ArrayList<ListViewArrayAdapterData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		checkBoxPref = getSharedPreferences("CheckBoxState", Activity.MODE_PRIVATE);

		ListView listView = (ListView) findViewById(R.id.allList);
		Button button = (Button) findViewById(R.id.button1);

		if (checkBoxPref != null) {
			//保存データがある場合
			for (int i = 0; i < allNameTxt.length; i++) {
				ListViewArrayAdapterData adapterData =
						new ListViewArrayAdapterData(checkBoxPref.getBoolean("check" + i, true), allNameTxt[i]);
				allNameList.add(adapterData);
			}
		} else {
			for (int i = 0; i < allNameTxt.length; i++) {
				ListViewArrayAdapterData adapterData =
						new ListViewArrayAdapterData(true, allNameTxt[i]);
				allNameList.add(adapterData);
			}
		}

		adapter = new CustomAdapter(this, R.layout.check, allNameList);
		// アダプターを設定します
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);
		button.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ

		ListViewArrayAdapterData data = adapter.getItem(position);
		data.setCheckBoxChecked(!data.isCheckBoxChecked());
		//これを入れないとチェックボックスが変わらないよ
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch (v.getId()) {
		case R.id.button1:

			//チェックされているメンバだけを別リストにつないでintentで渡す
			ArrayList<String> selectNameList = new ArrayList<String>();
			//いっしょにチェックボックスの状態を覚える
			editor = checkBoxPref.edit();

			for (int i = 0; i < allNameList.size(); i++) {
				ListViewArrayAdapterData adapterData = allNameList.get(i);
				editor.putBoolean("check" + i, adapterData.isCheckBoxChecked());

				if (adapterData.isCheckBoxChecked()) {
//					Log.i("debug", adapterData.getTextViewText());
					selectNameList.add(adapterData.getTextViewText());
				}
			}

			editor.commit();

			Intent intent = new Intent(MainActivity.this, GroupActivity.class);
			intent.putStringArrayListExtra("list", selectNameList);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	public class CustomAdapter extends ArrayAdapter<ListViewArrayAdapterData> {
		private int resource;

		public CustomAdapter(Context context, int resource, List<ListViewArrayAdapterData> objects) {
			super(context, resource, objects);
			// TODO 自動生成されたコンストラクター・スタブ
			//リソースファイルは後で使うので、メンバ変数に保持しておく。
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自動生成されたメソッド・スタブ
			/* レイアウトを生成する */
			convertView = View.inflate(getContext(), this.resource, null);

			/* 生成したViewから各パーツを取得する */
			CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check);
			TextView nametxt = (TextView) convertView.findViewById(R.id.name);

			ListViewArrayAdapterData item = getItem(position);

			checkBox.setChecked(item.isCheckBoxChecked());
			nametxt.setText(item.getTextViewText());

			return convertView;
		}

	}

	/**
	 * ListViewの初期値設定クラス。
	 * {@link ListViewArrayAdapter}のコンストラクタで使用する
	 */
	class ListViewArrayAdapterData {
		/** {@link R.id#delCheck} に対するcheck状態 */
		private boolean checkBoxChecked = true;
		/** {@link R.id#name} に対するtext */
		private String textViewText = "";

		/**
		 * View内データの初期値設定。
		 * {@link R.id#name}のtextと{@link R.id#delCheck}のcheck状態の初期値を設定する。
		 * @param textViewText {@link R.id#name}のtext
		 * @param data2 {@link R.id#delCheck}のcheck状態
		 */
		ListViewArrayAdapterData(boolean data, String textViewText) {
			this.checkBoxChecked = data;
			this.textViewText = textViewText;
		}

		public boolean isCheckBoxChecked() {
			return checkBoxChecked;
		}

		public void setCheckBoxChecked(boolean checkBoxChecked) {
			this.checkBoxChecked = checkBoxChecked;
		}

		public String getTextViewText() {
			return textViewText;
		}

		public void setTextViewText(String textViewText) {
			this.textViewText = textViewText;
		}
	}

}
