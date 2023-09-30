package user;

import java.time.*;
import java.util.*;

import comparators.*;
import project.*;
import versionControlSystem.VersionControlSystemClass;

public class UserClass implements User { // README looks complete, care when changing
	
	private String userName;
	private int clearanceLevel;
	
	private Map<String, Project> workingAtProjectsMap;  // e preciso saber se pertence ao projecto  
	private Set<Project> workingAtProjectsSet;		 	// iterar sem ordem expecifica 
	private SortedSet<Revision> revisionsOfDeveloper; //used in 2.11 its a sortedSet because its ordered by criteria
													  //that doesnt change 

	
	public UserClass(String userName, int clearanceLevel){
		this.userName = userName;
		this.clearanceLevel = clearanceLevel;
		this.workingAtProjectsMap = new HashMap<String, Project>();
		this.workingAtProjectsSet = new HashSet<Project>();
		this.revisionsOfDeveloper = new TreeSet<Revision>(new ComparatorByDateThenRevisionNumberThenProjectName());
	}
	

	public int getClearanceLevel() {
		return clearanceLevel;
	}

	@Override
	public String getName() {
		return userName;
	}
	
	public String workaholicToString() {
		return String.format("%s: %d updates, %d projects, last update on %s", getName(), getNumberOfRevisions(),
			getNumberOfWorkingAtProjects(), VersionControlSystemClass.dateToString(getDateOfLastRevision()));
	}
	
	public Iterator<Project> getWorkingAtProjects() {
		return workingAtProjectsSet.iterator();
	}
	
	public void addWorkingAtProject(Project proj) {
		workingAtProjectsMap.put(proj.getProjName(), proj);
		workingAtProjectsSet.add(proj);
	}
	
	public boolean isWorkingAtProject(String projName) {
		return workingAtProjectsMap.containsKey(projName);
	}
	
	public int getNumberOfWorkingAtProjects() {
		return workingAtProjectsSet.size();
	}
	
	public boolean equals(Object o) {
		if(o == null)
			return false;
		
		if(!(o instanceof User))
			return false;
		
		User other = (User) o;
		return this.getName().equals(other.getName());
		
	}
	
	public void addRevision(Revision rev) {
		revisionsOfDeveloper.add(rev);
	}
	
	public int getNumberOfRevisions() {
		return revisionsOfDeveloper.size();
	}
	
	public Iterator<Revision> getRevisionsOfDeveloper() {
		return revisionsOfDeveloper.iterator();
	}
	
	public LocalDate getDateOfLastRevision() {
		return revisionsOfDeveloper.first().getDate();
	}
}
