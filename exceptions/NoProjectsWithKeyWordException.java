package exceptions;

@SuppressWarnings("serial")
public class NoProjectsWithKeyWordException extends Exception {
	
	private String keyword;
	
	public NoProjectsWithKeyWordException(String keyword) {
		super();
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
