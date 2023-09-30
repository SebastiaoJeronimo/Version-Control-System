package comparators;

import java.time.*;
import java.util.*;

import project.*;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 * this comparator sorts projects by date if they have the same date sorts by revision name
 */
public class ComparatorByTypeDateNumberofrevisionsName implements Comparator<Project> {
	
	@Override
	public int compare(Project o1, Project o2) {
		int d1 = (o1 instanceof InHouse)? 0 : 1;
		int d2 = (o2 instanceof InHouse)? 0 : 1;
		
		int aux = d1 - d2;
		if(aux != 0) {
			return aux;	// InHouse primeiro, Outsourced depois
		}
		
		if(o1 instanceof InHouse) {
			boolean nullPointerInO1 = false;
			boolean nullPointerInO2 = false;
			LocalDate DateOfo1 = null;
			LocalDate DateOfo2 = null;
			
			
			DateOfo1 = o1.getDateOflastChange();
			if(DateOfo1 == null)  nullPointerInO1 = true;
		
			DateOfo2 = o2.getDateOflastChange();
			if(DateOfo2 == null)  nullPointerInO2 = true;
			
			
			if (nullPointerInO1 || nullPointerInO2) {
				aux = 0;
				if(nullPointerInO1 && !nullPointerInO2) {
					aux = -1;
				}
				if(!nullPointerInO1 && nullPointerInO2) {
					aux = 1;
				}
			}
			else {
				aux = - DateOfo1.compareTo(DateOfo2); //ordem descendente
			}
			
			
			if(aux != 0) {
				return aux;
			}
			
			
			aux = - (o1.getNumberOfRevisions() - o2.getNumberOfRevisions()); //ordem descendente
			if(aux != 0) {
				return aux;
			}
		}
		return o1.getProjName().compareTo(o2.getProjName()); // ordem alfabetica
	}
}
