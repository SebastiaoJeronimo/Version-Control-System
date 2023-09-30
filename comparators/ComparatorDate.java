package comparators;

import java.util.*;

import project.*;

/**
 * @author Goncalo Carvalho <br> Sebastiao Jeronimo	
 * compares and its used for sorting the revisions by date 
 */
public class ComparatorDate implements Comparator<Revision> {
	@Override
	public int compare(Revision r1, Revision r2) {
		if (r1 == null || r2 == null) {
			if (r1 == null && r2 == null)
				return 0;
			if (r1 == null)
				return 1;
			//if (r2 == null)  // not needed because already checked
			return -1;
		}
		
		int aux = - r1.getDate().compareTo(r2.getDate()); //ordem descendente
		if(aux != 0)
			return aux; 
		
		return - (r1.getRevisionNumber() - r2.getRevisionNumber()); //ordem descendente
	}

}
