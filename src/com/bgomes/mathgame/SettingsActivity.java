package com.bgomes.mathgame;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, 
				new SettingsFragment()).commit();
	}
}
