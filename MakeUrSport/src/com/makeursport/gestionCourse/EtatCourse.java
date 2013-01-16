package com.makeursport.gestionCourse;
/**
 * Enum des trois �tat de la course
 *
 */
public enum EtatCourse {
	CourseLancee(0),
	CourseArretee(1),
	CourseEnPause(2);
	private final int statut;
	EtatCourse(int i) {
		this.statut=i;
	}
	double getStatusInt() {
		return statut;
	}
	
	
	}
