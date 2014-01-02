package com.example.testprojnet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		final EditText edit = (EditText) findViewById(R.id.editText);
		Button but = (Button) findViewById(R.id.but);
		
		but.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s = edit.getText().toString();
				//Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Settings.this,MainActivity.class);
				Bundle b = new Bundle();
				b.putString("ip", s);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
