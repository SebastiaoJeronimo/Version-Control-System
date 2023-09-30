package exceptions;

@SuppressWarnings("serial")
public class ArtefactExceedsProjectConfidentiality extends Exception {
	
	private String artefactName;
	
	public ArtefactExceedsProjectConfidentiality(String artefactName) {
		super();
		this.artefactName = artefactName;
	}

	public String getArtefactName() {
		return artefactName;
	}
}
