package comparators;

import java.util.*;

import project.*;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 * this is a comparator used to sort revisions by date if they have the same date then by revision number the by project name
 */
public class ComparatorByDateThenRevisionNumberThenProjectName implements Comparator<Revision>{
	public int compare(Revision r1,Revision r2){
		
		int aux = r1.getDate().compareTo(r2.getDate());
		if(aux != 0) {
			return -aux; // - because it s descending order
		}
		
		aux = r1.getRevisionNumber() - r2.getRevisionNumber();
		if(aux != 0) {
			return - aux; // - because it s descending order
		}
		
		return r1.getProjectName().compareTo(r2.getProjectName()); // normal, alphabetical order
	}
}
