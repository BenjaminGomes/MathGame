package com.bgomes.mathgame;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestionFragment extends Fragment implements OnClickListener {

	private TextView timerLabel;
	private TextView timer;
	private TextView mathQuestion;
	private Button choiceA;
	private Button choiceB;
	private Button choiceC;
	private Button choiceD;
	
	private SharedPreferences savedValues;
	
	private int secs = 0,
			    mils = 0,
			    correctCount = 0,
			    wrongCount = 0,
			    value1 = 0,
			    value2 = 0,
			    answer = 0;

	private double formatTimeValue = 0.00;
	private String timeValue = "00.00";
	private CountDownTimer countTimer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());
		correctCount = 0;
		wrongCount = 0;
		onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_add_question_fragment,
                                     container, false);

        // get references to the widgets
        timerLabel = (TextView) view.findViewById(R.id.timerLabelTextView);
        timer = (TextView) view.findViewById(R.id.timerTextView);
        mathQuestion = (TextView) view.findViewById(R.id.mathQuestionTextView);
        choiceA = (Button) view.findViewById(R.id.choiceAButton);
        choiceB = (Button) view.findViewById(R.id.choiceBButton);
        choiceC = (Button) view.findViewById(R.id.choiceCButton);
        choiceD = (Button) view.findViewById(R.id.choiceDButton);
        
        // set the listeners
        choiceA.setOnClickListener(this);
        choiceB.setOnClickListener(this);
        choiceC.setOnClickListener(this);
        choiceD.setOnClickListener(this);
        
        
        countTimer = new CountDownTimer(60000, 10) {
        	public void onTick (long millisecsLeft) {
        		secs = (int) (millisecsLeft / 1000);
        		mils = (int) (millisecsLeft % 1000);
        		
        		timeValue = Integer.toString(secs) + "." + Integer.toString(mils);
        		formatTimeValue = Double.parseDouble(timeValue);
        		NumberFormat nFormat = new DecimalFormat("#0.00");
        		timer.setText(nFormat.format(formatTimeValue));
        	}
        	
        	public void onFinish() {
        		timer.setText("0.00");
        		choiceA.setEnabled(false);
        		choiceB.setEnabled(false);
        		choiceC.setEnabled(false);
        		choiceD.setEnabled(false);
        		
				onPause();
				getFragmentManager().beginTransaction().replace(android.R.id.content,  
								new RevealScoreFragment()).commit();
        	}
        }.start();

        loadQuestion();
        
        return view;
	}
	
	@Override
    public void onPause() {
        // save the instance variables       
        Editor editor = savedValues.edit();        
        // editor.putString("userName", userName.getText().toString());
        editor.putInt("correctCount", correctCount);
        editor.putInt("wrongCount", wrongCount);
        editor.commit();
        /*
         * Find a way to be able to leave the application screen while timer is going,
         * but in such a way that the application does not allow to the timer to continue
         * to count down and crash application
         */
        // countTimer.cancel();

        super.onPause();      
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // userName.setText(savedValues.getString("userName", "Guest"));
        wrongCount = savedValues.getInt("wrongCount", 0);
        correctCount = savedValues.getInt("correctCount", 0);
    }
    
    @Override
	public void onClick(View v) {
    	
		switch(v.getId()) {
			case R.id.choiceAButton:
				testAnswer("A");
				break;
			case R.id.choiceBButton:
				testAnswer("B");
				break;
			case R.id.choiceCButton:
				testAnswer("C");
				break;
			case R.id.choiceDButton:
				testAnswer("D");
				break;
		}
	}
    
    private void loadQuestion() {
    	int  option0 = 5,
			 option1 = 5,
			 option2 = 5,
			 option3 = 5;
    	
    	Random randomGen = new Random();

    	value1 = randomGen.nextInt(99) + 1;
    	value2 = randomGen.nextInt(99) + 1;
    	answer = value1 + value2;
    	mathQuestion.setText("Select the answer: " + value1 + " + " + value2 + " =");
    	
    	option0 = randomGen.nextInt(4);
    	option1 = randomGen.nextInt(4);
    	while (option1 == option0) {
    		option1 = randomGen.nextInt(4);
    	}
    	option2 = randomGen.nextInt(4);
    	while ((option2 == option0) || (option2 == option1)) {
    		option2 = randomGen.nextInt(4);
    	}
    	option3 = randomGen.nextInt(4);
    	while ((option3 == option0) || (option3 == option1) || (option3 == option2)) {
    		option3 = randomGen.nextInt(4);
    	}
    	
    	switch(option0) {
    		case 0:
    			choiceA.setText(String.valueOf(answer));
    			break;
    		case 1:
    			choiceB.setText(String.valueOf(answer));
    			break;
    		case 2:
    			choiceC.setText(String.valueOf(answer));
    			break;
    		case 3:
    			choiceD.setText(String.valueOf(answer));
    			break;
    	}
    	switch(option1) {
			case 0:
				choiceA.setText(String.valueOf(answer + (randomGen.nextInt(9)+1)));
				break;
			case 1:
				choiceB.setText(String.valueOf(answer + (randomGen.nextInt(9)+1)));
				break;
			case 2:
				choiceC.setText(String.valueOf(answer + (randomGen.nextInt(9)+1)));
				break;
			case 3:
				choiceD.setText(String.valueOf(answer + (randomGen.nextInt(9)+1)));
				break;
    	}
    	switch(option2) {
			case 0:
				choiceA.setText(String.valueOf(answer - (randomGen.nextInt(9)+1)));
				break;
			case 1:
				choiceB.setText(String.valueOf(answer - (randomGen.nextInt(9)+1)));
				break;
			case 2:
				choiceC.setText(String.valueOf(answer - (randomGen.nextInt(9)+1)));
				break;
			case 3:
				choiceD.setText(String.valueOf(answer - (randomGen.nextInt(9)+1)));
				break;
    	}
    	switch(option3) {
			case 0:
				choiceA.setText(String.valueOf(answer + 10));
				break;
			case 1:
				choiceB.setText(String.valueOf(answer - 10));
				break;
			case 2:
				choiceC.setText(String.valueOf(answer + 10));
				break;
			case 3:
				choiceD.setText(String.valueOf(answer - 10));
				break;
    	}
    	
    }
    
    private void testAnswer(String sOption) {
    	String numString = "";
    	int num = 0;
    	
    	if (sOption.equalsIgnoreCase("A")) {
    		numString = choiceA.getText().toString();
    		num = Integer.parseInt(numString);
    		if (num == answer) {
    			correctCount ++;
    		} else {
    			wrongCount ++;
    		}
    	} else if (sOption.equalsIgnoreCase("B")) {
    		numString = choiceB.getText().toString();
    		num = Integer.parseInt(numString);
    		if (num == answer) {
    			correctCount ++;
    		} else {
    			wrongCount ++;
    		}
    	} else if (sOption.equalsIgnoreCase("C")) {
    		numString = choiceC.getText().toString();
    		num = Integer.parseInt(numString);
    		if (num == answer) {
    			correctCount ++;
    		} else {
    			wrongCount ++;
    		}
    	} else if (sOption.equalsIgnoreCase("D")) {
    		numString = choiceD.getText().toString();
    		num = Integer.parseInt(numString);
    		if (num == answer) {
    			correctCount ++;
    		} else {
    			wrongCount ++;
    		}
    	}
    	/*
    	 * Toast t = Toast.makeText(getActivity(), "Correct Count = " + correctCount +
		 *		" / Wrong Count = " + wrongCount, Toast.LENGTH_SHORT);
		 * t.show();
		 */
		loadQuestion();
    }
}

/*
 *	http://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
 *	The above has a good example of a stop watch timer 
 *
 *	http://developer.android.com/reference/android/os/CountDownTimer.html
 *	The above has good explanation of CountDown Timer
 */