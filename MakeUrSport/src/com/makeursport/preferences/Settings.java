package com.makeursport.preferences;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.makeursport.R;
/**
 * Activity des param�tre. Utilise l'ancienne API de param�tre
 * car la librairie de compatibilit� n'importe pas les nouvelles m�thodes
 * @author L'�quipe MakeUrSport
 */
public class Settings extends SherlockPreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
	}

}
