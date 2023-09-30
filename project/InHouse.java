package project;

import java.time.LocalDate;
import java.util.Iterator;

import user.User;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 * corresponds to the in house variant of a project which means the project that was made by the same company and not outsourced
 * to a different company
 */
public interface InHouse extends Project {

	/**
	 * @returns an iterator with all users (managers and developers) that work on this project
	 */
	Iterator<User> getTeam();

	/**
	 * @returns an iterator with all the artefacts of this project
	 */
	Iterator<Artefact> getArtefacts();

	/**
	 * @param user that corresponds to an object user
	 * @returns true if user belongs to the project team false if not
	 */
	boolean doesUserBelongToTeam(User user);

	/**
	 * @param artName corresponds to the artefact name 
	 * @returns true if the artefact belongs to the project
	 */
	boolean doesArtefactBelongToProj(String artName);

	int getArtefactConfidentiality(String artName);

	void addTeamMember(User user);

	void addArtefact(Artefact art);

	int getConfidentialityLevel();
	
	String toString();
	
	//int getnumberOfRevisions();
	
	Revision reviseArtefact(String artefactName, String description, User userResponsible, LocalDate date);

	String confidentialityToString();
	
}
