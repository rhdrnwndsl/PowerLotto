package com.jiw.powerlotto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListSortAdapter  extends BaseAdapter {
    private ArrayList<ListSortReader> m_list_sort_reader = new ArrayList<ListSortReader>() ;

    public ListSortAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return m_list_sort_reader.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_sort, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView _sort_number = (TextView) convertView.findViewById(R.id.txt_sort_number) ;
        TextView _sort_count = (TextView) convertView.findViewById(R.id.txt_sort_count) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListSortReader _list_sort_reader = m_list_sort_reader.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        _sort_number.setText(_list_sort_reader.GetNumber());
        _sort_count.setText(String.valueOf(_list_sort_reader.GetCount()));

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
        return m_list_sort_reader.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String _number, int _count) {
        ListSortReader item = new ListSortReader();
        item.ListSortReader(_number, _count);

        m_list_sort_reader.add(item);
    }
}
