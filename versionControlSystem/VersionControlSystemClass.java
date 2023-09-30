package versionControlSystem;

import java.time.*;
import java.util.*;

import comparators.*;
import exceptions.*;
import project.*;
import user.*;


public class VersionControlSystemClass implements VersionControlSystem {
	
	
	//Users
	private SortedSet<User> userSet; // stores users ordered alphabetically
	private Map<String, User> userMap; // its a map  because of the unique id username used for example in 2.11
	//Projects
	private List<Project> projList; // stores projects ordered by registration project id --> Project
	private Map<String, Project> projMap; // its a map  because of the unique id projectName
	private Map<String, List<Project>> projByKeyword;//used in 2.12 with a keyword 
	
	//We considered a SortedMap but it, unnecessarily, complicates the code 
	//private SortedMap<Integer, List<InHouse>> projByConfidentiality; 
	//used in 2.13 because of lower limit and higher limit, subMap,
	//the key is an Integer from 1 to 5  it returns the projects with that specific confidentiality
	
	
	public VersionControlSystemClass() { //TODO constructor
		userSet = new TreeSet<User>(new SortUserAphabetically());
		userMap = new HashMap<String, User>();
		projList = new LinkedList<Project>();
		projMap = new HashMap<String, Project>();
		projByKeyword = new HashMap<String ,List<Project>>();
	}
	
	
	@Override
	public boolean doesUserExist(String userName){ // Done
		return userMap.containsKey(userName);
	}
	
	@Override
	public void checkIfUserExist(String userName) throws UserNameIsAlreadyRegistered {
		if(doesUserExist(userName)) {
			throw new UserNameIsAlreadyRegistered(userName);
		}
	}
	
	public void checkIfUserDoesntExist(String userName) throws UserIsntRegisteredException {
		if(!doesUserExist(userName)) {
			throw new UserIsntRegisteredException(userName);
		}
	}
	
	@Override
	public boolean isManager(String managerName) {
		return userMap.get(managerName) instanceof Manager;
	}
	
	@Override
	public void checkIfIsManager(String managerName) throws ManagerNameDoesNotExist {
		if(!isManager(managerName)) {
			throw new ManagerNameDoesNotExist(managerName);
		}
	}
	
	@Override
	public void addManager(String userName, int clearanceLevel) throws UserNameIsAlreadyRegistered{ //Done
		
		this.checkIfUserExist(userName);
		
		User manager = new ManagerClass(userName, clearanceLevel);
		userMap.put(userName, manager);
		userSet.add(manager);
	}
	
	@Override
	public void addDeveloper(String userName, String managerName, int clearanceLevel) 
			throws UserNameIsAlreadyRegistered, ManagerNameDoesNotExist { //Done
		
		this.checkIfUserExist(userName);
		this.checkIfIsManager(managerName);
		
		User developer = new DeveloperClass(userName, clearanceLevel, (Manager)(userMap.get(managerName)) );
		userMap.put(userName, developer);
		userSet.add(developer);
		Manager man = (Manager) userMap.get(managerName);
		man.addDeveloperManaged((Developer) developer);
	}
	
	@Override
	public Iterator<User> getUsers() throws NoUsersException { //Done
		this.checkIfThereAreUsers();
		return userSet.iterator();
	}

	/**
	 * For all the Strings in <code>keywords</code>:<br>
	 * - If there doesn t already exist a project with this keyword, it maps a new SortedSet with the key keyword <br>
	 * - If it already exists, it adds <code>project</code> to the already existing SortedSet of Projects
	 * @param project Project to add to projByKeyWord
	 * @param keywords words that represent <code>project</code>
	 */
	private void addProjectToProjByKeyWord(Project project) {
		Iterator<String> itKeys = project.getKeywords();
		
		while(itKeys.hasNext()) {
			String key = itKeys.next();
			
			if(projByKeyword.containsKey(key)) {
				projByKeyword.get(key).add(project);
			}
			else {
				List<Project> keywordSet = new LinkedList<Project>();
				keywordSet.add(project);
				projByKeyword.put(key, keywordSet);
			}
		}
	}
	
