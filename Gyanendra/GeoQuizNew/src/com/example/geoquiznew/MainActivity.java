package com.example.geoquiznew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	Button mTrueButton;
	Button mFalseButton;
	TextView mQuestionTextView;
	Button mNextButton;
	Button mCheatButton;
	
	private boolean isMCheater;
	
	public static final String TAG = "GeoQuizNew";
	public static final String INDEX = "Index";
	public static final String EXTRA_MESSAGE_CHEAT = "com.example.geoquiz.cheat";
	
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{new TrueFalse(R.string.question_area, false),new TrueFalse(R.string.question_capital, false),
			new TrueFalse(R.string.question_city, true),new TrueFalse(R.string.question_population, true),new TrueFalse(R.string.question_river, true)};
	
	
	int mQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        mTrueButton = (Button)findViewById(R.id.trueButton);
        
        mFalseButton = (Button)findViewById(R.id.falseButton);
        
        mNextButton = (Button)findViewById(R.id.nextButton);
        
        mQuestionTextView = (TextView)findViewById(R.id.questionTextView);
        
        mCheatButton = (Button) findViewById(R.id.cheatButton);    
        
        
        
        if (savedInstanceState != null) {
        	
        	mQuestionIndex = savedInstanceState.getInt(INDEX,0);
			
		}
        
        updateQuestion();
        
        Log.d(TAG, "On create");
        
        
        
        mTrueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				answerToDisplay(true);			
				
				
			}
		});
        
        mFalseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				answerToDisplay(false);
				
			}
		});
        
        mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	mQuestionIndex = (mQuestionIndex + 1) % mQuestionBank.length;
				updateQuestion();
				
			}
		});
        
        
        mCheatButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent I = new Intent(MainActivity.this,CheatActivity.class);
				
				Boolean correctAnswer = mQuestionBank[mQuestionIndex].isIsTrue();
				I.putExtra(EXTRA_MESSAGE_CHEAT, correctAnswer);
				startActivityForResult(I, 0);
				
			}
		});
        
        
        

       
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	Log.d(TAG, "On Start");
    }
    
    
    @Override
    public void onPause(){
    	super.onPause();
    	Log.d(TAG, "On pause ");
    }

    
    @Override
    public void onResume(){
    	super.onResume();
    	Log.d(TAG, "On resume");
    }
    
    
    @Override
    public void onStop(){
    	super.onStop();
    	Log.d(TAG, "On stop");
    }
    
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	Log.d(TAG, "On destroy");
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
    	super.onSaveInstanceState(savedInstanceState);
    	Log.d(TAG, "Inside Onsave Bundle");
    	savedInstanceState.putInt(INDEX, mQuestionIndex);
    	
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
    	
    	isMCheater = data.getBooleanExtra(CheatActivity.EXTRA_MESSAGE_ANSWER_SHOWN, false);
    	
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void updateQuestion(){
    	
    	//Log.d(TAG, "Inside Update Question", new Exception());
    	
    	isMCheater = false;
    	int mQuestionToDisplay = mQuestionBank[mQuestionIndex].getQuestion();
    	mQuestionTextView.setText(mQuestionToDisplay);
    }
    
    
    public void answerToDisplay(boolean isAnswerTrue){
    	
    	boolean questionBankanswer = mQuestionBank[mQuestionIndex].isIsTrue();
    	
    	if (isMCheater == true) {
    		
    		Toast toast = Toast.makeText(MainActivity.this, R.string.judgement_toast,Toast.LENGTH_SHORT);
    		toast.show();
			
		} else {
			
			if (isAnswerTrue == questionBankanswer) {
	    		
	    		Toast toast = Toast.makeText(MainActivity.this, R.string.correct_toast,Toast.LENGTH_SHORT);
	    		toast.show();
				
			} else {
				
				Toast toast = Toast.makeText(MainActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT);
				toast.show();

			}

		}
    	
    	
    	
    
    }



}
