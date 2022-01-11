package com.jiw.powerlotto;

public class ListSortReader {

    private String m_number;
    private  int m_count;

    public void ListSortReader(String _number, int _count){
        m_number = _number;
        m_count = _count;
    }

    public String GetNumber(){
        return m_number;
    }

    public int GetCount(){
        return m_count;
    }
}
