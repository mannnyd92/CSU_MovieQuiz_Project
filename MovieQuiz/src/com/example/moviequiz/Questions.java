package com.example.moviequiz;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import android.content.res.AssetManager;


public class Questions {
	 String que;
	 String opt1;
	 String opt2;
	 String opt3;
	 String opt4;
	 String correct;
	 boolean answeredBool = false;
	 String selected = "";
	 ArrayList<String> questions = new ArrayList <String>();
	 Map <Integer, String> map = new HashMap();
	 ArrayList<Integer> random = new ArrayList <Integer>();
	 Questions que1;
	 Questions que2;
	 Questions que3;
	 Questions que4;
	 Questions que5;
	 Questions que6;
	 Questions que7;
	 Questions que8;
	 Questions que9;
	 Questions que10;
	 
	public Questions(){
		
	}
	public Questions(String que, String opt1, String opt2, String opt3, String opt4, String correct){
		this.que = que;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.correct = correct;
	}
////setters and getters///////////////////////////////////////////////////////////////////////////////////////////////
	public String getCorrect(){
		return correct;
	}
	public void setCorrect(String s){
		correct = s;
	}
	public boolean getAnswered(){
		return answeredBool;
	}
	public void setAnswered(boolean set){
		answeredBool = set;
	}
	public String getSelected(){
		return selected;
	}
	public void setSelected(String x){
		selected = x;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readQuestions(AssetManager a){
		
		InputStream input = null;
		try {
			input = a.open("P4Trivia");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		int count = 0;
		Scanner scan = new Scanner(input);


		while(scan.hasNextLine()){
			
			que = scan.nextLine();
			opt1 = scan.nextLine();
			opt2 = scan.nextLine();
			opt3 = scan.nextLine();
			opt4 = scan.nextLine();
			
			if(opt1.contains("&")){
				opt1 = opt1.substring(1);
				correct = opt1;
			}
			
			if(opt2.contains("&")){
				opt2 = opt2.substring(1);
				correct = opt2;
			}
			if(opt3.contains("&")){
				opt3 = opt3.substring(1);
				correct = opt3;
			}
			if(opt4.contains("&")){
				opt4 = opt4.substring(1);
				correct = opt4;
			}

			
			map.put(count, que + "|" + opt1 + "|" + opt2 + "|" + opt3 + "|" + opt4 + "|" + correct);

			count++;
			
			
				
			
		}
		
	}
	public void getRandom(){
		Random ran = new Random();
		while(random.size() != 10){
			int rando = ran.nextInt(50);
				if(!random.contains(rando)){
					random.add(rando);
				}
			}
	}
	public void makeQuestions(){
		
		String quest = map.get(random.get(0));
		
		String [] single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que1 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(1));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que2 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(2));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que3 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(3));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que4 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(4));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que5 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(5));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que6 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(6));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que7 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(7));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que8 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(8));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que9 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(9));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		que10 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
	}

}
