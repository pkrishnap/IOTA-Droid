package com.example.geoquiznew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	public static final String EXTRA_MESSAGE_ANSWER_SHOWN ="com.example.is_answer_shown";
	
	boolean mRightAnswer;
	
	TextView mCorrectAnswer;
	Button mShowAnswerButton;
	
	private void setAnswerResultShown(boolean isAnswerShown){
		Intent data = new Intent();
		data.putExtra(EXTRA_MESSAGE_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mRightAnswer = getIntent().getBooleanExtra(MainActivity.EXTRA_MESSAGE_CHEAT, false);
		
		mCorrectAnswer = (TextView)findViewById(R.id.correctAnswer);
		mShowAnswerButton = (Button)findViewById(R.id.showAnswerButton);
		
		setAnswerResultShown(false);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (mRightAnswer == true) {
					
					mCorrectAnswer.setText(R.string.true_text);
					
				} else {
					mCorrectAnswer.setText(R.string.false_text);

				}
				
				setAnswerResultShown(true);
				
			}
		});
		
		
	}
	
	
	
	

}
