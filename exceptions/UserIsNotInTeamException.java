package exceptions;

@SuppressWarnings("serial")
public class UserIsNotInTeamException extends Exception{

	private String userName;
	private String projName;
	
	public UserIsNotInTeamException(String userName, String projName) {
		super();
		this.userName = userName;
		this.projName = projName;
	}

	public String getUserName() {
		return userName;
	}

	public String getProjectName() {
		return projName;
	}
}
