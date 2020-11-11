package com.example.zulfikaranshari.rentku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class faq extends AppCompatActivity {

    RecyclerView myRecyclerview;
    MyAdapter adapter;
    List<MyDataSetGet> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        myRecyclerview = (RecyclerView) findViewById(R.id.myRecycler);

        myRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
        myRecyclerview.setLayoutManager(LM);
        myRecyclerview.setItemAnimator(new DefaultItemAnimator());
        myRecyclerview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        listData = new ArrayList<>();

        adapter = new MyAdapter(listData);

        FDB = FirebaseDatabase.getInstance();
        GetDataFirebase();

    }

    void GetDataFirebase(){

        DBR = FDB.getReference("faq");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MyDataSetGet data = dataSnapshot.getValue(MyDataSetGet.class);
                //add to arrayList
                listData.add(data);
                //add list into adapter/RecyclerView
                myRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        List<MyDataSetGet> listArray;

        public MyAdapter(List<MyDataSetGet> List){
            this.listArray = List;
        }


        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_faq, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            MyDataSetGet data = listArray.get(position);

            holder.MyText.setText(data.getQues());
            holder.MyText2.setText(data.getAns());

        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView MyText, MyText2;

            public MyViewHolder(View itemView) {
                super(itemView);

                MyText = (TextView)itemView.findViewById(R.id.textview);
                MyText2 = (TextView)itemView.findViewById(R.id.jawaban);
            }
        }

        @Override
        public int getItemCount() {
            return listArray.size();
        }
    }
}



