package com.bgomes.mathgame;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoresFragment extends Fragment implements OnClickListener {
	private TextView highScoreTableTitle;
	private TableLayout highScoreTable;
	private Button resetHighScores;
	
	private SharedPreferences savedValues;
	
	private int numOfRecords;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.activity_high_scores_fragment, 
									 container, false);
				
		highScoreTableTitle = (TextView) view.findViewById(R.id.highScoreTableTitleTextView);
		highScoreTable = (TableLayout) view.findViewById(R.id.highScoreTableLayout);
		resetHighScores = (Button) view.findViewById(R.id.resetHighScoresButton);
		
		resetHighScores.setOnClickListener(this);
		
		onResume();
		populateTable();
        
		return view;
	}
	
	public void onPause() {
		Editor editor = savedValues.edit();
	    editor.putInt("numOfRecords", numOfRecords);
	    
		editor.commit();
		super.onPause();
	}

	@Override
    public void onResume() {
        super.onResume();
        numOfRecords = savedValues.getInt("numOfRecords", 0);
    }
	
	@Override
	public void onClick(View v) {
    	
		switch(v.getId()) {
			case R.id.resetHighScoresButton:
				/*
				 * Toast t = Toast.makeText(getActivity(), "You selected to clear the record of high scores", Toast.LENGTH_SHORT);
				 * t.show();
				 */
				ScoreDB db = new ScoreDB(getActivity());
				db.deleteTable();
				getFragmentManager().beginTransaction().replace(android.R.id.content,  
						new HighScoresFragment()).commit();
				break;
		}
	}
	
	private void populateTable() {
		ScoreDB db = new ScoreDB(getActivity());
		
		numOfRecords = 10;
        // ArrayList<Score> scores = db.getScores(String.valueOf(numOfRecords));
        ArrayList<Score> scores = db.getScores();

	    TableRow rowHeader = new TableRow(getActivity());
	    rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
	    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
	    	TableLayout.LayoutParams.WRAP_CONTENT));
	    String[] headerText = {"User", "Final", "Correct", "Wrong"};
	    for(String c:headerText) {
	      	TextView tv = new TextView(getActivity());
	       	tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	       			TableRow.LayoutParams.WRAP_CONTENT));
	       	tv.setGravity(Gravity.LEFT);
	       	tv.setTextSize(18);
	       	tv.setPadding(5, 5, 5, 5);
	       	tv.setText(c);
	       	rowHeader.addView(tv);
	    }
	    highScoreTable.addView(rowHeader);
	        
	    for (Score s : scores) {
	      	String usr = s.getUserName();
	       	int fScr = s.getFinalScore();
	       	int cCnt = s.getCorrectCount();
	       	int wCnt = s.getWrongCount();
	       	
	       	TableRow row = new TableRow(getActivity());
	       	row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
	       			TableLayout.LayoutParams.WRAP_CONTENT));
	       	
	       	String[] colText = {usr, fScr+"", cCnt+"", wCnt+""};
	        	
	       	for(String text:colText) {
	       		TextView tv = new TextView(getActivity());
	       		tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	       				TableRow.LayoutParams.WRAP_CONTENT));
	       		tv.setGravity(Gravity.LEFT);
	       		tv.setTextSize(16);
	       		tv.setPadding(5, 5, 5, 5);
	       		tv.setText(text);
	       		row.addView(tv);
	       	}
	       	highScoreTable.addView(row);
	    } 
	}
	
}
