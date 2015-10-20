package com.mawujun.plugins.callActivityPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MyActivity extends Activity {
	private TextView textView;

	private int flag = 0;

	private Intent intentNew = null;

	private Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		 Log.i("CallActivityPlugin", "已经进入MainActivity");
		setContentView(context.getResources().getIdentifier("my_activity",
				"layout", context.getPackageName()));

		intentNew = this.getIntent();

		textView = (TextView) findViewById(context.getResources()
				.getIdentifier("message", "id", context.getPackageName()));

//		btn.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				Intent mIntent = new Intent();
//				mIntent.putExtra("change01", "1000");
//				mIntent.putExtra("change02", "2000");
//				// 设置结果，并进行传送
//				setResult(RESULT_OK, mIntent);
//				finish();
//			}
//		});

	}
}
