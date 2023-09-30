package project;

import java.time.*;
import java.util.*;

import comparators.*;
import user.*;

public class ArtefactClass implements Artefact{ // README looks complete, care when changing

	private String name;
	private String projectName;
	private int confidentiality;
	//private LocalDate date;
	private String description;
	private SortedSet<Revision> revisions;  //sorted by date
	private User user;
	
	public ArtefactClass(String name, String projectName, int confidentiality ,LocalDate date ,String description, User user) {
		this.name = name;
		this.projectName = projectName;
		this.confidentiality = confidentiality;
		//this.date = date;
		this.description = description;
		this.revisions = new TreeSet<Revision>(new ComparatorDate());
		this.user = user;
		
		revise(description, date, user);
	}
	
	public Revision revise(String description, LocalDate date, User userResponsible) {
		Revision rev = new RevisionClass(date, description, getNumberOfRevisions()+1, userResponsible,
				this.projectName, this.name);
		revisions.add(rev);	
		return rev;
	}
	
	public LocalDate getLastRevisionDate() {
		return revisions.first().getDate();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int getConfidentiality() {
		return confidentiality;
	}
	
	public String getDescription() {
		return description;
	}
	
	public User getUser() {
		return user;
	}

	public Iterator<Revision> getRevisions(){
		return revisions.iterator();
	}
	
	public int getNumberOfRevisions() {
		return revisions.size();
	}
}