	@Override
	public void addOutsourcedProject(String managerName, String projName, List<String> keywords, String companyName) 
			throws ManagerNameDoesNotExist, ProjectAlreadyExistsException { //Done
		
		this.checkIfIsManager(managerName);
		this.checkIfProjectExistThrowIfDoes(projName);
		Manager man = (Manager) userMap.get(managerName);
		Project project = new OutsourcedClass(projName, man, keywords, companyName);
		
		man.addWorkingAtProject(project);
		projList.add(project);
		projMap.put(projName, project);
		this.addProjectToProjByKeyWord(project);
	}
	
	@Override
	public void addInhouseProject(String managerName, String projName, List<String> keywords, int confidentiality)
			throws ManagerNameDoesNotExist, ProjectAlreadyExistsException, ManagerClearanceIsLowerException{ //Done
		
		this.checkIfIsManager(managerName);
		this.checkIfProjectExistThrowIfDoes(projName);
		this.managerHasClearance(managerName, confidentiality);
		
		Manager man = (Manager) userMap.get(managerName);
		Project project = new InHouseClass(projName, man, keywords, confidentiality);
		
		man.addWorkingAtProject(project);
		projList.add(project);
		projMap.put(projName, project);
		this.addProjectToProjByKeyWord(project);
	}
	
	@Override
	public Iterator<Project> getProjects() throws NoProjectsException {
		checkIfThereProjects();
		return projList.iterator();
	}

	public boolean isProjectsEmpty() {
		return projMap.isEmpty();
	}
	
	public void checkIfThereProjects() throws NoProjectsException {
		if(isProjectsEmpty()) {
			throw new NoProjectsException();
		}
	}
	
	@Override
	public boolean doesProjectExist(String projName) {
		return projMap.containsKey(projName);
	}
	
	@Override
	public void checkIfProjectExistThrowIfDoesnt(String projName) throws ProjectDoesNotExistException  {
		if(!doesProjectExist(projName))
			throw new ProjectDoesNotExistException(projName);
	}
	
	public void checkIfProjectExistThrowIfDoes(String projName) throws ProjectAlreadyExistsException  {
		if(doesProjectExist(projName))
			throw new ProjectAlreadyExistsException(projName);
	}

	@Override
	public int getManagerClearance(String managerName) {
		return userMap.get(managerName).getClearanceLevel();
	}
	
	public void managerHasClearance(String managerName, int confidentiality) throws ManagerClearanceIsLowerException {
		int managerClearance = this.getManagerClearance(managerName);
		if (managerClearance < confidentiality) {
			throw new ManagerClearanceIsLowerException(managerName, managerClearance);
		}
	}
	
	@Override
	public boolean isProjectOutsourced(String projName) {
		return projMap.get(projName) instanceof Outsourced;
	}

	@Override
	public void checkIfProjectIsOutSourced(String projName) throws ProjectIsOutSourcedException {
		if(isProjectOutsourced(projName)) {
			throw new ProjectIsOutSourcedException(projName);
		}
		
	}
	
	@Override
	public String getProjectManagerName(String projName) {
		return projMap.get(projName).getManager().getName();
	}
	
	public void checkIfManagerIsProjectManager(String projName, String managerName) throws ManagerDoesntManageProjectException {
		String actualProjectManagerName = getProjectManagerName(projName);
		if(!actualProjectManagerName.equals(managerName))
			throw new ManagerDoesntManageProjectException(projName, actualProjectManagerName);
	}

	@Override
	public boolean isUserInTeam(String projName, String userName) {
		User user = userMap.get(userName);
		InHouse proj = (InHouse) projMap.get(projName);
		return proj.doesUserBelongToTeam(user);
	}

	public void checkIfUserIsInTeam(String projName, String userName) throws UserAlreadyInTeamException {
		if(isUserInTeam(projName, userName))
			throw new UserAlreadyInTeamException(userName);
	}
	
	public void checkIfUserIsNotInTeam(String projName, String userName) throws UserIsNotInTeamException {
		if(!isUserInTeam(projName, userName))
			throw new UserIsNotInTeamException(userName, projName);
		
	}
	
	@Override
	public boolean UserHasClearanceForProject(String projName, String userName) {
		User user = userMap.get(userName);
		InHouse proj = (InHouse) projMap.get(projName);
		return user.getClearanceLevel() >= proj.getConfidentialityLevel();
	}

