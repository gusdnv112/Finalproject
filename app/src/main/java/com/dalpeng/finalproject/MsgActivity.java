package com.dalpeng.finalproject;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Park on 2017-06-05.
 */

public class MsgActivity extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<Msg, ViewHolder> mFirebaseAdapter;
    Button btnSend;
//    RecyclerView recyclerView;
    EditText msgtext;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ListView listView;
    ArrayAdapter adapter;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

//        recyclerView = (RecyclerView) findViewById(R.id.msgLv);
        btnSend = (Button) findViewById(R.id.sendMsgBtn);
        msgtext = (EditText) findViewById(R.id.msgEt);
        listView = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Msg,ViewHolder>(
                Msg.class,
                R.layout.chat,
                ViewHolder.class,
                databaseReference.child("sample")) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Msg chatdata, int position) {
                viewHolder.userTv.setText(id);
                viewHolder.userTv.setTextColor(243445);
                viewHolder.msgTv.setText(chatdata.getMsg());
            }

        };
        databaseReference.child("sample").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Msg chatData = dataSnapshot.getValue(Msg.class);  // chatData를 가져오고
                String sd = chatData.getUser();
                if(sd.equals(id)) {
                    adapter.add("(You)" + chatData.getUser()+ ": " +chatData.getMsg()  );

                }else{
                    adapter.add(chatData.getUser() + " : " + chatData.getMsg());
                }
                listView.setSelection(adapter.getCount()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastvisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (lastvisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastvisiblePosition == (positionStart - 1))) {
                    listView.scrollTo(lastvisiblePosition,positionStart);
                      }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgtext.getText().toString();



                if (!TextUtils.isEmpty(msg)) {
                    Msg chatData = new Msg(id, msg);
                    databaseReference.child("sample").push().setValue(chatData);
                    msgtext.setText("");
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView msgTv, userTv;

        public ViewHolder(View v) {
            super(v);

            msgTv = (TextView) v.findViewById(R.id.msgTv);
            userTv = (TextView) v.findViewById(R.id.userTv);

        }
    }

}

