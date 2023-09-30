package user;

import java.util.*;

import comparators.*;

public class ManagerClass extends UserClass implements Manager {
	
	private SortedSet<Developer> developersManaged; //2.11 ordered alphabetically
	private int numberOfProjectsManaged;
	
	public ManagerClass(String userName,int clearanceLevel) {
		// TODO
		super(userName, clearanceLevel);
		developersManaged = new TreeSet<Developer>(new SortUserAphabetically());
	}
	
	public void incNumberOfProjectsManaged() {
		numberOfProjectsManaged++;
	}
	
	public int getNumberOfProjectsManaged() {
		return numberOfProjectsManaged;
	}
	
	public int getNumberOfDevelopersManaged() {
		return developersManaged.size();
	}
	
	@Override
	public String toString() {
		return String.format("manager %s [%d, %d, %d]", this.getName(), this.getNumberOfDevelopersManaged(), 
							  							this.getNumberOfProjectsManaged(), this.getNumberOfWorkingAtProjects());
	}

	public Iterator<Developer> getDevelopersBeingManaged() {
		return developersManaged.iterator();
	}

	public void addDeveloperManaged(Developer dev) {
		developersManaged.add(dev);
	}
	
}
