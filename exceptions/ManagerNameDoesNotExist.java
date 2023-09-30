package exceptions;

@SuppressWarnings("serial")
public class ManagerNameDoesNotExist extends Exception {
	private String name;
	
	public ManagerNameDoesNotExist(String name) {
		super();
		this.name=name;
	}
	
	public String getManagerName() {
		return name;
	}
}
