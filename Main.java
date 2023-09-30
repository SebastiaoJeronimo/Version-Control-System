import java.time.*;
import java.util.*;

import versionControlSystem.*;
import user.*;
import project.*;
import exceptions.*;

/**
 * 
 * @author Goncalo Carvalho<br>Sebastiao Jeronimo
 *
 */
public class Main {

	/**
	 * User messages
	 */
	public static final String UNKNOWN_MSG = "Unknown command. Type help to see available commands.";
	public static final String EXIT_MSG = "Bye!";
	public static final String AVAILABLE_COMMANDS_MSG = "Available commands:";
	public static final String HELP_MSG = "register - adds a new user\n" 
										+ "users - lists all registered users\n"
										+ "create - creates a new project\n" 
										+ "projects - lists all projects\n"
										+ "team - adds team members to a project\n" 
										+ "artefacts - adds artefacts to a project\n"
										+ "project - shows detailed project information\n" 
										+ "revision - revises an artefact\n"
										+ "manages - lists developers of a manager\n" 
										+ "keyword - filters projects by keyword\n"
										+ "confidentiality - filters projects by confidentiality level\n"
										+ "workaholics - top 3 employees with more artefacts updates\n"
										+ "common - employees with more projects in common\n" 
										+ "help - shows the available commands\n"
										+ "exit - terminates the execution of the program";
	public static final String USER_REGISTERED_MSG = "User %s was registered as %s with clearance level %d.\n";
	public static final String UNKNOWN_JOB_MSG = "Unknown job position.";
	public static final String USER_ALREADY_EXISTS_MSG = "User %s already exists.\n";
	public static final String MANAGER_DOES_NOT_EXIST_MSG = "Project manager %s does not exist.\n";
	public static final String NO_REGISTERED_USERS_MSG = "No users registered.";
	public static final String ALL_REGISTERED_USERS_MSG = "All registered users:";
	public static final String NO_PROJECTS_MSG = "No projects added.";
	public static final String ALL_PROJECTS_MSG = "All projects:";
	public static final String PROJECT_CREATED_MSG = "%s project was created.\n";
	public static final String UNKNOWN_PROJ_TYPE_MSG = "Unknown project type.";
	public static final String PROJECT_ALREADY_EXISTS_MSG = "%s project already exists.\n";
	public static final String PROJECT_DOES_NOT_EXIST_MSG = "%s project does not exist.\n";
	public static final String MANAGER_HAS_CLEARANCE_MSG = "Project manager %s has clearance level %d.\n";
	public static final String PROJECT_IS_MANAGED_BY_MSG = "%s is managed by %s.\n";
	public static final String LATEST_TEAM_MEMBERS_MSG = "Latest team members:";
	public static final String AlREADY_IN_THE_TEAM_MSG = "%s: already a member.\n";
	public static final String INSUFFICIENT_CLEARANCE_MSG = "%s: insufficient clearance level.\n";
	public static final String DOES_NOT_EXIST_MSG = "%s: does not exist.\n";
	public static final String ADDED_TO_TEAM_MSG = "%s: added to the team.\n";
	public static final String ART_ADDED_TO_PROJ_MSG = "%s: added to the project.\n";
	public static final String ART_ALREADY_IN_PROJ_MSG = "%s: already in the project.\n";
	public static final String ART_EXCEEDS_CONF_MSG = "%s: exceeds project confidentiality level.\n";
	public static final String ART_DOES_NOT_EXIST_MSG = "%s does not exist in the project.\n";
	public static final String LATEST_ARTEFACTS_MSG = "Latest project artefacts:";
	public static final String USER_DOES_NOT_EXIST_MSG = "User %s does not exist.\n";
	public static final String ISNT_MEMBER_OF_TEAM_MSG = "User %s does not belong to the team of %s.\n";
	public static final String CANT_ACCESS_ARTEFACT_MSG = "User %s cannot access artefact.\n";
	public static final String REVISON_SUBMITTED_MSG = "Revision %d of artefact %s was submitted.\n";
	public static final String PROJECT_IS_OUTSOURCED_MSG = "%s is an outsourced project.\n";
	public static final String NO_PROJ_KEYWORD_MSG = "No projects with keyword %s.\n";
	public static final String ALL_PROJ_KEYWORD_MSG = "All projects with keyword %s:\n";
	public static final String NO_PROJ_IN_LIMITS_MSG = "No projects within levels %d and %d.\n";
	public static final String ALL_PROJ_IN_LIMITS_MSG = "All projects within levels %d and %d:\n";
	public static final String NO_WORKAHOLICS_MSG = "There are no workaholics.";
	public static final String NO_USERS_WITH_COMMON_PROJS = "Cannot determine employees with common projects.";
	//public static final String USERS_MANAGER_TEMPLATE_MSG = "manager %s [%d, %d, %d]\n";
	//public static final String USERS_DEVELOPER_TEMPLATE_MSG = "developer %s is managed by %s [%d]\n";