	@Override
	public boolean isArtefactInProject(String projName, String artefactName) {
		InHouse proj = (InHouse) projMap.get(projName);
		return proj.doesArtefactBelongToProj(artefactName);
	}

	public void checkIfArtefactIsInProject(String projName, String artefactName) throws ArtefactAlreadyInProjectException {
		if (isArtefactInProject(projName, artefactName))
			throw new ArtefactAlreadyInProjectException(artefactName);
	}
	
	public void checkIfArtefactIsNotInProject(String projName, String artefactName) throws ArtefactDoesNotExistException {
		if (!isArtefactInProject(projName, artefactName))
			throw new ArtefactDoesNotExistException(artefactName);
	}
	
	public boolean doesUserHaveClearanceForProject(String projName, User user) {
		InHouse proj = (InHouse) projMap.get(projName);
		return proj.getConfidentialityLevel() <= user.getClearanceLevel();
	}
	
	public void checkIfUserDoesntHaveClearanceForProject(String projName, User user) throws UserDoesntHaveClearanceException {
		if(!doesUserHaveClearanceForProject(projName, user))
			throw new UserDoesntHaveClearanceException(user.getName());
	}

	@Override
	public boolean doesUserHaveClearanceForArtefact(String userName, String projName, String artefactName) {
		User user = userMap.get(userName);
		InHouse proj = (InHouse) projMap.get(projName);
		return user.getClearanceLevel() >= proj.getArtefactConfidentiality(artefactName);
	}
	
	public void checkIfUserHasClearanceForArtefact(String userName, String projName, String artefactName) throws UserDoesntHaveClearanceException {
		if(!doesUserHaveClearanceForArtefact(userName, projName, artefactName)) {
			throw new UserDoesntHaveClearanceException(userName);
		}
	}
	
	@Override
	public int reviseArtefact(String userName, String projName, String artefactName, LocalDate revisionDate,
			String comment) throws UserIsntRegisteredException, ProjectDoesNotExistException, UserIsNotInTeamException,
								   UserDoesntHaveClearanceException, ProjectIsOutSourcedException, ArtefactDoesNotExistException {
		
		checkIfUserDoesntExist(userName); // throws UserIsntRegisteredException
		checkIfProjectExistThrowIfDoesnt(projName); // throws ProjectDoesNotExistException
		checkIfProjectIsOutSourced(projName); // throws ProjectIsOutSourcedException
		checkIfArtefactIsNotInProject(projName, artefactName); // throws ArtefactDoesNotExistException
		checkIfUserIsNotInTeam(projName, userName); // throws UserIsNotInTeamException
		checkIfUserHasClearanceForArtefact(userName, projName, artefactName); // throws UserDoesntHaveClearanceException
		
		InHouse proj = (InHouse) projMap.get(projName);
		Revision rev = proj.reviseArtefact(artefactName, comment, userMap.get(userName), revisionDate);
		
		return rev.getRevisionNumber();
	}

	@Override
	public String getManagerInfo(String managerName) throws ManagerNameDoesNotExist  {
		
		this.checkIfIsManager(managerName);
		String out = "";
		Manager manager = (Manager) (userMap.get(managerName));
		
		out += String.format("Manager %s:\n", manager.getName());
		
		Iterator<Developer> itDevs = manager.getDevelopersBeingManaged();
		while(itDevs.hasNext()) {
			Developer dev = itDevs.next();
			out += dev.getName() + "\n";
			
			Iterator<Revision> itRevisions = dev.getRevisionsOfDeveloper();
			while(itRevisions.hasNext()) {
				Revision rev = itRevisions.next();
				
				out += String.format("%s, %s, revision %d, %s, %s\n", rev.getProjectName(), rev.getArtefactName(),
						rev.getRevisionNumber(), dateToString(rev.getDate()), rev.getDescription());	
			}	
		}
		return out;
	}
	
	@Override
	public Iterator<Project> getProjectsWithKeyword(String keyword) throws NoProjectsWithKeyWordException{
		if(!projByKeyword.containsKey(keyword)) {
			throw new NoProjectsWithKeyWordException(keyword);
		}
		
		List<Project> list = projByKeyword.get(keyword); 
		list.sort(new ComparatorByTypeDateNumberofrevisionsName());
		return list.iterator();
	}
	
