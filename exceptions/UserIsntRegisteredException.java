package exceptions;

@SuppressWarnings("serial")
public class UserIsntRegisteredException extends Exception {

	private String userName;
	
	public UserIsntRegisteredException(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}
