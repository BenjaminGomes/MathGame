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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class RevealScoreFragment extends Fragment {
	
	private TextView userScore;
	private TextView userName;
	private TextView wrongAnswers;
	private TextView correctAnswers;
	private TextView highScore;
	private TextView highScoreList;
	private TableLayout table;
	
	private int correctCount = 0,
			    wrongCount = 0,
			    finalScore = 0;
	
	private String user;
	
	private boolean newHighScore = false;
	
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
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.activity_reveal_score_fragment, 
									 container, false);
		
		userScore = (TextView) view.findViewById(R.id.userScoreTextView);
		userName = (TextView) view.findViewById(R.id.userNameTextView);
		wrongAnswers = (TextView) view.findViewById(R.id.wrongAnswersTextView);
		correctAnswers = (TextView) view.findViewById(R.id.correctAnswersTextView);
		highScore = (TextView) view.findViewById(R.id.highScoreTextView);
		highScoreList = (TextView) view.findViewById(R.id.highScoreTableTitleTextView);
		table = (TableLayout)view.findViewById(R.id.highScoreTableLayout);
		
		onResume();
		
		// get db object
		ScoreDB db = new ScoreDB(getActivity());
		
        // insert a task
        Score score = new Score(user, finalScore, correctCount, wrongCount);
        
        db.insertScore(score);
        int lastId = db.getLastId();
        // Toast t = Toast.makeText(getActivity(), "lastId = " + lastId, Toast.LENGTH_LONG);
		// t.show();
		
		int highScoreId = db.getHighScore();
		
		if (lastId == highScoreId) {
			newHighScore = true;
		}
        // display all tasks (id + name)
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
        table.addView(rowHeader);
        
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
        	table.addView(row);
        }
        // db.deleteTable();
		return view;
	}
	
	public void onPause() {
		Editor editor = savedValues.edit();
		
		editor.putString("user", userName.getText().toString());
		editor.putInt("correctCount", correctCount);
	    editor.putInt("wrongCount", wrongCount);
	    editor.putInt("finalScore", finalScore);
	    
		editor.commit();
		super.onPause();
	}

	@Override
    public void onResume() {
        super.onResume();
        user = savedValues.getString("userName", "Guest");
        wrongCount = savedValues.getInt("wrongCount", 0);
        correctCount = savedValues.getInt("correctCount", 0);
        finalScore = savedValues.getInt("finalScore", 0);
        
        showResults();
    }
	
	private void showResults() {
		if (correctCount < wrongCount) {
			finalScore = 0;
		} else {
			finalScore = correctCount - wrongCount;
		}

		userName.setText(user);
		correctAnswers.setText(String.valueOf(correctCount));
		wrongAnswers.setText(String.valueOf(wrongCount));
		userScore.setText(String.valueOf(finalScore));
		if (!newHighScore) {
			highScore.setVisibility(View.GONE);
		} else {
			highScore.setVisibility(View.VISIBLE);
		}
	}
}

/*
 * The following explains how to read from SQLite database and create a tableRow with data
 * http://www.worldbestlearningcenter.com/tips/Android-sqlite-table-layout-example.htm
 */
