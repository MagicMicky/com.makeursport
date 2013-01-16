package com.makeursport.gestionCourse;
/**
 * Enum des sports g�rer par l'application
 * @author L'�quipe MakeUrSport
 * 
 */
public enum Sport {
	COURSE(0),
	VELO(1),
	ROLLER(2);
	
	private final int sport;
	
	Sport(int i) {
		this.sport=i;
	}
	
	public int getSportInt() {
		return sport;
	}
	
	public static Sport getSport(int i) {
		Sport s;
		switch(i) {
		case 0:
			s=Sport.COURSE;
			break;
		case 1:
			s=Sport.VELO;
			break;
		case 2:
			s=Sport.ROLLER;
			break;
		default:
			s=Sport.COURSE;
		}
		return s;
	}
}
