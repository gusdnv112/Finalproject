package com.dalpeng.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TimerTask mtask;
    private Timer mtimer;
    private final Handler handler = new Handler();
    private TextView timerview;
    private TextView wordview;
    int timer_sec;
    int count;
    int n,m,l;
    CustomTask ct = new CustomTask();
    CustomTask2 ct2 = new CustomTask2();
    ArrayList ar = new ArrayList();
    ArrayList ar2 = new ArrayList();
    ArrayList<Integer> ar3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStop = (Button) this.findViewById(R.id.btnStop);
        Button btnStart = (Button) this.findViewById(R.id.btnstart);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testStart();
                text();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String ss="";
        wordview = (TextView) this.findViewById(R.id.wordview);
        TextView countdown = (TextView) this.findViewById(R.id.countdown);
        final Button btn1 = (Button) this.findViewById(R.id.button1);
        final Button btn2 = (Button) this.findViewById(R.id.button2);
        final Button btn3 = (Button) this.findViewById(R.id.button3);
        final Button btn4 = (Button) this.findViewById(R.id.button4);

        try {
            ar = ct.execute().get();
            ar2 = ct2.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "onStart: " + ar.get(0).toString());
            Log.e("errrr", "onStart: " + e.toString());
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn1.getText().toString().equals(ar2.get(n))){
                    mtimer.cancel();
                    text();
                    testStart();
                }else{
                    alert();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn2.getText().toString().equals(ar2.get(n))){
                    mtimer.cancel();
                    text();
                    testStart();
                }else{
                    alert();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn3.getText().toString().equals(ar2.get(n))){
                    mtimer.cancel();
                    text();
                    testStart();

                }else{
                    alert();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn4.getText().toString().equals(ar2.get(n))){;
                    mtimer.cancel();
                    text();
                    testStart();
                }else{
                    alert();

                }
            }
        });

        wordview.setText("안녕하세요~~");


    }

    public void testStart() {
        timerview = (TextView) findViewById(R.id.countdown);
        timer_sec = 16;
        count = 0;
        mtask = new TimerTask(){
            @Override
            public void run() {

                Update();
            }
        };
        mtimer = new Timer();
        mtimer.schedule(mtask, 0, 1100);
    }
    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                timer_sec--;
                timerview.setText(timer_sec + "초");
                if(timer_sec==0){
                    stop();
                }
            }
        };
        handler.post(updater);
    }
    protected void stop(){
        AlertDialog.Builder alertbuilder =  new AlertDialog.Builder(this);
        alertbuilder.setTitle("시간 초과하였습니다");
        alertbuilder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertbuilder.show();
        mtimer.cancel();


        wordview.setText("다시 도전하세요~");
    }


    public void text() {

        ar3 = new ArrayList<Integer>();
        Log.e("errrr", "onStart: " + ar.size());
        Button btn1 = (Button) this.findViewById(R.id.button1);
        Button btn2 = (Button) this.findViewById(R.id.button2);
        Button btn3 = (Button) this.findViewById(R.id.button3);
        Button btn4 = (Button) this.findViewById(R.id.button4);
        n = (int) (Math.random() * ar2.size()) +1;
        Random random = new Random();
        m = random.nextInt(20) + 10;
        ar3.add(m+m);
        ar3.add((int)Math.pow(m,2));
        ar3.add(m+2);
        ar3.add(n);
        Log.e("MATH", "text: "+n );
        TextView wordview = (TextView) this.findViewById(R.id.wordview);

        wordview.setText(ar.get(n).toString());
        Collections.shuffle(ar3);

        if(n < 10){
            btn1.setText(ar2.get(1).toString());
            btn2.setText(ar2.get(4).toString());
            btn3.setText(ar2.get(7).toString());
            btn4.setText(ar2.get(10).toString());
        }else if(n > ar2.size() - 10){
            btn1.setText(ar2.get(ar.size() - 3).toString());
            btn2.setText(ar2.get(ar.size() - 10).toString());
            btn3.setText(ar2.get(ar.size() - 7).toString());
            btn4.setText(ar2.get(ar.size() - 4).toString());
        }else{
            btn1.setText(ar2.get(ar3.get(0)).toString());
            btn2.setText(ar2.get(ar3.get(1)).toString());
            btn3.setText(ar2.get(ar3.get(2)).toString());
            btn4.setText(ar2.get(ar3.get(3)).toString());
        }
        Log.e("errrr", "onStart: " + ar.size());

    }
    public void alert(){
        AlertDialog.Builder alertbuilder =  new AlertDialog.Builder(this);
        alertbuilder.setTitle("틀렸습니다");
        alertbuilder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertbuilder.show();
        mtimer.cancel();

    }


}

