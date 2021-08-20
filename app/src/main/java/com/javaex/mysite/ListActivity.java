package com.javaex.mysite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javaex.vo.GuestbookVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView_guestbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*** 툴바 영역 ***/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*** 자바 객체화 ***/
        ListView listView_guestbook = (ListView) findViewById(R.id.listView_guestbook);

        /*** 스레드 실행(ListAsyncTask 실행) ***/
        ListAsyncTask listAsyncTask = new ListAsyncTask();
        listAsyncTask.execute();

        /*** 서버로부터 데이터 가져오기 => 가상 ***/
        List<GuestbookVo> gList = getListFromServer();

        /*** Adapter 생성 ***/
        GuestbookListAdapter guestbookListAdapter = new GuestbookListAdapter(getApplicationContext(), R.id.listView_guestbook, gList);

        /*** listView 안에 Adapter 세팅 ***/
        listView_guestbook.setAdapter(guestbookListAdapter);

        /*** 클릭 이벤트 ***/
        listView_guestbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 현재 클릭한 View의 index
                Log.d("Study", "index= " + i);

                // 현재 클릭한 View의 txt_no
                TextView txt_no = (TextView) view.findViewById(R.id.txt_no);
                Log.d("Study", "No=" + txt_no.getText().toString());

                // 화면에 출력되지 않은 데이터 즉, 리스트의 값을 가져온다.
                GuestbookVo guestbookVo = (GuestbookVo) adapterView.getItemAtPosition(i);
                Log.d("Study", "Vo=" + guestbookVo.toString());
                Log.d("Study", "Vo.no=" + guestbookVo.getNo());

            }
        });
    }

    /*** 방명록 데이터(가상) ***/
    public List<GuestbookVo> getListFromServer() {
        List<GuestbookVo> gList = new ArrayList<GuestbookVo>();
        for (int i = 1; i <= 50; i++) {
            GuestbookVo guestbookVo = new GuestbookVo();

            guestbookVo.setNo(i);
            guestbookVo.setName("정우성");
            guestbookVo.setPassword("1234");
            guestbookVo.setContent("테스트 글입니다.");
            guestbookVo.setReg_date("2021-08-19 -" + i);

            gList.add(guestbookVo);
        }
        return gList;
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
    public class ListAsyncTask extends AsyncTask<Void, Integer, List<GuestbookVo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<GuestbookVo> doInBackground(Void... voids) {

            List<GuestbookVo> gList = null;

            // 서버에 연결 후 요청을 한다.
            try {
                //url 생성
                URL url = new URL("http://58.234.223.146:8088/mysite5/api/guestbook/list");

                //url 연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // 10초 동안 기다린 후 응답이 없으면 종료
                conn.setConnectTimeout(10000);

                // 요청방식 POST
                conn.setRequestMethod("POST");

                //요청시 데이터 형식 json
                conn.setRequestProperty("Content-Type", "application/json");

                //응답시 데이터 형식 json
                conn.setRequestProperty("Accept", "application/json");

                //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                conn.setDoOutput(true);

                //InputStream으로 서버로 부터 응답을 받겠다는 옵션.
                conn.setDoInput(true);

                // 응답코드 200이 정상
                int resCode = conn.getResponseCode();

                Log.d("Study", "" + resCode);

                if (resCode == 200) {
                    // Stream을 통해 통신하며, 데이터 형식은 json으로 받는다.
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);

                    String jsonData = "";

                    while (true) {
                        String line = br.readLine();

                        if (line == null) {
                            break;
                        }
                        jsonData = jsonData + line;
                    }

                    Gson gson = new Gson();
                    gList = gson.fromJson(jsonData, new TypeToken<List<GuestbookVo>>() {
                    }.getType());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return gList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<GuestbookVo> gList) {

            Log.d("Study", "" + gList.size());

            super.onPostExecute(gList);
        }
    }
}