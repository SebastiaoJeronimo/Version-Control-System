package project;

import java.time.*;
import java.util.*;

import user.*;

public interface Project {
	
	LocalDate getDateOflastChange();
	
	void setDateOfLastChange(LocalDate date);
	
	public void incNumberOfRevisions();
	
	int getNumberOfRevisions();
	
	String getProjName();
	
	Iterator<String> getKeywords();
	
	boolean hasKeyword(String key);

	Manager getManager();
	
	String toString();
	
	String keywordPrintToString();
}
