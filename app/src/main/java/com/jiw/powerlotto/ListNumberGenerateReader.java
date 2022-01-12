package com.jiw.powerlotto;

public class ListNumberGenerateReader {

    private String m_no1,m_no2,m_no3,m_no4,m_no5,m_no6;

    public void ListNumberGenerateReader(String _no1, String _no2, String _no3, String _no4, String _no5, String _no6){
        m_no1 = _no1;m_no2 = _no2;m_no3 = _no3;m_no4 = _no4;m_no5 = _no5;m_no6 = _no6;
    }

    public String GetNumber1(){
        return m_no1;
    }
    public String GetNumber2(){
        return m_no2;
    }
    public String GetNumber3(){
        return m_no3;
    }
    public String GetNumber4(){
        return m_no4;
    }
    public String GetNumber5(){
        return m_no5;
    }
    public String GetNumber6(){
        return m_no6;
    }

}
