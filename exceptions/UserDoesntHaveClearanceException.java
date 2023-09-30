package exceptions;

@SuppressWarnings("serial")
public class UserDoesntHaveClearanceException extends Exception {
	
	private String userName;

	public UserDoesntHaveClearanceException(String userName){
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
}

