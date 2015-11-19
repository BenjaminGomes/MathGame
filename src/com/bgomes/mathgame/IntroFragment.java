package com.bgomes.mathgame;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class IntroFragment extends Fragment implements OnClickListener {
	
	private Button newGame;
	private Button highScores;
	
	private SharedPreferences savedValues;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_intro_fragment, container, false);
		
		newGame = (Button) view.findViewById(R.id.newGameButton);
		highScores = (Button) view.findViewById(R.id.highScoresButton);
		
		newGame.setOnClickListener(this);
		highScores.setOnClickListener(this);
		
		return view;
	}
	
	/*
	 * I'm not sure if the onPause/onResume (SharedPreferences) will be needed in this fragment
	 */
	@Override
    public void onPause() {
        // save the instance variables       
        Editor editor = savedValues.edit();
        // editor.putString("userName", userName.getText().toString());
        editor.commit();        

        super.onPause();      
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // userName.setText(savedValues.getString("userName", ""));
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.newGameButton:
				getFragmentManager().beginTransaction().replace(android.R.id.content,  new SelectGameFragment()).commit();
				break;
			case R.id.highScoresButton:
				getFragmentManager().beginTransaction().replace(android.R.id.content,  new HighScoresFragment()).commit();
				break;
		}
		
	}
}
