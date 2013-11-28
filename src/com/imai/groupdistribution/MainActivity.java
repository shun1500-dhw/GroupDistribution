package com.imai.groupdistribution;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	private static final String[] allNameTxt = {
			"安達誠寛",
			"一瀬孝",
			"今井俊介",
			"岩崎大輔",
			"岩崎拓也",
			"岩塚美由紀",
			"大司まり",
			"大津良馬",
			"嘉村翼",
			"神田圭司",
			"楠元信吾",
			"桑原玲",
			"小西未央子",
			"佐藤章",
			"柴田久美子",
			"大力新太郎",
			"高倉健治",
			"壇義弘",
			"能島章典",
			"野田幸代",
			"松島あゆみ",
			"松本真由美",
			"山口徹",
			"山本康平"
	};

	private ArrayList<String> allNameList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView listView = (ListView) findViewById(R.id.listView1);
		Button button = (Button) findViewById(R.id.button1);

		for (int i = 0; i < allNameTxt.length; i++) {
			allNameList.add(allNameTxt[i]);

		}

		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(
						this,
						android.R.layout.simple_list_item_1,
						allNameList
				);
		// アダプターを設定します
		listView.setAdapter(adapter);

		button.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = new Intent(MainActivity.this, GroupActivity.class);
		intent.putStringArrayListExtra("list", allNameList);
		startActivity(intent);
	}

	/**
	 * ListViewの初期値設定クラス。
	 * {@link ListViewArrayAdapter}のコンストラクタで使用する
	 */
	class ListViewArrayAdapterData {
		/** {@link R.id#name} に対するtext */
		String textViewText = "";
		/** {@link R.id#delCheck} に対するcheck状態 */
		boolean checkBoxChecked = false;

		/**
		 * View内データの初期値設定。
		 * {@link R.id#name}のtextと{@link R.id#delCheck}のcheck状態の初期値を設定する。
		 * @param textViewText {@link R.id#name}のtext
		 * @param data2 {@link R.id#delCheck}のcheck状態
		 */
		ListViewArrayAdapterData(String textViewText, boolean data2) {
			this.textViewText = textViewText;
			this.checkBoxChecked = data2;
		}
	}
}
