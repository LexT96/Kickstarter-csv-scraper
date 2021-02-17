package com.lex.com.lex.kickscrape;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
	
	
	public static String[] readLines (String fileName) {
		try {
			String row;
			
			BufferedReader csvReader = new BufferedReader(new FileReader("input.csv"));
			while ((row = csvReader.readLine()) != null) {
				String[] links = row.split(",");
				csvReader.close();
				return links;
			}		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
