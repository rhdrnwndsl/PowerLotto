package com.jiw.powerlotto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListPreviewAdapter extends BaseAdapter {

    private ArrayList<ListPreviewReader> m_list_preview_reader = new ArrayList<ListPreviewReader>() ;

    public ListPreviewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return m_list_preview_reader.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_preview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView _preview_drwNo = (TextView) convertView.findViewById(R.id.txt_preview_drwNo) ;
        TextView _preview_no1 = (TextView) convertView.findViewById(R.id.txt_preview_no1) ;
        TextView _preview_no2 = (TextView) convertView.findViewById(R.id.txt_preview_no2) ;
        TextView _preview_no3 = (TextView) convertView.findViewById(R.id.txt_preview_no3) ;
        TextView _preview_no4 = (TextView) convertView.findViewById(R.id.txt_preview_no4) ;
        TextView _preview_no5 = (TextView) convertView.findViewById(R.id.txt_preview_no5) ;
        TextView _preview_no6 = (TextView) convertView.findViewById(R.id.txt_preview_no6) ;
        TextView _preview_bonus = (TextView) convertView.findViewById(R.id.txt_preview_bonus) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListPreviewReader _list_preview_reader = m_list_preview_reader.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        _preview_drwNo.setText(_list_preview_reader.GetDrwNo());
        _preview_no1.setText(_list_preview_reader.GetNo1());
        _preview_no2.setText(_list_preview_reader.GetNo2());
        _preview_no3.setText(_list_preview_reader.GetNo3());
        _preview_no4.setText(_list_preview_reader.GetNo4());
        _preview_no5.setText(_list_preview_reader.GetNo5());
        _preview_no6.setText(_list_preview_reader.GetNo6());
        _preview_bonus.setText(_list_preview_reader.GetBonus());

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
        return m_list_preview_reader.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String _drwno, String _no1, String _no2, String _no3,
                        String _no4, String _no5, String _no6, String _bonus) {
        ListPreviewReader item = new ListPreviewReader();
        item.ListPreviewReader(_drwno, _no1, _no2, _no3, _no4,_no5,_no6,_bonus);

        m_list_preview_reader.add(item);
    }
}
