package user;

import java.util.Iterator;

public interface Manager extends User {

	int getNumberOfProjectsManaged();
	
	int getNumberOfDevelopersManaged();
	
	Iterator<Developer> getDevelopersBeingManaged();
	
	void addDeveloperManaged(Developer developer);

	void incNumberOfProjectsManaged();
}
