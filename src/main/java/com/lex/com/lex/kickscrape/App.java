package com.lex.com.lex.kickscrape;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        String[] links = CSVReader.readLines("input.csv");
        List<Project> projects = new LinkedList<Project>();
        for (String link : links) {
				projects.add(PageScraper.getData(link));
				Thread.sleep(10000);
        }
        CSVWriter.write(projects);
        
    }
}
