package versionControlSystem;

import java.time.*;
import java.util.*;

import exceptions.*;
import project.*;
import user.*;

public interface VersionControlSystem {

	boolean doesUserExist(String userName);
	
	/**
	 * @param userName string that corresponds to the user name
	 * @throws UserNameIsAlreadyRegistered if the user isn't registered
	 */
	void checkIfUserExist(String userName) throws UserNameIsAlreadyRegistered;

	void checkIfUserDoesntExist(String userName) throws UserIsntRegisteredException;
	
	boolean isManager(String managerName);
	
	/**
	 * 
	 * @param managerName String that corresponds to the manager name
	 * @throws ManagerNameDoesNotExist if the name doesn't correspond to a existing manager 
	 */
	void checkIfIsManager(String managerName) throws ManagerNameDoesNotExist;
	
	/**
	 * adds a manager to the system
	 * @param userName String that corresponds to the user name
	 * @param clearanceLevel int from 0 to 5 that corresponds to the clearance level
	 * @throws UserNameIsAlreadyRegistered if there is already a user named userName registered
	 */
	void addManager(String userName, int clearanceLevel) throws UserNameIsAlreadyRegistered;

	/**
	 * adds a developer to the system 
	 * @param userName its a string that corresponds to the user name
	 * @param managerName its a string that corresponds to the manager name
	 * @param clearanceLevel int from 0 to 5 that corresponds to the clearance level
	 * @throws ManagerNameDoesNotExist if managerName isn't the name of a registered manager
	 * @throws UserNameIsAlreadyRegistered if there is already a user named userName registered
	 */
	void addDeveloper(String userName, String managerName, int clearanceLevel) throws UserNameIsAlreadyRegistered, ManagerNameDoesNotExist;

	/**
	 * @throws NoUsersException 
	 * @returns a iterator with all the users ordered by alphabetical order
	 */
	Iterator<User> getUsers() throws NoUsersException ;

	/**
	 * @return an iterator of all the Projects registered by order of registration
	 * @throws NoProjectsException 
	 */
	Iterator<Project> getProjects() throws NoProjectsException;

	boolean isProjectsEmpty();
	
	void checkIfThereProjects() throws NoProjectsException;
	
	/**
	 * checks if the project with the unique projName already exists
	 * @param projName its a String that corresponds to the name and the unique identifier of the project
	 * @throws ProjectDoesNotExistException if the project doesn't exist
	 * @throws ProjectAlreadyExistsException if the project exists
	 * 
	 */
	void checkIfProjectExistThrowIfDoesnt(String projName) throws ProjectDoesNotExistException;

	void checkIfProjectExistThrowIfDoes(String projName) throws ProjectAlreadyExistsException;
	
	boolean doesProjectExist(String projName);
	
	/**
	 * @param managerName its the manager name
	 * @param confidentiality NOS NAO USAMOS ISTO EM NENHUM LADO GONCALO PFF VE ISTO
	 * @returns a int number from 0 to 5 that corresponds to the manager clearance 
	 */
	int getManagerClearance(String managerName);
	
	boolean isProjectOutsourced(String projName);

	//boolean isProjectManagedByManager(String projName, String managerName);

	String getProjectManagerName(String projName);

	void checkIfManagerIsProjectManager(String projName, String managerName) throws ManagerDoesntManageProjectException;
	
	boolean isUserInTeam(String projName, String userName);
	
	void checkIfUserIsNotInTeam(String projName, String userName) throws UserIsNotInTeamException;

	boolean UserHasClearanceForProject(String projName, String userName);

	boolean isArtefactInProject(String projName, String artefactName);

	boolean doesUserHaveClearanceForProject(String projName, User user);

	boolean doesUserHaveClearanceForArtefact(String userName, String projName, String artefactName);

	/**
	 * Registers a new revision in an artefact
	 * @param userName - Name of the user that made the revision
	 * @param projName - Name of the project that the artefact belongs to
	 * @param artefactName - Name of the artefact that was revised
	 * @param revisionDate - Date that the revision was made
	 * @param comment - Description of the revision, what was changed in the artefact
	 * @return The number of the revision added
	 * @throws ProjectDoesNotExistException 
	 * @throws UserIsntRegisteredException 
	 * @throws UserDoesntHaveClearanceException 
	 * @throws UserIsNotInTeamException 
	 * @throws ProjectIsOutSourcedException 
	 * @throws ArtefactDoesNotExistException 
	 */
	int reviseArtefact(String userName, String projName, String artefactName, LocalDate revisionDate, String comment) 
			throws UserIsntRegisteredException, ProjectDoesNotExistException, UserIsNotInTeamException,
				   UserDoesntHaveClearanceException, ProjectIsOutSourcedException, ArtefactDoesNotExistException;

	String getProjectInfo(String projName) throws ProjectDoesNotExistException, ProjectIsOutSourcedException;

	String getManagerInfo(String managerName) throws ManagerNameDoesNotExist;

	Iterator<Project> getProjectsWithKeyword(String keyword) throws NoProjectsWithKeyWordException;

	Iterator<InHouse> getProjectsInLimits(int lowerLimit, int upperLimit) throws NoProjectsWithinLimitsException;

	/**
	 * @returns true if there are users in the system false if its empty
	 */
	boolean areThereUsers();

	void checkIfThereAreUsers() throws NoUsersException;
	
	/**
	 * 
	 * @return an Iterator with the 3 or less most workaholic users registered
	 * @throws NoUsersException
	 * @throws NoWorkaholicsException
	 */
	Iterator<User> getWorkaholics() throws NoUsersException, NoWorkaholicsException;

	String getCommonInfo() throws NoUsersException;

	void addOutsourcedProject(String managerName, String projName, List<String> keywords, String companyName)
			throws ManagerNameDoesNotExist, ProjectAlreadyExistsException;

	void addInhouseProject(String managerName, String projName, List<String> keywords, int confidentiality) 
			throws ManagerNameDoesNotExist, ProjectAlreadyExistsException, ManagerClearanceIsLowerException;

	void addUserToTeam(String projName, String managerName, String userName) throws UserIsntRegisteredException, 
			UserAlreadyInTeamException, UserDoesntHaveClearanceException;

	void addArtefactToProject(String projName, String userName, LocalDate date, String artefactName,
			int confidentiality, String description) throws ArtefactAlreadyInProjectException,
			ArtefactExceedsProjectConfidentiality;

	void checkIfProjectIsOutSourced(String projName) throws ProjectIsOutSourcedException;
	
	//String dateToString(LocalDate date);
}
