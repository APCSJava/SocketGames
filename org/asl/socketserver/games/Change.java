package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.Servable;

public class Change implements Servable { 
	
		BufferedReader input;
		PrintWriter out;
		Change change = new Change(); 
	void change() {
	}	

	public void serve (BufferedReader input, PrintWriter out) throws IOException {
		this.input = input;
		this.out = out;
		boolean run = true;
		while(run){
		int choice = runMenu();
		if(choice == 0){
			double price = getPrice();
			double payment = getPayment(price);
			double change = calculateChange(price, payment);
			runChange(change);
		}else{
			run = false;
		}		
	}
		}
	public int runMenu(){
		System.out.println("------------\n" 
			+ "Welcome to Change" + "--------------\n" 
			+   "------------\n"
			+"0 - Run Change \n" 
			+"1 - Exit \n"
			+"------------- \n"
			+"Press to choose an option");
		//input.read();
		try {
			return input.read();
		}catch(IOException e) {
			return 0;
		}


	}
	public double calculateChange(double price, double payment){
		return payment - price;
	}
	public double getPrice(){
		try {
		//out.println()
		out.println("Please enter price of product ($) : ");
		
		double price = input.read();
		
		while(price > 100){
			out.println("Over one hundred");
			out.println("Please enter again:");
			price = input.read();
		}
		return price;
		}catch (IOException e) {
			return 0;
		}
	}
	public double getPayment(double price){
		try {
		out.println("Please enter payment amount ($) : ");
		double choice = input.read();
		while(choice < price){
			out.println("Entered amount is short changed");
			out.println("Please enter again");
			choice = input.read();
		}
		return choice;
		}catch (IOException e) {
			return 0;
		}
	}

	public void runChange(double change){
		change = printChange(change, 50);
		change = printChange(change, 20);
		change = printChange(change, 10);
		change = printChange(change, 5);
		change = printChange(change, 1);
		change = printChange(change, 0.25);
		change = printChange(change, 0.10);
		change = printChange(change, 0.01);


	}
	public double printChange(double bill, double change){
		int count = 0;
		while(change >= bill){
			change = change - bill;
			count++;
		}

		if(count > 0) {
			out.println("Give " + count + " x $" + bill);
		}
		return change;
	}
}
	
