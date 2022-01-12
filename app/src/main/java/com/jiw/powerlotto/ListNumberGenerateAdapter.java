package com.jiw.powerlotto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListNumberGenerateAdapter  extends BaseAdapter {
    public ArrayList<ListNumberGenerateReader> m_list_number_reader = new ArrayList<ListNumberGenerateReader>() ;

    public ListNumberGenerateAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return m_list_number_reader.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_number_generate, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView _num_number1 = (TextView) convertView.findViewById(R.id.txt_num_num1) ;
        TextView _num_number2 = (TextView) convertView.findViewById(R.id.txt_num_num2) ;
        TextView _num_number3 = (TextView) convertView.findViewById(R.id.txt_num_num3) ;
        TextView _num_number4 = (TextView) convertView.findViewById(R.id.txt_num_num4) ;
        TextView _num_number5 = (TextView) convertView.findViewById(R.id.txt_num_num5) ;
        TextView _num_number6 = (TextView) convertView.findViewById(R.id.txt_num_num6) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListNumberGenerateReader _list_num_reader = m_list_number_reader.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        _num_number1.setText(_list_num_reader.GetNumber1());
        _num_number1.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber1()));

        _num_number2.setText(_list_num_reader.GetNumber2());
        _num_number2.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber2()));

        _num_number3.setText(_list_num_reader.GetNumber3());
        _num_number3.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber3()));

        _num_number4.setText(_list_num_reader.GetNumber4());
        _num_number4.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber4()));

        _num_number5.setText(_list_num_reader.GetNumber5());
        _num_number5.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber5()));

        _num_number6.setText(_list_num_reader.GetNumber6());
        _num_number6.setBackgroundResource(getNumberBackgroundResource(_list_num_reader.GetNumber6()));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴.
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return m_list_number_reader.get(position) ;
    }

    public int getNumberBackgroundResource(String _number) {

        int number = Integer.parseInt(_number);
        if (number < 11)
        {
            return R.drawable.yellow_circle;
        }
        else if (number < 21)
        {
            return R.drawable.blue_circle;
        }
        else if (number < 31)
        {
            return R.drawable.red_circle;
        }
        else if (number < 41)
        {
            return R.drawable.gray_circle;
        }
        return R.drawable.green_circle;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String _no1, String _no2, String _no3, String _no4, String _no5, String _no6) {
        ListNumberGenerateReader item = new ListNumberGenerateReader();
        item.ListNumberGenerateReader(_no1, _no2,_no3,_no4,_no5,_no6);

        m_list_number_reader.add(item);
    }
}
