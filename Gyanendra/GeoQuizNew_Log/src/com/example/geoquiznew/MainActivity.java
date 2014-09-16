package com.example.geoquiznew;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
        
        updateQuestion();
        
        
        
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
				mQuestionIndex = (mQuestionIndex + 1) % mQuestionBank.length;
				updateQuestion();
				
			}
		});
        
        
        

       
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
    	
    	int mQuestionToDisplay = mQuestionBank[mQuestionIndex].getQuestion();
    	mQuestionTextView.setText(mQuestionToDisplay);
    }
    
    
    public void answerToDisplay(boolean isAnswerTrue){
    	
    	boolean questionBankanswer = mQuestionBank[mQuestionIndex].isIsTrue();
    	
    	if (isAnswerTrue == questionBankanswer) {
    		
    		Toast toast = Toast.makeText(MainActivity.this, R.string.correct_toast,Toast.LENGTH_SHORT);
    		toast.show();
			
		} else {
			
			Toast toast = Toast.makeText(MainActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT);
			toast.show();

		}
    	
    
    }



}
