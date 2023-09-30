package exceptions;

@SuppressWarnings("serial")
public class ArtefactAlreadyInProjectException extends Exception {
	
	private String artefactName;
	
	public ArtefactAlreadyInProjectException(String artefactName) {
		super();
		this.artefactName = artefactName;
	}

	public String getArtefactName() {
		return artefactName;
	}
}
