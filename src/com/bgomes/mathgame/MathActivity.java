package com.bgomes.mathgame;

/*
 * Ben Gomes
 * IS 295 (Spring 2015) / Daniel
 * Semester Project
 */

/*
	*************************************************************************
	*														  				*
	*	The following application will load the MathActivity, which then	*
	*	loads the following fragments of the app. as needed. The user is 	*
	*	first presented with the IntroFragment which allows users to select	*
	*	to either play a new game or review the high scores. If the High	*
	*	Scores button is selected, the HighScoresFragment will load which 	*
	*	currently only reveals the top 5 high scores. The fragment does		*
	*	allow the user to reset the values entered into the Score table 	*
	*	(where score information is appropriately stored). Also, the way 	*
	*	that the user navigates back to the IntroFragment is via a settings	*
	*	option titled, "Return To Main Menu". The other option in the 		*
	*	IntroFragment is to start a new game which launches the 			*
	*	SelectGameFragment. In this page, the user can edit their user name	*
	*	as well as select which type of addition game they wish to play (+, *
	*	-, x, /). However, I was only able to create the game for addition	*
	*	so far. The others will prompt with a Toast message so the user can	*
	*	see they are responsive. Once the user selects the game type (+),	*
	*	the following AddQuestionFragment is launched which starts a 60 	*
	*	second timer, where the user must answer as many questions correctly*
	*	as they can within the time provided. The questions are randomly	*
	*	generated as well as the answers and location of the answers. The 	*
	*	numbers used for the questions are also going to be > 0, but < 100. *
	*	After the user's time runs out, the last RevealScoreFragment is 	*
	*	loaded. In this page, the user is shown their final score, which is	*
	*	equal to the number of questions answered correctly minus the number*
	*	answered incorrectly. If the user got a high score, there is a text *
	*	view that is shown informing them so. In the bottom half of the page*
	*	the user is shown the top five high scores (if they exist).			*
	*																		*
	*	I was also unable to add a notification portion in my app as well as*
	*	change any sort of theme (text, background color, etc.). I also		*
	*	developed the program using Genymotion Emulator, and all was working*
	*	well with the settings menu. However, upon last minute testing of 	*
	*	the Eclipse Emulator we set up in class, the item that can be		*
	*	clicked to bring up the settings menu disappeared.					*
	*   													  				*
	*************************************************************************
*/
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
// import android.content.Intent;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MathActivity extends Activity {
	
	private int themeType = 0;
	private SharedPreferences prefs;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*
			***********************************************************
			*														  *
			*	I was unable to get the SettingsFragment to save and  *
			*	update the application's theme so I am commenting out *
			*	the PreferenceManager setup as it isn't needed		  *
			*														  *	   
			***********************************************************
         *	PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
         *	prefs = PreferenceManager.getDefaultSharedPreferences(this);
         *	setTheme();
		 */
        
        // setContentView(R.layout.activity_math);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
        					 new IntroFragment()).commit();
    }
    /*
     			***********************************************************
     			*														  *
     			*	I was unable to get the SettingsFragment to save the  *
     			*	theme correctly with the ThemeUtils.java file. 		  *
     			*	Part 1 of 2	(Large comment blocks)					  *
     			*   													  *
     			***********************************************************
	 *  @Override
	 *  public void onPause() {
	 *   	// save the instance variables       
	 *	    Editor editor = prefs.edit();
	 *	    editor.commit(); 
	 *    
	 *  	super.onPause();
	 *  }
	 *   
	 *  @Override
	 *   public void onResume() {
	 *   	super.onResume();
	 *   	themeType = Integer.parseInt(prefs.getString("pref_themetype", "0)"));
	 *   }
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.math, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(getApplicationContext(), 
            		SettingsActivity.class));
            return true;
        } else if (id == R.id.mainmenu_settings) {
        	getFragmentManager().beginTransaction().replace(android.R.id.content, 
        						 new IntroFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }
    /*
     			***********************************************************
     			*														  *
     			*	I was unable to get the SettingsFragment to save the  *
     			*	theme correctly with the ThemeUtils.java file		  *
     			*	Part 2 of 2	(Large comment blocks)					  *
     			*   													  *
     			***********************************************************
    *	private void setTheme() {
    *		switch(themeType) {
    *			case 1:
    *				this.setTheme(R.style.BlueTheme);
    *				break;
    *			case 2:
    *				this.setTheme(R.style.BlackTheme);
    *				break;
    *			default:
    *				break;
    *		}
    *	}
    */
}
