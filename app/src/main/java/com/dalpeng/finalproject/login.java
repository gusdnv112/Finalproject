package com.dalpeng.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Park on 2017-06-06.
 */

public class login extends AppCompatActivity {
    EditText txtid;

    private String id;
    public String getId() {
        return id;
    }

    CustomTask3 ct3 = new CustomTask3();
    ArrayList ar = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        final EditText etid = (EditText) findViewById(R.id.idtxt);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.e("etid", "onClick: "+etid.getText().toString() );
                    if(etid.getText().toString().length()  > 1){
                        id = etid.getText().toString();
                        Log.e("etid", "onClick: "+id );
                        Intent intent = new Intent(getApplicationContext(), MsgActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }else{
                        alert();
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });



    }
    protected void alert(){
        AlertDialog.Builder alertbuilder =  new AlertDialog.Builder(this);
        alertbuilder.setTitle("2글자 이상 입력해주세요");
        alertbuilder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertbuilder.show();
    }
}

