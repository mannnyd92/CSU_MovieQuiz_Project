package com.example.moviequiz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Questions {
	 String que;
	 String opt1;
	 String opt2;
	 String opt3;
	 String opt4;
	 String correct;
	 ArrayList<String> questions = new ArrayList <String>();
	 Map <Integer, String> map = new HashMap();
	 ArrayList<Integer> random = new ArrayList <Integer>();
	 Questions que1;
	 Questions que2;
	 Questions que3;
	
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

	public void readQuestions(String file){
		File input = new File(file);
	
		int count = 0;
		try{
			Scanner scan = new Scanner(input);
		
			System.out.println("test");
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
//				System.out.println("Que= " + que);
//				System.out.println("opt1= " + opt1 + " opt2= " + opt2 + " opt3= " + opt3 + " opt4= " + opt4);
//				
				//Questions quest = new Questions(que, opt1, opt2, opt3, opt4, correct);
				
				//System.out.println("quest= " + quest);
				
				map.put(count, que + "|" + opt1 + "|" + opt2 + "|" + opt3 + "|" + opt4 + "|" + correct);
//				questions.add(opt1);
//				questions.add(opt2);
//				questions.add(opt3);
//				questions.add(opt4);
//				
				//map.put(count, questions);
				count++;
				
				
					
				
			}
			//System.out.println(map);
		}catch(IOException e){System.out.println("exception");}
		
	}
	public void getRandom(){
		Random ran = new Random();
		while(random.size() != 10){
			//TODO change random number range to fifty once all questions are in.
			int rando = ran.nextInt(15);
				if(!random.contains(rando)){
					random.add(rando);
				}
			}
		System.out.println(random);
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
		Questions que4 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(4));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que5 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(5));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que6 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(6));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que7 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(7));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que8 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(8));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que9 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		quest = map.get(random.get(9));
		single = quest.split("\\|");
		que = single[0];
		opt1 = single[1];
		opt2 = single[2];
		opt3 = single[3];
		opt4 = single[4];
		correct = single[5];
		Questions que10 = new Questions(que, opt1, opt2, opt3, opt4, correct);
		
		System.out.println(que1 + "\n" + que2 + "\n" + que3 + "\n" + que4 + "\n" + que5 + "\n" + que6 + "\n" + que7 + "\n" + que8 + "\n" + que9 + "\n" + que10 );
	}
	public String toString(){
		String out;
		out = que + "\n" + opt1 + "\n" + opt2 + "\n" + opt3 + "\n" + opt4;
		return out;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Questions q = new Questions();
		q.readQuestions("P4Trivia");
		q.getRandom();
		q.makeQuestions();
//		for(int i=0; i < questions.size(); i++ ){
//			System.out.println(questions.get(i));
//		}
		//Questions Q1 = new Questions();
		
	}

}