	@Override
	public Iterator<InHouse> getProjectsInLimits(int lowerLimit, int upperLimit) throws NoProjectsWithinLimitsException {
		/*
		SortedSet<InHouse> projsInRange2 = new TreeSet<InHouse>(new ProjectComparatorByProjName());
		
		SortedMap<Integer, List<InHouse>> projInRangeByConfidentiality = projByConfidentiality.subMap(
				Integer.valueOf(lowerLimit), Integer.valueOf(upperLimit+1));// +1 because upperLimit of the subMap is exclusive
		
		Iterator<Entry<Integer, List<InHouse>>> subMapSetIt = projInRangeByConfidentiality.entrySet().iterator();
		while(subMapSetIt.hasNext()) {
			//List<InHouse> listOfInHouse = subMapSetIt.next().getValue();
			Iterator<InHouse> InHouseIt =  subMapSetIt.next().getValue().iterator();
			while(InHouseIt.hasNext()) {
				projsInRange2.add(InHouseIt.next());
			}
		}
		*/
		
		SortedSet<InHouse> projsInRange = new TreeSet<InHouse>(new ProjectComparatorByProjName());
		Iterator<Project> it = projList.iterator();
		while(it.hasNext()) {
			Project proj = it.next();
			if(proj instanceof InHouse && ((InHouse) proj).getConfidentialityLevel() >= lowerLimit 
					&& ((InHouse) proj).getConfidentialityLevel() <= upperLimit) {
				
				projsInRange.add((InHouse)proj);
			}
		}
		
		Iterator<InHouse> itInHouse = projsInRange.iterator();
		if(!itInHouse.hasNext()) {
			throw new NoProjectsWithinLimitsException(lowerLimit, upperLimit);
		}
		
		
		return itInHouse;
		// return projsInRange2.iterator();
	}
	
	@Override
	public boolean areThereUsers() {
		return !userMap.isEmpty();
	}
	
	public void checkIfThereAreUsers() throws NoUsersException {
		if(!areThereUsers()) {
			throw new NoUsersException();
		}
	}
	
	@Override
	public Iterator<User> getWorkaholics() throws NoUsersException, NoWorkaholicsException{
		checkIfThereAreUsers();
		
		List<User> workaholicList = new LinkedList<User>(userSet);
		Iterator<User> userIt = userSet.iterator();
		while(userIt.hasNext()) {
			User user = userIt.next();
			if(user.getNumberOfRevisions() == 0) {
				workaholicList.remove(user);
			}
		}
		/* TODO Este remove() provavelmente é mais eficiente
		int i = 0;
		while(userIt.hasNext()) {
			User user = userIt.next();
			if(user.getNumberOfRevisions() == 0) {
				workaholicList.remove(i);
			}
			i++;
		}
		*/
		workaholicList.sort(new UserComparatorForWorkaholic());
		
		Iterator<User> workaholicIt = workaholicList.iterator();
		if(!workaholicIt.hasNext()) {
			throw new NoWorkaholicsException();
		}
		
		return workaholicIt;
	}
	
