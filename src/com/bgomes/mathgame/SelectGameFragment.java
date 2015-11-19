package com.bgomes.mathgame;

import com.bgomes.mathgame.R;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class SelectGameFragment extends Fragment 
			implements OnClickListener, OnEditorActionListener {

	private EditText userName;
	private TextView userNameLabel;
	private Button add;
	private Button subtract;
	private Button multiply;
	private Button divide;
	
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
        
        View view = inflater.inflate(R.layout.activity_select_game_fragment,
                                     container, false);

        // get references to the widgets
        userName = (EditText) view.findViewById(R.id.userNameEditText);
        userNameLabel = (TextView) view.findViewById(R.id.userNameLabelTextView);
        add = (Button) view.findViewById(R.id.addButton);
        subtract = (Button) view.findViewById(R.id.subtractButton);
        multiply = (Button) view.findViewById(R.id.multiplyButton);
        divide = (Button) view.findViewById(R.id.divideButton);
        
        // set the listeners
        userName.setOnEditorActionListener(this);
        add.setOnClickListener(this);
        subtract.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);

        //  savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        return view;
	}

	@Override
    public void onPause() {
        // save the instance variables       
        Editor editor = savedValues.edit();        
        editor.putString("userName", userName.getText().toString());
        editor.commit();        

        super.onPause();      
    }
    
    @Override
    public void onResume() {
        super.onResume();
        userName.setText(savedValues.getString("userName", "Guest"));
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.addButton:
				onPause();
				getFragmentManager().beginTransaction().replace(android.R.id.content,  new AddQuestionFragment()).commit();
				break;
			case R.id.subtractButton:
				//getFragmentManager().beginTransaction().replace(android.R.id.content,  new SelectGameFragment()).commit();
				Toast u = Toast.makeText(getActivity(), "You Selected A Subtraction Game", Toast.LENGTH_SHORT);
				u.show();
				break;
			case R.id.multiplyButton:
				// Can't use Toast v as it is already a variable passed via parameters
				Toast w = Toast.makeText(getActivity(), "You Selected A Multiplication Game", Toast.LENGTH_SHORT);
				w.show();
				break;
			case R.id.divideButton:
				Toast x = Toast.makeText(getActivity(), "You Selected A Division Game", Toast.LENGTH_SHORT);
				x.show();
				break;
		}
		
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE ||
			actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
			onPause();
		}
		return false;
	}
}
