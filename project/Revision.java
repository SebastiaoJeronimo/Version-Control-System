package project;

import java.time.*;

import user.*;

public interface Revision {
	 
	int getRevisionNumber();
	
	LocalDate getDate();
	
	String getDescription();
	
	String getProjectName();
	
	User getUserResponsible();

	String getArtefactName();
}
