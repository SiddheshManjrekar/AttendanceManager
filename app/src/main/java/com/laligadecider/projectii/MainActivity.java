package com.laligadecider.projectii;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static List<MyLayout> subjectList = new ArrayList<>();
    Gson gson;
    String json;
    public static String fileName="MySharedString";
    int change;

    public static SharedPreferences appSharedPrefs,someData;
    RecyclerView recyclerView;
    MyLayoutAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    //int mOrientation= LinearLayoutManager.VERTICAL;
    //int mOrientation= LinearLayoutManager.HORIZONTAL;
    //int mOrientation=StaggeredGridLayoutManager.HORIZONTAL;
    int mOrientation= GridLayoutManager.VERTICAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        someData=getSharedPreferences(fileName,0);
        change=someData.getInt("sharedInt", 0); // a string that loads stored value and default value;

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Type type = new TypeToken<List<MyLayout>>() {}.getType();
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        subjectList = gson.fromJson(json, type);
        mAdapter=new MyLayoutAdapter();


        Log.e("Tag", "" + change);
        if (change==0){
            subjectList = new ArrayList<>();
        }
        if (change>0) {
            int space= subjectList.size();
            Log.e("Size",""+space);
            change = space;
        }
        mAdapter.setCounter(change);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MyLayoutAdapter(subjectList, mOrientation);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext(),mOrientation,false);
        //mLayoutManager = new StaggeredGridLayoutManager(5,mOrientation);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),3,mOrientation,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_search:

                FragmentManager fm = getFragmentManager();
                PopupFragment dialogFragment = new PopupFragment ();
                dialogFragment.show(fm, "Sample Fragment");


                //Toast.makeText(getApplicationContext(),"Already out",Toast.LENGTH_SHORT).show();



                /*
                MoviesAdapter m=new MoviesAdapter(mOrientation);
                int change=m.getCounter();
                ++change;
                m.setCounter(change);
                recyclerView.setAdapter(mAdapter);
                */

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doThis(){
        PopupFragment pf=new PopupFragment();
        int validity=pf.getValidity();
        if (validity==1){
            String sub=pf.getSubjectName();
            int onTV=pf.getonTV();
            MyLayout movie = new MyLayout(sub, onTV);
            subjectList.add(movie);
            mAdapter.notifyDataSetChanged();
            int change=mAdapter.getCounter();
            ++change;

            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            gson = new Gson();
            json = gson.toJson(subjectList);
            prefsEditor.putString("MyObject", json);
            prefsEditor.commit();

            mAdapter.setCounter(change);
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void doThat(){
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        gson = new Gson();
        json = gson.toJson(mAdapter.getList());
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();

        //count

        Log.e("Tag", "" + mAdapter.getCounter());
        someData=getSharedPreferences("MySharedString", 0);
        SharedPreferences.Editor editor= someData.edit();
        editor.putInt("sharedInt", mAdapter.getCounter()); // add data to key
        editor.commit();
    }
}

