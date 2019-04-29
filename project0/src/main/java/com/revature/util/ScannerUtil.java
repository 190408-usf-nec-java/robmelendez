package com.revature.util;

import java.util.Scanner;

public class ScannerUtil {
	private static Scanner scanner = new Scanner(System.in);
	
	public static int getNumericChoice(int max) {
		int choice = -1;
		
		while (choice < 0 || choice > max) {
			
			if (scanner.hasNext()) {
				if (!scanner.hasNextInt()) {
					scanner.nextLine();
					continue;
				}
				choice = scanner.nextInt();
				// Next int will leave the new line character
				// after the user hit enter in the buffer
				// nextLine will clear the buffer out
				scanner.nextLine(); 
			}
		}
	
		return choice;
	}

	public static String getLine() {
		return scanner.nextLine();
	}
	
	public static double getDouble() {
		return scanner.nextDouble();
	}

	public static int getInt() {
		return scanner.nextInt();
	}
	
	
}
