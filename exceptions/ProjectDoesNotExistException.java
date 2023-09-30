package exceptions;

@SuppressWarnings("serial")
public class ProjectDoesNotExistException extends ProjectAlreadyExistsException {

	public ProjectDoesNotExistException(String projName) {
		super(projName);
	}

}
