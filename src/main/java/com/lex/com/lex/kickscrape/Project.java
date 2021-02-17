package com.lex.com.lex.kickscrape;

public class Project {
	
	protected String projectLink;
	protected Boolean succeeded;
	protected String goal;
	protected String amountPledged;
	protected String timeFrame;
	protected String duration;
	protected String location;
	protected Boolean isUS;	
	protected String backers;
	protected String comments;
	protected String updates;
	protected String rewardOptions;
	protected String faq;
	
	public Project(String projectLink, Boolean succeeded, String goal, String amountPledged, String timeFrame, String duration,
			String location, Boolean isUS, String backers, String comments, String updates, String rewardOptions,
			String faq) {
		this.projectLink = projectLink;
		this.succeeded = succeeded;
		this.goal = goal;
		this.amountPledged = amountPledged;
		this.timeFrame = timeFrame;
		this.duration = duration;
		this.location = location;
		this.isUS = isUS;
		this.backers = backers;
		this.comments = comments;
		this.updates = updates;
		this.rewardOptions = rewardOptions;
		this.faq = faq;
	}

	public String toString() {
		return projectLink + "," + succeeded + "," + goal + "," + amountPledged + "," + timeFrame + "," + duration + "," + location + "," 
				+ isUS + "," + backers + "," + comments + "," + updates + "," + rewardOptions + "," + faq;
	}
	

}
