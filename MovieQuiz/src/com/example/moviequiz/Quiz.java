package com.example.moviequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Quiz extends Activity {

	Questions q = new Questions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		getQuestions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	protected void getQuestions(){
		
		
	}
	
	public void next(View view){
		
	}
	
	public void back(View view){
		
	}
	
	public void answer(View view){
		
	}
	
	public void finish(View view){
		Intent intent = new Intent(this, FinalResult.class);
		startActivity(intent);
	}

}
