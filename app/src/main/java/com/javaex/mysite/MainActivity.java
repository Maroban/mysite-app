package com.javaex.mysite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.javaex.vo.GuestbookVo;

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
            }
        });
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