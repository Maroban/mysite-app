package com.javaex.mysite;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.javaex.vo.GuestbookVo;

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

        /*** 서버로부터 데이터 가져오기 => 가상 ***/
        List<GuestbookVo> gList = getListFromServer();

        /*** 자바 객체화 ***/
        ListView listView_guestbook = (ListView) findViewById(R.id.listView_guestbook);

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
}