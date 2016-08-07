package com.laligadecider.projectii;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MyLayoutAdapter extends RecyclerView.Adapter<MyLayoutAdapter.MyViewHolder> {

    public static List<MyLayout> subjectList;
    int check;
    public static int itemCounter=0;
    public int orientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    View v1;
    public static SharedPreferences totalItems,myList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subName;
        TextView count;

        //Initialize elements
        public MyViewHolder(View view) {
            super(view);
            count=(TextView) v1.findViewById(R.id.count);
            subName=(TextView) v1.findViewById(R.id.subname);
        }
    }

    //Constructor

    public MyLayoutAdapter(){

    }

    public MyLayoutAdapter(List<MyLayout> moviesList, int orientation) {

        this.subjectList=moviesList;
        this.orientation=orientation;
    }

    //Provide interface for the row
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (orientation==VERTICAL_LIST) {
            v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.pofile_list, parent, false);
        }

        return new MyViewHolder(v1);
    }

    //The correct data
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MyLayout mySubject = subjectList.get(position);
        holder.subName.setText(mySubject.getSub());
        holder.count.setText(""+mySubject.getAttendance());

        Button add=(Button) v1.findViewById(R.id.add);
        Button sub=(Button) v1.findViewById(R.id.sub);
        Button remove=(Button) v1.findViewById(R.id.remove);
        final TextView count=(TextView) v1.findViewById(R.id.count);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = Integer.parseInt(count.getText().toString());
                check += 1;
                count.setText("" + check);
                Log.e("TAG", "" + position);

                //Delete in case

                MyLayout movie1 = subjectList.get(position);
                MyLayout movie = new MyLayout(movie1.getSub(), check);
                subjectList.set(position,movie);

                //Delete in case


            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = Integer.parseInt(count.getText().toString());
                check -= 1;
                count.setText("" + check);

                MyLayout movie1 = subjectList.get(position);
                MyLayout movie = new MyLayout(movie1.getSub(), check);
                subjectList.set(position, movie);

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "" + position);
                subjectList.remove(holder.getAdapterPosition());

                --itemCounter;
                setCounter(itemCounter);
                notifyItemRemoved(holder.getAdapterPosition());
                Log.e("TAG", "" + subjectList.size());
                notifyItemRangeChanged(holder.getAdapterPosition(), subjectList.size());
                //notifyItemRangeRemoved(position, moviesList.size()-1);

            }
        });

    }



    @Override
    public int getItemCount() {
        return itemCounter;
    }

    public int getCounter(){
        return itemCounter;
    }
    public void setCounter(int c){
        itemCounter=c;
    }

    public List<MyLayout> getList(){
        return subjectList;
    }
}

