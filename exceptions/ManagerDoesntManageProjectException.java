package exceptions;

@SuppressWarnings("serial")
public class ManagerDoesntManageProjectException extends Exception {

	private String projectName;
	private String managerName;
	
	public ManagerDoesntManageProjectException(String projectName, String managerName) {
		super();
		this.managerName = managerName;
		this.projectName = projectName;
		
	}

	public String getProjectName() {
		return projectName;
	}

	public String getManagerName() {
		return managerName;
	}

}
