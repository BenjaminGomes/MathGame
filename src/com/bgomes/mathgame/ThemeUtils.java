package com.bgomes.mathgame;

import android.app.*;
import android.content.Intent;

public class ThemeUtils {
	private static int cTheme;
	public final static int BLACK = 0;
	public final static int BLUE = 1;
	
	public static void changeToTheme(Activity activity, int theme) {
		cTheme = theme;
		activity.finish();
		
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	
	public static void onActivityCreateSetTheme(Activity activity) {
		switch (cTheme) {
			case BLACK:
				activity.setTheme(R.style.BlackTheme);
				break;
			case BLUE:
				activity.setTheme(R.style.BlueTheme);
				break;
		}
	}
	
}

/*
	***********************************************************
	*														  *
	*	The above code isn't used as I was unable to have the *
	*	program change the theme.							  *
	*   													  *
	***********************************************************
 *
 * Used the following web site as a reference to dynamically allocate
 * themes:
 * http://www.developer.com/ws/android/changing-your-android-apps-theme-dynamically.html
 */