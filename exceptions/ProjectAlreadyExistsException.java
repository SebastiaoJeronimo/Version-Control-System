package exceptions;

@SuppressWarnings("serial")
public class ProjectAlreadyExistsException extends Exception {
	private String projName;
	
	public ProjectAlreadyExistsException(String projName) {
		this.projName = projName; 
	}
	
	public String getProjectName() {
		return projName;
	}
	
}
