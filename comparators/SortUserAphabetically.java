package comparators;

import java.util.Comparator;

import user.User;

/**
 * @author utilizador Goncalo Carvalho<br>Sebastiao Jeronimo
 *this comparator is used to sort users alphabetically 
 */
public class SortUserAphabetically implements Comparator<User>{
	
	@Override
	public int compare(User o1, User o2) {
		// TODO fazer cenas para agradar a prof Carla
		return o1.getName().compareTo(o2.getName());
	}

}
