package project;


import java.util.*;

import user.*;

public class OutsourcedClass extends ProjectClass implements Outsourced {

	
	private String kompany; //Vincent Jean Mpoy Kompany
	
	public OutsourcedClass(String projName, Manager manager, List<String> keywords, String company) {
		super(projName, manager, keywords);
		this.kompany = company;
	}

	public String getCompany() {
		return kompany;
	}
	
	public String toString() {
		return "outsourced " + super.toString() + " and developed by " + this.getCompany();
		
	}
	
	public String keywordPrintToString() {
		return this.toString();
	}
}
