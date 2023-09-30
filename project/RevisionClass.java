package project;

import java.time.LocalDate;

import user.User;

public class RevisionClass implements Revision { // TODO README looks complete, care when changing
	
	private int revisionNumber;
	private LocalDate date;
	private String description;
	private User userResponsible;
	private String projectName;
	private String artefactName;
	
	public RevisionClass(LocalDate date, String description, int revisionNumber, User userResponsible,
			String projectName, String artefactName) {
		this.description = description;
		this.date = date;
		this.revisionNumber = revisionNumber;
		this.userResponsible = userResponsible;
		this.projectName = projectName;
		this.artefactName = artefactName;
	}

	public int getRevisionNumber() {
		return revisionNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public User getUserResponsible() {
		return userResponsible;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public String getArtefactName() {
		return artefactName;
	}
}
