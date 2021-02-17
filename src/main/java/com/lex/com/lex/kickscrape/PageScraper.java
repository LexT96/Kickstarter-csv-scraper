package com.lex.com.lex.kickscrape;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;

public class PageScraper {
	
	private static Document doc;

	// get all relevant data from the given projectLink
	protected static Project getData(String projectLink) throws IOException {
		try {
			System.out.println("Link: " + projectLink);
			doc = Jsoup.connect(projectLink)
				      .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
				      .referrer("http://www.google.com")
				      .timeout(120000)
				      .get();
			Boolean succeeded = checkIfSucceeded();	
			String duration = getDuration();
			String location = getLocation();
			Boolean isUS = checkIfAmericanProject(location);				
			String timeFrame = getTimeFrame();
			String comments = getComments();
			String updates = getUpdates();
			String faq = getFAQ();
			String rewardOptions = getRewardOptions();
			String backers = getBackers(succeeded);
			String amountPledged = getAmountPledged(succeeded);
			String goal = getGoal(succeeded);		
			Project p = new Project(projectLink, succeeded, goal, amountPledged, timeFrame, duration,
						location, isUS, backers, comments, updates, rewardOptions, faq);
			return p;


		} 
		catch (IOException e) {
			System.out.println("Could not connect to " + projectLink);
			e.printStackTrace();
			}
		return new Project(null,null,null,null,null,null,null,null,null,null,null,null,null);
	}
	
	// get the amount of the financial goal set by the starter
	private static String getGoal (Boolean succeeded) {
		if (succeeded)
			return doc.getElementsByClass("spotlight-project-video-archive").select("span.money").get(1).text().replace(',', '.');
		return getGoalFromJson();
		
	}
	
	// get the amount of people that backed the project
	private static String getBackers (Boolean succeeded) {
		if (succeeded)
			return doc.getElementsByClass("NS_campaigns__spotlight_stats").select("b").text().split(" ")[0];
		else 
			return doc.getElementById("react-project-header").attr("data-initial").split("\"backersCount\":")[1].split(",\"percentFunded\"")[0];
	}
	
	// get the location of the project starter
	private static String getLocation () {
		return doc.select("a[href*=/places]").first().text().replace(',', '.');
	}
	
	// check if the project has been successfully funded
	private static Boolean checkIfSucceeded () {
		return doc.getElementsByClass("NS_campaigns__spotlight_stats").size() > 0;
	}
	
	// check if the project was created in the US
	private static boolean checkIfAmericanProject (String location) {
		// trim location to state / country
		location = location.split(". ")[1];
		// if the length of the string isn't equal to 2 it can't be an American project
		if (location.length() != 2)
			return false;
		// create a string array for all US-states
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME"
                , "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", 
                "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		// check if the project location is an US state and return the result
		  for (String state : states) 
			  if (state.equals(location)) 
				  return true;
		  return false;		 		
	}
	
	// get the amount of comments written by backers and the starter
	private static String getComments () {
		return doc.getElementById("comments-emoji").attr("data-comments-count");
	}
	
	// get the amount of updates posted by the starter
	private static String getUpdates () {
		return doc.getElementById("updates-emoji").attr("emoji-data");
	}
	
	// get the amount of questions answered in the FAQ
	private static String getFAQ () {
		return doc.getElementById("faq-emoji").attr("emoji-data");
	}
	
	// get the time frame of the project in the format "start date - end date"
	private static String getTimeFrame() {
		return doc.getElementsByClass("NS_campaigns__funding_period").select("p.f5").text().split(" \\(")[0].replace(',', '.');
	}
	
	// get the duration of the campaign
	private static String getDuration () {
		return doc.getElementsByClass("NS_campaigns__funding_period").select("p.f5").text().split(" \\(")[1].split(" days")[0];
		
	}
	
	// get the amount of reward options
	private static String getRewardOptions () {
		return String.valueOf(doc.getElementsByClass("pledge-selectable-sidebar").size());		
	}
	
	// get the total amount that was pledged by the backers
	private static String getAmountPledged (Boolean succeeded) {
		if (succeeded)
			return doc.getElementsByClass("NS_campaigns__spotlight_stats").select("span.money").text().replace(',', '.');
		else 
			return getAmountPledgedFromJson();
	}
		
	// helper method to parse "react-project-header" that is given in json
	private static JsonObject parseJsonHeader() {
		Gson gson = new Gson();
		String jsonString =  doc.getElementById("react-project-header").attr("data-initial");
		JsonElement element = gson.fromJson(jsonString,JsonElement.class);
		JsonObject object = element.getAsJsonObject();
		JsonElement project = object.get("project");
		return project.getAsJsonObject();
	}
	
	// helper method to get the amount pledged and the currency from the json header
	private static String getAmountPledgedFromJson() {
		JsonObject pledged = parseJsonHeader().getAsJsonObject("pledged");
		String currency = null;
		String amountPledged = null;
		for (Map.Entry<String,JsonElement> entry : pledged.entrySet()) {
			if (entry.getKey().equals("currency"))
				currency = entry.getValue().toString();
			else if (entry.getKey().equals("amount"))
				amountPledged = entry.getValue().toString();
			}
		return new StringBuilder().append(currency + " " + amountPledged).toString().replace("\"", "").replace(',', '.');		
	}
	
	// helper method to get the goal amount and the currency from the json header
	private static String getGoalFromJson() {
		JsonObject goal = parseJsonHeader().getAsJsonObject("goal");
		String currency = null;
		String amountGoal = null;
		for (Map.Entry<String,JsonElement> entry : goal.entrySet()) {
			if (entry.getKey().equals("currency"))
				currency = entry.getValue().toString();
			else if (entry.getKey().equals("amount"))
				amountGoal = entry.getValue().toString();
			}
		return new StringBuilder().append(currency + " " + amountGoal).toString().replace("\"", "").replace(',', '.');	
	}

}