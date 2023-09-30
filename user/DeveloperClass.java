package user;


public class DeveloperClass extends UserClass implements Developer { // README looks complete, care when changing

	private Manager manager;
		
	public DeveloperClass(String userName, int clearanceLevel, Manager manager) {
		super(userName, clearanceLevel);
		this.manager = manager;
		
	}

	public String getManagerName() {
		return manager.getName();
		
	}
	
	public String toString() {
		return String.format("developer %s is managed by %s [%d]", this.getName(), this.getManagerName(),
																   this.getNumberOfWorkingAtProjects());
	}	
}
