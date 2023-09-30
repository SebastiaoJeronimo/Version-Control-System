package user;

import java.time.*;
import java.util.*;

import project.*;

public interface User {
	
	/**
	 * @returns the user clearence level
	 */
	int getClearanceLevel();
	
	/**
	 * @returns the user name
	 */
	String getName();
	
	String workaholicToString();
	
	/**
	 * @returns the number of project the user is working at
	 */
	int getNumberOfWorkingAtProjects();

	void addWorkingAtProject(Project project);
	
	boolean isWorkingAtProject(String projName);

	Iterator<Project> getWorkingAtProjects();
	
	public void addRevision(Revision rev);
	
	public int getNumberOfRevisions();
	
	Iterator<Revision> getRevisionsOfDeveloper();
	
	LocalDate getDateOfLastRevision();
}
