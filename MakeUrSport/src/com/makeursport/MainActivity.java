package com.makeursport;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.makeursport.R;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Activit� principale de MakeUrSport qui permet de g�rer
 * les diff�rents fragments de l'application
 * @author L'�quipe MakeUrSport
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	public static final int PARCOURSDIALOG_REQUESTCODE = 11;
	/**
	 * Tag utilis� pour le LOGCAT (affichage de message quand on debug)
	 */
	private final String LOGCAT_TAG=this.getClass().getCanonicalName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        FragmentManager fm = this.getSupportFragmentManager();//R�cup�ration d'un FragmentManager, pour gerer les fragments
        
        //Affichage du "home as up" de l'actionbar (petite fl�che qui indique que l'on peut clicker sur l'icone de l'appli
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        //On met la vue de "derri�re" (ce qu'affiche le menu) comme �tant menu_frame
        setBehindContentView(R.layout.menu_frame);
        
        
        //On remplace ce que contient menu_frame par un nouveau MenuFragment
        //qui affiche toutes les infos que l'on veut, c'est a dire
        //notre menu
        fm.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();

		//Param�trage du SlidingMenu
        SlidingMenu menu = getSlidingMenu();//On recupere le sliding menu que l'on vient de mettre
        menu.setMode(SlidingMenu.LEFT);//On dit qu'il est sur la gauche
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setFadeDegree(0.35f);
        menu.setFadeEnabled(true);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//Qu'on glisse en fonction des marges
        menu.setBehindOffsetRes(R.dimen.menu_behind_offset);//Et que l'on veut afficher un bout de notre vrai view
        													//m�me quand on slide
        
        //On affiche le contenu que l'on veut � l'ouverture de l'application, ici CourseEnCours()
        switchContent(new CourseEnCours());
        //ViewServer.get(this).addWindow(this);

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	//	ViewServer.get(this).removeWindow(this);
	}
	@Override
	public void onResume() {
		super.onResume();
	//	ViewServer.get(this).setFocusedWindow(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(LOGCAT_TAG, "onActivityResult : mainActivity " + requestCode);
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==PARCOURSDIALOG_REQUESTCODE) {
			switch(resultCode) {
			case Activity.RESULT_OK:
				float dist = data.getFloatExtra(ParcoursDialog.DISTANCE, 0);
				double lat = data.getDoubleExtra(ParcoursDialog.LATITUDE, 0);
				double lon = data.getDoubleExtra(ParcoursDialog.LONGITUDE, 0);
				LatLng ptDepart = new LatLng(lat,lon);
				Log.d("DIST", dist+"");
				Log.d("MA LATLNG", "lat:"+ptDepart.latitude + " lon:" + ptDepart.longitude);
				
				GenerationParcoursATask parcours = new GenerationParcoursATask(dist,this);
				parcours.execute(ptDepart);
				
				break;
			case Activity.RESULT_CANCELED:
				break;
				default:
			}		
		} else {
			this.getSupportFragmentManager().findFragmentById(R.id.main_layout).onActivityResult(requestCode, resultCode, data);
		}

		
	}
	
	/**
	 * Permet d'ouvrir le fragment de courseEnCours, avec les bonnes infos
	 
	public void ouvrirFragmentCourseEnCours(int anneeNaissance, int taille, float poids) {
        //On d�finit nos arguments pour la CourseEnCours
        Bundle args = new Bundle();
        args.putInt(this.getString(R.string.SP_taille),taille);
        args.putInt(this.getString(R.string.SP_annee_naissance), anneeNaissance);
        args.putFloat(this.getString(R.string.SP_poids), poids);
        
        
        //On cr�er notre nouvelle CourseEnCours()
        CourseEnCours course = new CourseEnCours();
        course.setArguments(args);//On lui rajoute ces arguments
        this.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.main_layout, course)
        .commitAllowingStateLoss();//Et on affiche ce fragment.
	}*/
	/**
	 * Change le contenu du fragment principal
	 * @param f le nouveau fragment
	 */
	public void switchContent(Fragment f) {
		this.getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.replace(R.id.main_layout, f)
		.commit();
		
		getSlidingMenu().showContent();
	}
	/**
	 * Rajoute un fragment a la backtrace (quand on fait back on retourne dessus)
	 * @param f le nouveau fragment
	 */
	public void addFragment(Fragment f) {
		this.getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.replace(R.id.main_layout, f)
		.addToBackStack("AddedFragment")
		.commit();
	}
	/*public void removeFragment(Fragment f) {
		this.getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.remove(f)
		.commit();
		this.getSupportFragmentManager().popBackStack();
	}*/
}
