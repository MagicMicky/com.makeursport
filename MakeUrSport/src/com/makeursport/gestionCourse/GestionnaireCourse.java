package com.makeursport.gestionCourse;

import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Gestionnaire d'une course, permettant d'int�ragit avec celle ci.
 * @author l'�quipe MakeUrSport
 * 
 */
public class GestionnaireCourse {
	private final static String LOGCAT_TAG="GestionnaireCourse";
	private Course maCourse;
	
	public GestionnaireCourse(){
		this.maCourse = new Course();
	}
	
	/**
     * Met a jour les infos de la course
     * @param vitesseReelle la vitesse de l'utilisateur en m/s
     * @param loc1 le premier point (pour la distance)
     * @parma loc2 le deuxieme point
     */
    public void mettreAJourCourse(float vitesseReelle, Location loc1, Location loc2) {
    	Log.v(LOGCAT_TAG, "Mise � jour de la course : v:" + (vitesseReelle*3.6f) + " - lat:" + loc2.getLatitude());
    	this.maCourse.setVitesseReelle(vitesseReelle * 3.6f);
    	//this.maCourse.setDistance(distance);
    	this.maCourse.addDistance(this.calculerDistance(loc1, loc2));
    }
    /**
     * Demarre la course
     * @param c le contexte de l'activit� en cours, pour pouvoir r�cuperer le sportif depuis les pr�f�rences
     */
    public void demarrerCourse(Context c) {
    	Log.d(LOGCAT_TAG, "Course d�marr�e");
    	//On recupere le Sport mis en place par l'utilisateur
    	Sport s = this.maCourse.getSport();
    	this.maCourse = new Course();
    	this.maCourse.setSport(s);
    	this.maCourse.setUser(Sportif.fromPrefs(c));
    	this.maCourse.setEtatCourse(EtatCourse.CourseLancee);
    }
    /**
     * Met une course en pause
     */
    public void mettreEnPauseCourse() {
    	Log.v(LOGCAT_TAG,"Course mise en pause...");
    	this.maCourse.setEtatCourse(EtatCourse.CourseEnPause);
    }
    /**
     * Sort une course de pause
     */
    public void reprendreCourse() {
    	Log.v(LOGCAT_TAG, "Course reprise !");
    	this.maCourse.setEtatCourse(EtatCourse.CourseLancee);
    }
    /**
     * Arrete une course
     */
    public void stopperCourse() {
    	Log.v(LOGCAT_TAG, "Course arr�t�e.");
		this.maCourse.setEtatCourse(EtatCourse.CourseArretee);
    }
    /**
     * Recupere l'�tat de la course en cours
     * @return l'etat de la course
     */
    public EtatCourse getEtatCourse() {
    	return this.maCourse.getEtatCourse();
    }
    /**
     * Modifie l'�tat de la course
     * @param etatCourse 
     */
    public void setEtatCourse(EtatCourse etatCourse) {
    	this.maCourse.setEtatCourse(etatCourse);
    }
    /**
     * recupere la course en cours
     * @return la course
     */
	public Course getCourse() {
		return this.maCourse;
	}
	/**
	 * Remplace la course en cours par une nouvelle course
	 * @param c la nouvelle course
	 */
	public void setCourse(Course c) {
		this.maCourse = c;
	}
	
	/**
	 * Calcule la distance entre deux points
	 * @param loc1 le premier point
	 * @param loc2 le deuxieme point
	 * @return distance parcouru entre les points, en km
	 */
	private float calculerDistance(Location loc1, Location loc2) {
	   double earthRadius = 6371;
	   double dLat = Math.toRadians(loc2.getLatitude()-loc1.getLatitude());
	   double dLng = Math.toRadians(loc2.getLongitude()-loc1.getLongitude());
	   double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	              Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
	              Math.sin(dLng/2) * Math.sin(dLng/2);
	   double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	   double dist = earthRadius * c;
	   Log.v(LOGCAT_TAG + "_calculerDistance", dist + "");
	   return Double.valueOf(dist).floatValue();
	}
	
	
}
