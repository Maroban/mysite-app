package com.javaex.mysite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.javaex.vo.GuestbookVo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // 필드
    private Button btn_write;
    private EditText edit_name;
    private EditText edit_password;
    private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** 자바 객체로 변환 ***/
        btn_write = (Button) findViewById(R.id.btn_write);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_content = (EditText) findViewById(R.id.edit_content);

        /*** 툴바 영역 ***/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle("방명록 작성");

        /*** 저장 버튼을 클릭할 때 ***/
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Study", "저장");

                /*** 방명록 데이터를 Vo로 만들기 ***/
                String name = edit_name.getText().toString();
                String password = edit_password.getText().toString();
                String content = edit_content.getText().toString();

                GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
                Log.d("Study", guestbookVo.toString());

                /*** 리스트 액티비티로 전환 ***/
                startActivity(new Intent(MainActivity.this, ListActivity.class));

                /*** 스레드 실행(WriteAsyncTask class 실행) ***/
                WriteAsyncTask writeAsyncTask = new WriteAsyncTask();
                writeAsyncTask.execute(guestbookVo);

            }
        });
    }

    /*** 툴바 뒤로가기 버튼 ***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*** ListAsyncTask(이너 클래스) ***/
    public class WriteAsyncTask extends AsyncTask<GuestbookVo, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(GuestbookVo... guestbookVos) {

            // Vo --> json
            Gson gson = new Gson();
            String json = gson.toJson(guestbookVos[0]);
            Log.d("javaStudy", "json --> " + json);

            // 서버에 연결 후 요청을 한다.
            try {
                // url 생성
                URL url = new URL("http://58.234.223.208:8088/mysite5/api/guestbook/write2");

                // url 연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // 10초 동안 기다린 후 응답이 없으면 종료
                conn.setConnectTimeout(10000);

                // 요청방식 POST
                conn.setRequestMethod("POST");

                // 요청시 데이터 형식 json
                conn.setRequestProperty("Content-Type", "application/json");

                // 응답시 데이터 형식 json
                conn.setRequestProperty("Accept", "application/json");

                // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                conn.setDoOutput(true);

                // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
                conn.setDoInput(true);

                // 데이터를 Stream 통하여 전송
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                bw.write(json);
                bw.flush();

                // 응답코드 200이 정상
                int resCode = conn.getResponseCode();

                // 응답코드 200과 같은 의미
                if (resCode == HttpURLConnection.HTTP_OK) {
                    /*** 리스트 액티비티로 전환 ***/
                    startActivity(new Intent(MainActivity.this, ListActivity.class));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}