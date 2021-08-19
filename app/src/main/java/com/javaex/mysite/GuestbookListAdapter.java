package com.javaex.mysite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.javaex.vo.GuestbookVo;

import java.util.List;

public class GuestbookListAdapter extends ArrayAdapter<GuestbookVo> {

    private TextView txt_no;
    private TextView txt_name;
    private TextView txt_reg_date;
    private TextView txt_content;


    // 생성자
    public GuestbookListAdapter(Context context, int resource, List<GuestbookVo> items) {
        super(context, resource, items);
    }

    // getView:  ListView에서 하나의 row로 사용될 실제 view를 리턴한다.
    // 처음 화면에 나오는 리스트와 여유분 1~2개까지만 만들고, 나머지 리스트는 앞에 만들었던 것을 재사용한다.
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // 만들어 놓은 View 없을 때 만들어야 한다.(화면에 출력되는 리스트와 여유분 1~2개까지만 만든다.)
        if (view == null) {
            // 부풀릴 수 있는 기계를 가져온다.
            LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(getContext());

            // view 객체로 만들어 부풀린다.
            view = layoutInflater.inflate(R.layout.activity_list_item, null);

        }

        // 데이터 객체화
        txt_no = view.findViewById(R.id.txt_no);
        txt_name = view.findViewById(R.id.txt_name);
        txt_reg_date = view.findViewById(R.id.txt_reg_date);
        txt_content = view.findViewById(R.id.txt_content);

        // 데이터 가져오기(1개의 데이터)
        // getItem: 특정 position에 위치한 data들을 얻어온다.
        GuestbookVo guestbookVo = super.getItem(position);

        txt_no.setText("" + guestbookVo.getNo());
        txt_name.setText(guestbookVo.getName());
        txt_reg_date.setText(guestbookVo.getReg_date());
        txt_content.setText(guestbookVo.getContent());


        return view;
    }
}
