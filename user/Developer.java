package user;


import project.*;

public interface Developer extends User {

	String getManagerName();

	void addRevision(Revision rev);
	
}
