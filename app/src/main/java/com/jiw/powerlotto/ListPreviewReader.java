package com.jiw.powerlotto;

public class ListPreviewReader {

    private String m_drwNo;
    private String m_no1;
    private String m_no2;
    private String m_no3;
    private String m_no4;
    private String m_no5;
    private String m_no6;
    private String m_bonus;

    public void ListPreviewReader(String _drwno, String _no1, String _no2, String _no3,
                                  String _no4, String _no5, String _no6, String _bonus){
        m_drwNo = _drwno;
        m_no1 = _no1;
        m_no2 = _no2;
        m_no3 = _no3;
        m_no4 = _no4;
        m_no5 = _no5;
        m_no6 = _no6;
        m_bonus = _bonus;
    }

    public String GetDrwNo(){
        return m_drwNo;
    }

    public String GetNo1(){
        return m_no1;
    }

    public String GetNo2(){
        return m_no2;
    }
    public String GetNo3(){
        return m_no3;
    }
    public String GetNo4(){
        return m_no4;
    }
    public String GetNo5(){
        return m_no5;
    }
    public String GetNo6(){
        return m_no6;
    }
    public String GetBonus(){
        return m_bonus;
    }

}
