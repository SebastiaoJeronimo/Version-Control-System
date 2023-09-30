package exceptions;

@SuppressWarnings("serial")
public class ManagerClearanceIsLowerException extends Exception {
	private int managerClearance;
	private String managerName;
	
	public ManagerClearanceIsLowerException(String managerName, int managerClearance) {
		super();
		this.managerClearance=managerClearance;
		this.managerName=managerName;
	}

	public int getManagerClearance() {
		return managerClearance;
	}

	public String getManagerName() {
		return managerName;
	}

}
