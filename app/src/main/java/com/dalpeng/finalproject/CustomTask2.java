package com.dalpeng.finalproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Park on 2017-04-15.
 */

public class CustomTask2 extends AsyncTask<String, Void, ArrayList> {
    String outid= "";
    String outname = "";
    String outprice = "";
    ArrayList ar;
    @Override
    protected ArrayList doInBackground(String... strs) {

        return getJsonText();
    }

    public ArrayList getJsonText() {
        StringBuffer sb = new StringBuffer();
        try {
            //주어진 URL 문서의 내용을 문자열로 얻는다.
            String jsonPage = getStringFromUrl("http://107.178.216.194:8080/final/word.jsp");
            Log.e("page", "getJsonText: " + jsonPage);

            JSONObject json = new JSONObject(jsonPage);
            Log.d("1234", jsonPage);

            JSONArray jArr = json.getJSONArray("list");

            ar = new ArrayList();
            for (int i = 0; i < jArr.length(); i++) {
                //i번째 배열 할당
                json = jArr.getJSONObject(i);
                String pw = json.getString("mean");
                ar.add(pw);
                //StringBuffer 출력할 값을 저장
                sb.append(pw + "\n");
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tttttttttttttttttt", "getJsonText: " + e.toString());
            // TODO: handle exception
        }
        return ar;
    }

    public String getStringFromUrl(String pageurl) {
        HttpURLConnection urlConnection = null;
        Log.e("tttttttttttttttttt", "11hello world");

        StringBuffer page = new StringBuffer();

        try {

            URL url = new URL(pageurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


            new DataOutputStream(urlConnection.getOutputStream()); // 보내고

            Scanner scan = new Scanner(urlConnection.getInputStream()); //받고

            while (scan.hasNext()) {
                String str = scan.nextLine();
                page.append(str);
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("tttt", "getStringFromUrl: " + e.toString());
        } catch (Exception e) {
            Log.e("tttt222", "getStringFromUrl: " + e.toString());
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tttt", "getStringFromUrl: " + e.toString());
            }
            Log.e("tttt3333", "getStringFromUrl: www");

        }
        Log.e("parkssssss", "getStringFromUrl: " + page.toString());
        return page.toString();
    }

}



