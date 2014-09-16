package com.example.geoquiznew;


public class TrueFalse {
	
	private int mQuestion;
	private boolean mIsTrue;
	
	public TrueFalse(int question, boolean isTrue){
		
		mQuestion = question;
		mIsTrue = isTrue;

		
	}

	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int question) {
		mQuestion = question;
	}

	public boolean isIsTrue() {
		return mIsTrue;
	}

	public void setIsTrue(boolean isTrue) {
		mIsTrue = isTrue;
	}
	
	

}
