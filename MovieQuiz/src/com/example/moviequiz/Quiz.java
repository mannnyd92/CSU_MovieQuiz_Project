package com.example.moviequiz;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quiz extends Activity {
	TextView quest;
	RadioButton button1;
	RadioButton button2;
	RadioButton button3;
	RadioButton button4;
	RadioGroup radiogroup;

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
		TextView tv = (TextView) findViewById(R.id.textView2);
		Questions temp = questions.get(p);
		quest.setText((p+1) + ". " + questions.get(p).que);	
		button1.setText(questions.get(p).opt1);
		button2.setText(questions.get(p).opt2);
		button3.setText(questions.get(p).opt3);
		button4.setText(questions.get(p).opt4);
		
		radiogroup.clearCheck();
		
		if(temp.answeredBool == true){
			if(temp.selected.equals(temp.opt1)){
				button1.setChecked(true);
			}
			if(temp.selected.equals(temp.opt2)){
				button2.setChecked(true);
			}
			if(temp.selected.equals(temp.opt3)){
				button3.setChecked(true);
			}
			if(temp.selected.equals(temp.opt4)){
				button4.setChecked(true);
			}
		}
		
		if(temp.answeredBool == true){
			if(questions.get(position).selected.equals(questions.get(position).correct)){
				tv.setText(R.string.correct);
				tv.setTextColor(Color.GREEN);
			}
			else{
				tv.setText(R.string.incorrect);
				tv.setTextColor(Color.RED);
			}
		}else{
			tv.setText("");
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
		questions.add(q.que3);
		questions.add(q.que4);
		questions.add(q.que5);
		questions.add(q.que6);
		questions.add(q.que7);
		questions.add(q.que8);
		questions.add(q.que9);
		questions.add(q.que10);
		
		quest = (TextView) findViewById(R.id.textView1);
		button1 = (RadioButton) findViewById(R.id.radio0);
		button2 = (RadioButton) findViewById(R.id.radio1);
		button3 = (RadioButton) findViewById(R.id.radio2);
		button4 = (RadioButton) findViewById(R.id.radio3);
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup1);
		
		display(position);
	}
	
	public void next(View view){
		if(position == 9){
			finish(view);
		}else{position++;}
		display(position);
	}
	
	public void back(View view){
		if(position == 0){
			position = 9;
		}else{position--;}
		display(position);
	}
	
	public void answer(View view){
		TextView tv = (TextView) findViewById(R.id.textView2);
		
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
		if(questions.get(position).selected.equals(questions.get(position).correct)){
			tv.setText(R.string.correct);
			tv.setTextColor(Color.GREEN);
		}
		else{
			tv.setText(R.string.incorrect);
			tv.setTextColor(Color.RED);
		}
		
	}
	
	public void finish(View view){
		int answered = 0;
		int score = 0;
		for(int i = 0; i<10; i++){
			if(questions.get(i).answeredBool == true){
				answered++;
			}
			if(questions.get(i).selected.equals(questions.get(i).correct)){
				score++;
			}
		}
		
		Intent intent = new Intent(this, FinalResult.class);
		Bundle bundle = new Bundle();
		bundle.putInt("answered", answered );
		bundle.putInt("score", score);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
