package comparators;

import java.util.Comparator;

import user.User;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 *this is the comparator used for the worka
 */
public class UserComparatorForWorkaholic implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		int aux = o1.getNumberOfRevisions() - o2.getNumberOfRevisions();
		if(aux != 0) {
			return - aux; //ordem descendente
		}
		
		aux = o1.getNumberOfWorkingAtProjects() - o2.getNumberOfWorkingAtProjects();
		if(aux != 0) {
			return - aux; // ordem decendente
		}
		
		aux = - o1.getDateOfLastRevision().compareTo(o2.getDateOfLastRevision());
		if(aux != 0) {
			return aux; // ordem decendente
		}
		
		return o1.getName().compareTo(o2.getName());
	}
}
