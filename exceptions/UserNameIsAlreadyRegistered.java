package exceptions;


@SuppressWarnings("serial")
public class UserNameIsAlreadyRegistered extends Exception {
	
	private String userName;

	public UserNameIsAlreadyRegistered(String userName) {
		super();
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
}