	public static final int NUMBER_OF_WORKAHOLICS = 3;

	/**
	 * Program final variables, TODO make this an Enum
	 */
	private static final String MANAGER = "manager";
	private static final String DEVELOPER = "developer";
	private static final String INHOUSE = "inhouse";
	private static final String OUTSOURCED = "outsourced";
	
	/**
	 * @author Goncalo Carvalho<br>Sebastiao Jeronimo
	 */
	private enum Command {
		EXIT, HELP, REGISTER, USERS, CREATE, PROJECTS, TEAM, ARTEFACTS, PROJECT, REVISION, MANAGES, KEYWORD,
		CONFIDENTIALITY, WORKAHOLICS, COMMON, UNKNOWN
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		VersionControlSystem vcs = new VersionControlSystemClass();

		Command c = getCommand(in);

		while (c != Command.EXIT) {
			switch (c) {
			case HELP:
				helpCMD(in);
				break;

			case REGISTER:
				registerCMD(in, vcs);
				break;

			case USERS:
				usersCMD(in, vcs);
				break;

			case CREATE:
				createCMD(in, vcs);
				break;

			case PROJECTS:
				projectsCMD(in, vcs);
				break;

			case TEAM:
				teamCMD(in, vcs);
				break;

			case ARTEFACTS:
				artefactsCMD(in, vcs);
				break;

			case PROJECT:
				projectCMD(in, vcs);
				break;

			case REVISION:
				revisionCMD(in, vcs);
				break;

			case MANAGES:
				managesCMD(in, vcs);
				break;

			case KEYWORD:
				keywordCMD(in, vcs);
				break;

			case CONFIDENTIALITY:
				confidentialityCMD(in, vcs);
				break;

			case WORKAHOLICS:
				workaholicCMD(in, vcs);
				break;

			case COMMON:
				commonCMD(in, vcs);
				break;

			default:
				System.out.println(UNKNOWN_MSG);
				break;
			}
			c = getCommand(in);
		}
		exitCMD();
		in.close();
	}

	/**
	 * Gets the command
	 * 
	 * @param str
	 * @return - the String introduced as a <code>Command</code><br>
	 *         - or if <code>str</code> doesn't match a <code>Command</code> it
	 *         returns <code>Command.UNKNOWN</code>
	 */
	private static Command getCommand(Scanner in) { // DONE, NOT TESTED

		try {
			Command com = Command.valueOf(in.next().toUpperCase());
			return com;
		} catch (IllegalArgumentException e) { // if the input doesn't match a Command then valueOf(comm) trows
												// IllegalArgumentException
			return Command.UNKNOWN;
		}
	}

	/**
	 * prints the exit message
	 */
	private static void exitCMD() { // DONE, TESTED
		System.out.println(EXIT_MSG);
	}

	/**
	 * Prints the help messages
	 */
	private static void helpCMD(Scanner in) { // DONE, TESTED
		in.nextLine();
		System.out.println(AVAILABLE_COMMANDS_MSG);
		System.out.println(HELP_MSG);
	}

