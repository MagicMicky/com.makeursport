package com.makeursport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMapOptions;
import com.makeursport.gestionCourse.Course;
import com.makeursport.gestionCourse.GestionnaireHistorique;
/**
 * Fragment permettant l'affichage d'une course termin�e.
 * @author L'�quipe MakeUrSport
 *
 */
public class CourseFragment extends SherlockFragment{
	private static final String LOGCAT_TAG = "CourseFragment";
	/**
	 * Le String permettant de recuperer l'id pass� en intent/bundle
	 */
	public static final String ID = "com.makeursport.ID";
	private TextView calories=null;
	private TextView vitesseMoy=null;
	private TextView duree=null;
	private TextView distance=null;
	/**
	 * Le gestionnaire de l'historique avec lequel on communique a la db
	 */
	private GestionnaireHistorique historique;
	/**
	 * L'id de la course en cours
	 */
	private int courseId=-1;
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	    View monLayout=inflater.inflate(R.layout.activity_course, container,false);
	   
	    /**
	     * On r�cup�re l'id de la course click�e
	     */
	    if(this.getArguments()!=null && this.getArguments().containsKey(ID)){
	    	this.courseId=this.getArguments().getInt(ID);
	    }
	    
		//On met dans sa place r�serv�, le MapFragment
		FragmentManager fm = this.getChildFragmentManager(); 
		//Pour creer un nouveau MyMapFragment, on utilise newInstance
		//Plut�t qu'un constructeur. Conseill� par la doc
		MyMapFragment mapFragment = MyMapFragment.newInstance(new GoogleMapOptions().zoomControlsEnabled(false));
		fm.beginTransaction()
		.replace(R.id.mapfragment_location, mapFragment)
		.commit();
	    
	    
	    /**
	     * Requ�te de r�cupr�ration de la course
	     */
	    this.historique=new GestionnaireHistorique(this);
	    this.historique.selectionnerCourse(courseId);
	    
	    /**
	     * On associe les vues avec les ressources 
	     */
	    this.vitesseMoy=(TextView)monLayout.findViewById(R.id.TV_vit_moyenne_valeur);
	    this.duree=(TextView)monLayout.findViewById(R.id.TV_duree);
	    this.distance=(TextView)monLayout.findViewById(R.id.TV_distance_valeur);
	    this.calories=(TextView)monLayout.findViewById(R.id.TV_calories_valeur);
	    
	    
		this.setHasOptionsMenu(true);//On signal que l'on veut recevoir les appels concernant le menu de l'action bar

	    return monLayout;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.w(LOGCAT_TAG, "Passage dans le onCreateOptionsMenu");
		inflater.inflate(R.menu.course_activity, menu);
	}
	
	/**
	 * On met � jour toute les vues avec la course r�cup�r�e grace � la requ�te SQL
	 * @param course la course click�e
	 */
	public void modifView(Course course){
		this.calories.setText(course.getCaloriesBrulees()+"");
		this.distance.setText(course.getDistanceArrondi()+ getString(R.string.unite_distance));
		this.vitesseMoy.setText(course.getVitesseMoyenne()+ getString(R.string.unite_vitesse));
    	long duree = course.getDuree();
    	this.duree.setText(String.format("%dh%dm%ds", duree/(3600), (duree%3600)/(60), (duree%(60))));

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.MenuBT_delete:
			this.historique.supprimerCourse(courseId);
			break;
		case R.id.MenuBT_share:
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			//J'ai fait une course de x km en y min, tu peux pas test
			i.putExtra(Intent.EXTRA_TEXT, this.getString(R.string.mess_partage, distance.getText(), duree.getText()));
			this.startActivity(Intent.createChooser(i, this.getString(R.string.partage_title)));
			break;
		case android.R.id.home:
			this.retourHistoriqueFragment();
		}
		return true;
	}

	public void retourHistoriqueFragment() {
		FragmentManager fm = this.getSherlockActivity().getSupportFragmentManager();
		fm.beginTransaction()
		.remove(this)
		.commit();
		fm.popBackStack();
	}

}
