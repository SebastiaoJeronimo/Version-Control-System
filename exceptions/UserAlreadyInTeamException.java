package exceptions;

@SuppressWarnings("serial")
public class UserAlreadyInTeamException extends Exception {
	
	private String userName;
	
	public UserAlreadyInTeamException(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
