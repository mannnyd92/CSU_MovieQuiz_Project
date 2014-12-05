package com.example.moviequiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class FinalResult extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_result);
		display();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.final_result, menu);
		return true;
	}
	public void display(){
		Bundle bundle = getIntent().getExtras();
		int answered = bundle.getInt("answered");
		int score = bundle.getInt("score");
		TextView quest = (TextView) findViewById(R.id.textView1);
		quest.setText("Attempted: " + answered + "/10" + "\n" + "Correct: " + score);
	}
}
