package project;


import java.time.*;
import java.util.*;

import comparators.*;
import user.*;
import versionControlSystem.VersionControlSystemClass;

public class InHouseClass extends ProjectClass implements InHouse {
	
	private int confidentialityLevel;
	private List<User> team; //its a list because we need the insertion order for the command on 2.9
	private List<Artefact> projPiecesList;	// for the iteration in the command 2.9, the order is always changing
											// so it can't be sortedSet because we find it harder to reorder a sortedSet
											// than a list
	private Map<String,Artefact> projPieces; //to access a Artefact in the command revision 2.10
	
	
	public InHouseClass(String projName, Manager manager, List<String> keywords, int confidentialityLevel) {
		super(projName, manager, keywords);
		this.confidentialityLevel = confidentialityLevel;
		team = new LinkedList<User>();
		team.add(manager);
		projPiecesList = new LinkedList<Artefact>();
		projPieces = new HashMap<String,Artefact>();
		
	}
	
	
	public String toString() {
		return "in-house " + super.toString() + " [" + getConfidentialityLevel() + ", " + (team.size()-1) + ", " 
				+ projPiecesList.size() + ", " + getNumberOfRevisions() + "]";
	}
	
	public String keywordPrintToString() {
		return "in-house " + super.toString() + " [" + getConfidentialityLevel() + ", " + (team.size()-1) + ", " 
				+ projPiecesList.size() + ", " + getNumberOfRevisions() + ", "
				+ VersionControlSystemClass.dateToString(getDateOflastChange()) + "]";
	}
	
	public String confidentialityToString() {
		String out = super.toString() + " and has keywords ";
		
		Iterator<String> it = getKeywords();
		while(it.hasNext()) {
			String keyword = it.next();
			out += keyword;
			
			if(it.hasNext()) {
				out += ", ";
			}
			else {
				out += ".";
			}
		}
		
		return out;
	}
	
	public Iterator<User> getTeam() {
		Iterator<User> it = team.iterator();
		it.next(); // remove the manager
		return it;
	}
	
	public Iterator<Artefact> getArtefacts() { // ordered by date of last revision then by name
		projPiecesList.sort(new ComparatorByDateThenName());
		return projPiecesList.iterator();
	}
	
	public boolean doesUserBelongToTeam(User user) {
		return team.contains(user);
	}
	
	public boolean doesArtefactBelongToProj(String artName) {
		return projPieces.containsKey(artName);
	}
	
	public int getArtefactConfidentiality(String artName) {
		return projPieces.get(artName).getConfidentiality();
	}
	
	public void addTeamMember(User user) {
		team.add(user);
		user.addWorkingAtProject(this);
	}
	
	public void addArtefact(Artefact art) {
		projPieces.put(art.getName(), art);
		projPiecesList.add(art);
		this.setDateOfLastChange(art.getLastRevisionDate());
	}

	public int getConfidentialityLevel() {
		return confidentialityLevel;
	}
	
	public Revision reviseArtefact(String artefactName, String description, User userResponsible, LocalDate date) {
		Artefact art = projPieces.get(artefactName);
		Revision rev = art.revise(description, date, userResponsible);
		this.incNumberOfRevisions();
		this.setDateOfLastChange(date);
		userResponsible.addRevision(rev);
		return rev;
	}
}
