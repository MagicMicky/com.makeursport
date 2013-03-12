package com.makeursport;

import java.util.ArrayList;
import java.util.Locale;

import com.makeursport.gestionCourse.Course;

import android.content.Context;
import java.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Classe permettant de li�e une collection de Course a une ListView
 * @author L'�quipe MakeUrSport
 *
 */
public class HistoriqueAdapter extends BaseAdapter {

	/**
	 * La liste de courses que notre adapter doit g�rer
	 */
	private ArrayList<Course> listCourse;
	/**
	 * Le context de l'activit� en cours
	 */
	private Context mContext;
	/**
	 * Le layout inflater qui permet d'inflater des vues
	 */
	private LayoutInflater inflater;
	
	/**
	 * Cr�ation d'un Adapter pour gerer et afficher une liste de courses
	 * @param context le context de l'activity en cours
	 */
	public HistoriqueAdapter(Context context) {
		this.inflater=LayoutInflater.from(context);
		this.mContext=context;
		this.listCourse=new ArrayList<Course>();
	}


	/**
	 * Recupere le nombre d'item de notre {@link #listCourse}
	 * @return le nombre d'Item de l'adapter
	 */
	public int getCount() {
		
		return this.listCourse.size();
	}
	
	/**
	   * R�cup�rer un item de la liste en fonction de sa position
	   * @param position - Position de l'item � r�cup�rer
	   * @return l'item r�cup�r�
	  */
	public Course getItem(int position) {
		
		return listCourse.get(position);
	}

	/**
	 * R�cup�rer l'identifiant d'un Item de la liste en fonction de sa position
	 * @param position la position de l'Item � r�cup�rer
	 * @return l'id de l'Item
	 */
	public long getItemId(int position) {
		return this.listCourse.get(position).getId();
	}
	
	public View getView(int position, View convertview, ViewGroup parent) {
		LinearLayout layout =null;
		
		
		if(convertview!=null){
			layout=(LinearLayout) convertview;
		}
		else{
			layout=(LinearLayout)inflater.inflate(R.layout.element_layout, parent, false);
		}

		 //On inflate les vues qui composent l'adapter
		TextView infosCourse=(TextView)layout.findViewById(R.id.elem_info_course);
		//ImageView sportCourse=(ImageView)layout.findViewById(R.id.elem_icone);
		//TextView idCourse=(TextView) layout.findViewById(R.id.id_course);
		
		//On set les contenus 
		//idCourse.setText(this.getItem(position).getId()+"");
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		infosCourse.setText(mContext.getString(R.string.histo_course_titre,df.format(this.getItem(position).getDate()), this.getItem(position).getDistanceArrondi() + "" ));
		switch(this.getItem(position).getSport()){
			case COURSE : infosCourse.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.ic_action_course),null,null,null);
				break;
			case VELO : infosCourse.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.ic_action_velo),null,null,null);
				break;
			case ROLLER : infosCourse.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.ic_action_roller),null,null,null);
				break;
		}
		
		return layout;
	}

	/**
	 * Modifie la {@link #listCourse} et notify l'adapter des changements
	 * @param courses la liste des nouvelles courses
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.listCourse = courses;
		this.notifyDataSetChanged();
	}

}
