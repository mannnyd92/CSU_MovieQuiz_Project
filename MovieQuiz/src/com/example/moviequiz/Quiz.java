package com.example.moviequiz;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Quiz extends Activity {


	ArrayList<Questions> questions = new ArrayList<Questions>();
	int position = 0;
	
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
	
	protected void display(int p){
		Questions temp = questions.get(p);
		TextView quest = (TextView) findViewById(R.id.textView1);
		quest.setText(questions.get(p).que);
		RadioButton button1 = (RadioButton) findViewById(R.id.radio0);
		button1.setText(questions.get(p).opt1);
		RadioButton button2 = (RadioButton) findViewById(R.id.radio1);
		button2.setText(questions.get(p).opt2);
		RadioButton button3 = (RadioButton) findViewById(R.id.radio2);
		button3.setText(questions.get(p).opt3);
		RadioButton button4 = (RadioButton) findViewById(R.id.radio3);
		button4.setText(questions.get(p).opt4);
		
		if(temp.answeredBool == true){
			if(temp.selected.equals(temp.opt1)){
				button1.setChecked(true);
			}
			if(temp.selected.equals(temp.opt2)){
				button1.setChecked(true);
			}
			if(temp.selected.equals(temp.opt3)){
				button1.setChecked(true);
			}
			if(temp.selected.equals(temp.opt4)){
				button1.setChecked(true);
			}
		}
	}
	
	protected void getQuestions(){
		Resources resources = getResources();
		AssetManager file = resources.getAssets();
		
		Questions q = new Questions();
		q.readQuestions(file);
		q.getRandom();
		q.makeQuestions();

		questions.add(q.que1);
		questions.add(q.que2);
		questions.add(q.que2);
		questions.add(q.que3);
		questions.add(q.que4);
		questions.add(q.que5);
		questions.add(q.que6);
		questions.add(q.que7);
		questions.add(q.que8);
		questions.add(q.que9);
		questions.add(q.que10);
		display(position);
	}
	
	public void next(View view){
		if(position == 9){
			position = 0;
		}
		else{position++;}
		display(position);
	}
	
	public void back(View view){
		if(position == 0){
			position = 9;
		}else{position--;}
		display(position);
	}
	
	public void answer(View view){
		RadioButton button1 = (RadioButton) findViewById(R.id.radio0);
		RadioButton button2 = (RadioButton) findViewById(R.id.radio1);
		RadioButton button3 = (RadioButton) findViewById(R.id.radio2);
		RadioButton button4 = (RadioButton) findViewById(R.id.radio3);
		
		if(button1.isChecked()){
			questions.get(position).setAnswered(true);
			questions.get(position).setSelected(questions.get(position).opt1);
		}
		if (button2.isChecked()){
			questions.get(position).setAnswered(true);
			questions.get(position).setSelected(questions.get(position).opt2);
		}
		if (button3.isChecked()){
			questions.get(position).setAnswered(true);
			questions.get(position).setSelected(questions.get(position).opt3);
		}
		if (button4.isChecked()){
			questions.get(position).setAnswered(true);
			questions.get(position).setSelected(questions.get(position).opt4);
		}
		
	}
	
	public void finish(View view){
//		String tally
//		Bundle bundle = new Bundle();
//		bundle.putString("data", )
		Intent intent = new Intent(this, FinalResult.class);
		startActivity(intent);
	}

}
