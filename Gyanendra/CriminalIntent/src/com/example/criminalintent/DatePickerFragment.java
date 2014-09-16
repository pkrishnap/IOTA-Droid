package com.example.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	
	public static final String EXTRA_DATE ="EXTRA_DATE";
	Date mDate;

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(mDate);
		int year = calender.get(Calendar.YEAR);
		int month = calender.get(Calendar.MONTH);
		int day = calender.get(Calendar.DATE);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		DatePicker mDialogDatePicker = (DatePicker)v.findViewById(R.id.dialogDatePicker);
		
		mDialogDatePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
								
			}
		});
		
		
		
		
		return new AlertDialog.Builder(getActivity()).setView(v).
					setTitle(R.string.crime_date_title).setPositiveButton(android.R.string.ok, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							sendResult(Activity.RESULT_OK);
							
						}
					}).create();
		
	}
	
	
	public static DatePickerFragment newInstance(Date d){
		
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, d);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		return fragment;
		
	}
	
	private void sendResult(int resultCode){
		
		if(getTargetFragment()==null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode , i);
		
		
	}

}
