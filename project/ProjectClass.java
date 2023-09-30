package project;

import java.time.*;
import java.util.*;

import user.*;

public class ProjectClass implements Project {
	
	private String projName;
	private Manager manager;
	private List<String> keywords;
	private int numberOfRevisions;
	private LocalDate dateOflastChange;
	
	
	
	public ProjectClass(String projName, Manager manager, List<String> keywords) {
		//TODO complete
		dateOflastChange = null;
		this.numberOfRevisions = 0;
		this.projName = projName;
		this.manager = manager;
		manager.incNumberOfProjectsManaged();
		this.keywords = keywords;
	}
	
	public String toString() {
		return projName + " is managed by " + manager.getName();
	}
	
	public LocalDate getDateOflastChange() {
		return dateOflastChange;
	}
	
	public void setDateOfLastChange(LocalDate date) {
		if(dateOflastChange == null || date.isAfter(dateOflastChange))
			dateOflastChange = date;
	}
	
	public void incNumberOfRevisions() {
		numberOfRevisions++;
	}
	
	public int getNumberOfRevisions() {
		return numberOfRevisions;
	}
	
	public String getProjName() {
		return projName;
	}
	
	public Iterator<String> getKeywords() { 
		return keywords.iterator();
	}
	
	public boolean hasKeyword(String keyword) {
		return keywords.contains(keyword);
	}

	public Manager getManager() {
		return manager;
	}

	public String keywordPrintToString() {
		return "Need to implement keywordPrintToString() in sub-Class";
	}
}
