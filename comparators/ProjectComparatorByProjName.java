package comparators;

import java.util.Comparator;

import project.Project;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 * this comparator is used to sort projects by name
 */
public class ProjectComparatorByProjName implements Comparator<Project> {

	@Override
	public int compare(Project o1, Project o2) {
		return o1.getProjName().compareTo(o2.getProjName());
	}

}