	@Override
	public String getCommonInfo() throws NoUsersException{
		
		checkIfThereAreUsers(); // throws NoUsersException
		
		User commonKing = null, commonPartner = null;
		int nCommonProjects = 0;
		
		Iterator<User> userIt = userSet.iterator();
		while(userIt.hasNext()) {
			User possibleKing = userIt.next();
			
			Iterator<User> otherUsersIt = userSet.iterator();
			while(otherUsersIt.hasNext()) {
				User possiblePartner = otherUsersIt.next();
				if(possiblePartner.equals(possibleKing)) {
					continue;
				}
				int auxNumCommonProjects = 0;
				
				Iterator<Project> inHouseIt = possibleKing.getWorkingAtProjects();
				while(inHouseIt.hasNext()) {
					if(possiblePartner.isWorkingAtProject(inHouseIt.next().getProjName())) {
						auxNumCommonProjects++;
					}
				}
				// nao é preciso verificar ordem alfabetica pq estamos a iterar sobre um sortedSet,
				// ordenado alfabeticamente
				if(auxNumCommonProjects > nCommonProjects || commonKing == null) { 
					
					commonKing = possibleKing;
					commonPartner = possiblePartner;
					nCommonProjects = auxNumCommonProjects;
				}
				/*else {
					if(auxNumCommonProjects == nCommonProjects) {
						if (possibleKing.getName().compareTo(commonKing.getName()) < 0) {
							commonKing = possibleKing;
							commonPartner = possiblePartner;
							nCommonProjects = auxNumCommonProjects;
						}
						if(possibleKing.getName().equals(commonKing.getName()) && 
								possiblePartner.getName().compareTo(commonPartner.getName()) < 0){
							
							commonKing = possibleKing;
							commonPartner = possiblePartner;
							nCommonProjects = auxNumCommonProjects;
						}
						
					}
				}*/
			}
		}
		if(nCommonProjects == 0) {
			throw new NoUsersException();
		}
		return String.format("%s %s have %d projects in common.", commonKing.getName(), commonPartner.getName(), nCommonProjects);
	}

	
	@Override
	public void addUserToTeam(String projName, String managerName, String userName) throws UserIsntRegisteredException,
			UserAlreadyInTeamException, UserDoesntHaveClearanceException{
		
		checkIfUserDoesntExist(userName);
		checkIfUserIsInTeam(projName, userName);
		
		User user = userMap.get(userName);
		checkIfUserDoesntHaveClearanceForProject(projName, user);
		
		InHouse project = (InHouse)(projMap.get(projName));
		project.addTeamMember(user);
	}

	
	public boolean doesArtefactExceedsProjectConfidentiality(String projName, int conf) {
		InHouse proj = (InHouse) projMap.get(projName);
		return proj.getConfidentialityLevel() < conf;
	}
	
	
	public void checkIfArtefactExceedsProjectConfidentiality(String projName, String artefactName, int conf) throws ArtefactExceedsProjectConfidentiality {
		if(doesArtefactExceedsProjectConfidentiality(projName, conf)) {
			throw new ArtefactExceedsProjectConfidentiality(artefactName);
		}
	}
	
	
	@Override
	public void addArtefactToProject(String projName, String userName, LocalDate date, String artefactName,
			int confidentiality, String description) throws ArtefactAlreadyInProjectException, ArtefactExceedsProjectConfidentiality {
		
		checkIfArtefactIsInProject(projName, artefactName);
		checkIfArtefactExceedsProjectConfidentiality(projName, artefactName, confidentiality);
		
		InHouse proj = (InHouse) projMap.get(projName);
		Artefact art = new ArtefactClass(artefactName, projName, confidentiality, date, description, userMap.get(userName));
		
		proj.addArtefact(art);
		User user = userMap.get(userName);
		user.addRevision(art.getRevisions().next()); //Como acabou de ser criado existe pelo menos um
	}

	
	@Override
	public String getProjectInfo(String projName) throws ProjectDoesNotExistException, ProjectIsOutSourcedException{
		
		
		checkIfProjectExistThrowIfDoesnt(projName);
		checkIfProjectIsOutSourced(projName);
		
		String out = "";
		InHouse proj = (InHouse) projMap.get(projName);
		Manager man  = proj.getManager(); 
		
		out += String.format("%s [%d] managed by %s [%d]:\n", proj.getProjName(), proj.getConfidentialityLevel(), 
															man.getName(), man.getClearanceLevel());
		
		Iterator<User> it = proj.getTeam();
		while(it.hasNext()) {
			User user = it.next();
			out += String.format("%s [%d]\n", user.getName(), user.getClearanceLevel());
		}
		
		Iterator<Artefact> itArt = proj.getArtefacts();
		while(itArt.hasNext()) {
			Artefact art = itArt.next();
			out += String.format("%s [%d]\n", art.getName(), art.getConfidentiality());
			
			Iterator<Revision> itRev= art.getRevisions();
			while(itRev.hasNext()) {
				Revision rev = itRev.next();
				LocalDate date = rev.getDate();
				out += String.format("revision %d %s %s %s\n", rev.getRevisionNumber(), rev.getUserResponsible().getName(),
																dateToString(date), rev.getDescription());
				
			}
		}
		
		return out;
	}
	
	
	public static String dateToString(LocalDate date) {
		return String.format("%02d-%02d-%d", date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
	}
	
}
