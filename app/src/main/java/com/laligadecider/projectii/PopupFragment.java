package com.laligadecider.projectii;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupFragment extends DialogFragment {

    EditText subject_name,total_hours,pass_per;
    Button setReminder,cancel;
    public static String subject,hours,pass;
    public static int perc,hrs;
    int sch,tch,pch;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    public static int validity=0;
    public static int onTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_dialog, null);

        getDialog().setTitle("Add Subject");

        //Initialize
        subject_name=(EditText) rootView.findViewById(R.id.subject_name);
        total_hours=(EditText) rootView.findViewById(R.id.total_hours);
        pass_per=(EditText) rootView.findViewById(R.id.pass_per);
        setReminder=(Button) rootView.findViewById(R.id.setReminder);
        cancel=(Button) rootView.findViewById(R.id.cancel);


        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GetData

                validity=0;
                subject = subject_name.getText().toString();
                hours = total_hours.getText().toString();
                pass = pass_per.getText().toString();


                //validation

                if(subject.length()<1){
                    subject_name.setError("Subject Name cannot be empty");
                    sch=0;
                }else {
                    subject_name.setError(null);
                    sch = 1;
                }


                if(total_hours.length()<1) {
                    total_hours.setError("Total Hours cannot be empty");
                    tch = 0;
                }
                else if (!hours.matches("[0-9]+")) {
                    total_hours.setError("Invalid Total Hours");
                    tch = 0;
                }
                else {
                    hrs=Integer.parseInt(hours);
                    total_hours.setError(null);
                    tch = 1;
                }


                if (pass.length() == 0) {
                    pass_per.setError("Pass Precentage cannot be empty");
                    pch = 0;
                }
                else if (!pass.matches("[0-9]+")) {
                    pass_per.setError("Invalid Percentage");
                    pch = 0;
                }
                else{
                    perc=Integer.parseInt(pass);

                if(perc<0 || perc>100){

                    pass_per.setError("Percentage should be between 0-100");
                    pch = 0;
                }
                else {
                    pass_per.setError(null);
                    pch = 1;
                }
                }

                if (pch==1 && tch==1 && sch==1){

                    validity=1;
                    onTV= ((100-perc) * hrs)/100;

                    Log.e("Tag",""+onTV);
                    Log.e("Tag",""+perc);
                    Log.e("Tag",""+hrs);
                    MainActivity ma=(MainActivity)getActivity();
                    ma.doThis();
                    /*
                    MoviesAdapter m=new MoviesAdapter(subject,VERTICAL_LIST);
                    MainActivity ma=(MainActivity)getActivity();
                    int change=m.getCounter();
                    ++change;
                    m.setCounter(change);
                    ma.recyclerView.setAdapter(m);
                    */
                    dismiss();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return rootView;
    }

    public String getSubjectName(){
        return subject;
    }

    public int getonTV(){
        return onTV;
    }

    public int getValidity(){
        return validity;
    }



}



