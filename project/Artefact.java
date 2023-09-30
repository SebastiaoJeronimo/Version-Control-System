package project;

import java.time.*;
import java.util.Iterator;

import user.*;

/**
 *@author Goncalo Carvalho<br>Sebastiao Jeronimo
 * its the project artefect interface the artefact its like the project 
 */
public interface Artefact {
	
	/**
	 * @returns the artefact name
	 */
	String getName();
	
	/**
	 * @returns the artefact confidentiality level 
	 */
	int getConfidentiality();
	
	/**
	 * @returns a localDate variable that corresponds to the last time that the artefact got a revision
	 */
	LocalDate getLastRevisionDate();
	
	/**
	 * @returns the user that created the artefact
	 */
	User getUser();
	
	/**
	 * @returns the discription of the artefact
	 */
	String getDescription();
	
	/**
	 * @returns an iterator with the revisions of the artefact
	 */
	Iterator<Revision> getRevisions();
	
	/**
	 * @returns the number of revisions of this artefact 
	 */
	int getNumberOfRevisions();
	
	/**
	 * creates an artefact revision
	 * @param description corresponds to the revision description
	 * @param date corresponds to the date that this revision was made
	 * @param userResponsible corresponds to the user responsible for the revision
	 * @returns the revision created 
	 */
	Revision revise(String description, LocalDate date, User userResponsible);
}
