package exceptions;

@SuppressWarnings("serial")
public class ArtefactDoesNotExistException extends Exception {
	
	private String artefactName;
	
	public ArtefactDoesNotExistException(String artefactName) {
		super();
		this.artefactName = artefactName;
	}

	public String getArtefactName() {
		return artefactName;
	}
}
