package com.lex.com.lex.kickscrape;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class CSVWriter {
	
	public static void write (List<Project> projects) throws IOException {
	Writer csvWriter = new OutputStreamWriter(new FileOutputStream("output.csv"), "UTF-8");
	csvWriter.append("projectLink,succeeded,goal,amountPledged,timeFrame,duration,location,isUs,backers,comments,updates,rewardOptions,faq\n");
	
	for (Project p : projects) {
	    csvWriter.append(p.toString());
	    csvWriter.append("\n");
	}
	csvWriter.flush();
	csvWriter.close();

	}

}