	/**
	 * @param in  Obejct Scanner
	 * @param vcs its the object VersionControlSystem registers a user to the system
	 */
	private static void registerCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		try {
			String jobType = in.next().toLowerCase();

			switch (jobType) {
				case MANAGER:
					registerManager(in, vcs);
					break;
	
				case DEVELOPER:
					registerDeveloper(in, vcs);
					break;
	
				default:
					in.nextLine(); // to "clear" the input
					throw new JobPositionUnknown();
			}
		} 
		catch (JobPositionUnknown e) {
			System.out.println(UNKNOWN_JOB_MSG);
		} 
		catch (UserNameIsAlreadyRegistered e) {
			System.out.printf(USER_ALREADY_EXISTS_MSG, e.getUserName());
		} 
		catch (ManagerNameDoesNotExist e) {
			System.out.printf(MANAGER_DOES_NOT_EXIST_MSG, e.getManagerName());
		}

	}

	/**
	 * registers a user specifically manager
	 * 
	 * @param in  object Scanner
	 * @param vcs object versionControlSystem
	 * @throws UserNameIsAlreadyRegistered exception thrown if the user name is
	 *                                     already registered
	 */
	private static void registerManager(Scanner in, VersionControlSystem vcs) throws UserNameIsAlreadyRegistered { // Done, not tested
		// Done, not tested
		String userName = in.next();
		int clearanceLevel = in.nextInt();
		in.nextLine();

		vcs.addManager(userName, clearanceLevel);
		System.out.printf(USER_REGISTERED_MSG, userName, MANAGER, clearanceLevel);
	}

	/**
	 * registers a user specifically a developer
	 * 
	 * @param in  object Scanner
	 * @param vcs object versionControlSystem
	 * @throws UserNameIsAlreadyRegistered exception thrown if the user is already
	 *                                     registered
	 * @throws ManagerNameDoesNotExist     exception thrown if the manager name
	 *                                     doesnt exist
	 */
	private static void registerDeveloper(Scanner in, VersionControlSystem vcs) // Done, not tested
			throws UserNameIsAlreadyRegistered, ManagerNameDoesNotExist { 
		String userName = in.next();
		String managerName = in.next();
		int clearanceLevel = in.nextInt();
		in.nextLine();

		vcs.addDeveloper(userName, managerName, clearanceLevel);
		System.out.printf(USER_REGISTERED_MSG, userName, DEVELOPER, clearanceLevel);
	}

	/**
	 * list all the users in the system
	 * 
	 * @param in  object Scanner
	 * @param vcs object versionControlSystem
	 */
	private static void usersCMD(Scanner in, VersionControlSystem vcs) { // Done, Not Tested
		try {
			in.nextLine();

			Iterator<User> it = vcs.getUsers(); // ordered alphabetically

			System.out.println(ALL_REGISTERED_USERS_MSG);
			while (it.hasNext()) {
				User user = it.next();
				System.out.println(user); // uses toString(), Done
			}
		} 
		catch (NoUsersException e) {
			System.out.println(NO_REGISTERED_USERS_MSG);
		}

	}

	/**
	 * create a new project
	 * 
	 * @param in  its the object Scanner
	 * @param vcs its the object VersionControlSystem
	 */
	private static void createCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested

		String managerName = in.next();
		String projType = in.next();
		String projName = in.nextLine().trim();
		int numberKeywords = in.nextInt();

		List<String> keywords = new LinkedList<String>();
		for (int i = 0; i < numberKeywords; i++) {
			keywords.add(in.next());
		}

		in.nextLine();
		String lastInput = in.nextLine().trim();
		
		try {
			switch(projType) {
				case INHOUSE:
					int confidentiality = Integer.parseInt(lastInput);
					createInHouse(vcs, managerName, projName, keywords, confidentiality);
					break;
				case OUTSOURCED:
					String companyName = lastInput;
					createOutsourced(vcs, managerName, projName, keywords, companyName);
					break;
				default:
					throw new UnknownProjectType();
			}
		} 
		catch (UnknownProjectType e) {
			System.out.println(UNKNOWN_PROJ_TYPE_MSG);
		} 
		catch (ManagerNameDoesNotExist e) {
			System.out.printf(MANAGER_DOES_NOT_EXIST_MSG, e.getManagerName());
		} 
		catch (ProjectAlreadyExistsException e) {
			System.out.printf(PROJECT_ALREADY_EXISTS_MSG, e.getProjectName());
		} 
		catch (ManagerClearanceIsLowerException e) {
			System.out.printf(MANAGER_HAS_CLEARANCE_MSG, e.getManagerName(), e.getManagerClearance());
		}
	}

	/**
	 * creates a new project specifically an out sourced project
	 * 
	 * @param vcs         object VersionControlSystem
	 * @param managerName String that corresponds to the manager name
	 * @param projName    String that corresponds to the project name
	 * @param keywords    List of Strings that correspond to the key words
	 *                    associated with the project
	 * @param companyName String that corresponds to the name of the company thats
	 *                    in charge of this project
	 * @throws ManagerNameDoesNotExist
	 * @throws ProjectAlreadyExistsException
	 */
	private static void createOutsourced(VersionControlSystem vcs, String managerName, String projName, // Done, not tested
			List<String> keywords, String companyName) throws ManagerNameDoesNotExist, ProjectAlreadyExistsException {

		vcs.addOutsourcedProject(managerName, projName, keywords, companyName); 
		System.out.printf(PROJECT_CREATED_MSG, projName);
	}

	/**
	 * creates a new project specifically an in house project
	 * 
	 * @param vcs             object VersionControlSystem
	 * @param managerName     String that corresponds to the manager name
	 * @param projName        String that corresponds to the project name
	 * @param keywords        List of Strings that correspond to the key words
	 *                        associated with the project
	 * @param confidentiality its the int that corresponds to the confidential level
	 *                        of the project
	 * @throws ManagerClearanceIsLowerException this exception is thrown if the
	 *                                          manager doesnt have a suficient
	 *                                          confidential level to manage this
	 *                                          project
	 * @throws ProjectAlreadyExistsException
	 * @throws ManagerNameDoesNotExist
	 */
	private static void createInHouse(VersionControlSystem vcs, String managerName, String projName, List<String> keywords, // Done, not tested
			int confidentiality) throws ManagerClearanceIsLowerException, ManagerNameDoesNotExist,
			ProjectAlreadyExistsException { // Done, not tested

		vcs.addInhouseProject(managerName, projName, keywords, confidentiality);
		System.out.printf(PROJECT_CREATED_MSG, projName);
	}

	private static void projectsCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		in.nextLine();
		Iterator<Project> it = null;
		
		try {
			it = vcs.getProjects();
			System.out.println(ALL_PROJECTS_MSG);
			
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
		catch (NoProjectsException e) {
			System.out.println(NO_PROJECTS_MSG);
		}
	}

	private static void teamCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		String managerName = in.next();
		String projName = in.nextLine().trim();
		int numberOfUsers = in.nextInt();
		List<String> toAddToTeam = new LinkedList<String>();
		for (int i = 0; i < numberOfUsers; i++) {
			toAddToTeam.add(in.next());
		}

		try {
			vcs.checkIfIsManager(managerName);
			vcs.checkIfProjectExistThrowIfDoesnt(projName);
			vcs.checkIfManagerIsProjectManager(projName, managerName);
			vcs.checkIfProjectIsOutSourced(projName);

			System.out.println(LATEST_TEAM_MEMBERS_MSG);

			Iterator<String> namesIt = toAddToTeam.iterator();
			while (namesIt.hasNext()) {
				String userName = namesIt.next();

				try {
					vcs.addUserToTeam(projName, managerName, userName);
					System.out.printf(ADDED_TO_TEAM_MSG, userName);
				}
				catch (UserIsntRegisteredException e) { 
					System.out.printf(DOES_NOT_EXIST_MSG, e.getUserName());
				}
				catch (UserAlreadyInTeamException e) {
					System.out.printf(AlREADY_IN_THE_TEAM_MSG, e.getUserName());
				}
				catch (UserDoesntHaveClearanceException e) {
					System.out.printf(INSUFFICIENT_CLEARANCE_MSG, e.getUserName());
				}
			}
		} 
		catch (ManagerNameDoesNotExist e) {
			System.out.printf(MANAGER_DOES_NOT_EXIST_MSG, e.getManagerName());
		} 
		catch (ProjectDoesNotExistException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		} 
		catch (ProjectIsOutSourcedException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		}
		catch (ManagerDoesntManageProjectException e) {
			System.out.printf(PROJECT_IS_MANAGED_BY_MSG, e.getProjectName(), e.getManagerName());
		}

	}

	private static void artefactsCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		String userName = in.next();
		String projName = in.nextLine().trim();

		String dateStr[] = in.nextLine().trim().split("-");
		int day = Integer.parseInt(dateStr[0]);
		int month = Integer.parseInt(dateStr[1]);
		int year = Integer.parseInt(dateStr[2]);
		LocalDate date = LocalDate.of(year, month, day);
		
		int numberOfArtefacts = in.nextInt();
		in.nextLine();

		List<String> lines = new LinkedList<String>();
		for (int i = 0; i < numberOfArtefacts; i++) {
			lines.add(in.nextLine() + "\n");
		}

		try {
			vcs.checkIfUserDoesntExist(userName);
			vcs.checkIfProjectExistThrowIfDoesnt(projName);
			vcs.checkIfProjectIsOutSourced(projName);
			vcs.checkIfUserIsNotInTeam(projName, userName);

			System.out.println(LATEST_ARTEFACTS_MSG);

			Iterator<String> linesIt = lines.iterator();
			while (linesIt.hasNext()) {
				Scanner artefactInput = new Scanner(linesIt.next());

				String artefactName = artefactInput.next();
				int confidentiality = artefactInput.nextInt();
				String description = artefactInput.nextLine().trim();
				artefactInput.close();
				
				
				try {
					vcs.addArtefactToProject(projName, userName, date, artefactName, confidentiality, description);
					System.out.printf(ART_ADDED_TO_PROJ_MSG, artefactName);
				} 
				catch (ArtefactAlreadyInProjectException e) {
					System.out.printf(ART_ALREADY_IN_PROJ_MSG, e.getArtefactName());
				} 
				catch (ArtefactExceedsProjectConfidentiality e) {
					System.out.printf(ART_EXCEEDS_CONF_MSG, e.getArtefactName());
				}
				
			}
		} 
		catch (UserIsntRegisteredException e) {
			System.out.printf(USER_DOES_NOT_EXIST_MSG, e.getUserName());
		} 
		catch (ProjectDoesNotExistException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		}
		catch (ProjectIsOutSourcedException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		}
		catch (UserIsNotInTeamException e) {
			System.out.printf(ISNT_MEMBER_OF_TEAM_MSG, e.getUserName(), e.getProjectName());
		}

	}

	private static void projectCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		String projName = in.nextLine().trim();
		String projInfo = null;

		try {
			projInfo = vcs.getProjectInfo(projName);
			System.out.print(projInfo);
		} 
		catch (ProjectDoesNotExistException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		} 
		catch (ProjectIsOutSourcedException e) {
			System.out.printf(PROJECT_IS_OUTSOURCED_MSG, e.getProjectName());
		}
	}

	private static void revisionCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		String userName = in.next();
		String projName = in.nextLine().trim();
		String artefactName = in.next();

		String date[] = in.next().split("-");
		int day = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int year = Integer.parseInt(date[2]);
		LocalDate revisionDate = LocalDate.of(year, month, day);

		String comment = in.nextLine().trim();

		int revisionNumber = 0;

		try {
			revisionNumber = vcs.reviseArtefact(userName, projName, artefactName, revisionDate, comment);
			System.out.printf(REVISON_SUBMITTED_MSG, revisionNumber, artefactName);
		} 
		catch (UserIsntRegisteredException e) {
			System.out.printf(USER_DOES_NOT_EXIST_MSG, e.getUserName());
		}
		catch (ProjectDoesNotExistException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		}
		catch(ProjectIsOutSourcedException e) {
			System.out.printf(PROJECT_DOES_NOT_EXIST_MSG, e.getProjectName());
		}
		catch (UserIsNotInTeamException e) {
			System.out.printf(ISNT_MEMBER_OF_TEAM_MSG, e.getUserName(), e.getProjectName());
		} 
		catch (UserDoesntHaveClearanceException e) {
			System.out.printf(CANT_ACCESS_ARTEFACT_MSG, e.getUserName());
		}
		catch(ArtefactDoesNotExistException e) {
			System.out.printf(ART_DOES_NOT_EXIST_MSG, e.getArtefactName());
		}
	}

	private static void managesCMD(Scanner in, VersionControlSystem vcs) { // DOne, not tested
		String managerName = in.nextLine().trim();
		String managerInfo = null;

		try {
			managerInfo = vcs.getManagerInfo(managerName);
			System.out.print(managerInfo);
		} 
		catch (ManagerNameDoesNotExist e) {
			System.out.printf(MANAGER_DOES_NOT_EXIST_MSG, managerName);
		}
	}

	private static void keywordCMD(Scanner in, VersionControlSystem vcs) { // Done, not tested
		String keyword = in.nextLine().trim();
		Iterator<Project> projIt = null;

		try {
			projIt = vcs.getProjectsWithKeyword(keyword);

			System.out.printf(ALL_PROJ_KEYWORD_MSG, keyword);
			while (projIt.hasNext()) {
				Project proj = projIt.next();
				System.out.println(proj.keywordPrintToString());
			}
		} 
		catch (NoProjectsWithKeyWordException e) {
			System.out.printf(NO_PROJ_KEYWORD_MSG, e.getKeyword());
		}
	}

	private static void confidentialityCMD(Scanner in, VersionControlSystem vcs) { // Done, tested
		int lowerLimit = in.nextInt();
		int upperLimit = in.nextInt();
		in.nextLine();

		if (lowerLimit > upperLimit) {
			int tmp = lowerLimit;
			lowerLimit = upperLimit;
			upperLimit = tmp;
		}

		Iterator<InHouse> projIt = null;

		try {
			projIt = vcs.getProjectsInLimits(lowerLimit, upperLimit);

			System.out.printf(ALL_PROJ_IN_LIMITS_MSG, lowerLimit, upperLimit);
			while (projIt.hasNext()) {
				System.out.println(projIt.next().confidentialityToString());
			}
		} catch (NoProjectsWithinLimitsException e) {
			System.out.printf(NO_PROJ_IN_LIMITS_MSG, e.getLowerLimit(), e.getUpperLimit());
		}
	}

	private static void workaholicCMD(Scanner in, VersionControlSystem vcs) { // Done, tested
		in.nextLine();
		Iterator<User> userIt = null;
		int numberWorkaholicsToPrint = 3;
		
		try {
			userIt = vcs.getWorkaholics();
			
			for(int i = 0; userIt.hasNext() && i < numberWorkaholicsToPrint; i++) {
				System.out.println(userIt.next().workaholicToString());
			}
		} 
		catch (NoUsersException e) {
			System.out.println(NO_WORKAHOLICS_MSG);
		} 
		catch (NoWorkaholicsException e) {
			System.out.println(NO_WORKAHOLICS_MSG);
		}
	}

	private static void commonCMD(Scanner in, VersionControlSystem vcs) { //Done, tested
		in.nextLine();

		try {
			System.out.println(vcs.getCommonInfo());
		} 
		catch (NoUsersException e) {
			System.out.println(NO_USERS_WITH_COMMON_PROJS);
		}
	}

}
