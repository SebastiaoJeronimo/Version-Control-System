package exceptions;

@SuppressWarnings("serial")
public class ProjectIsOutSourcedException extends Exception {
	
	private String projectName;
	
	public ProjectIsOutSourcedException(String projectName) {
		super();
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
}
