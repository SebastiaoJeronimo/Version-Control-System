package comparators;

import java.util.*;
import java.time.*;
import project.*;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 * this is a comparator used to sort the artefacts by date then by name
 */
@SuppressWarnings("unused")
public class ComparatorByDateThenName implements Comparator<Artefact> {

	public int compare(Artefact a1, Artefact a2) {
		int aux = - a1.getLastRevisionDate().compareTo(a2.getLastRevisionDate());
		if(aux != 0) {
			return aux;
		}
		return a1.getName().compareTo(a2.getName());
	}


}
