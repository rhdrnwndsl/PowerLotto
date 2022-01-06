package com.jiw.powerlotto;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;

public class DetailModel {

    String seq;
    String result;
    ArrayList<Integer> numberList = new ArrayList<>();

    public DetailModel() { }

    public static DetailModel build(Element value)
    {
        DetailModel detailModel = new DetailModel();
        detailModel.seq = value.select("th").text();
        detailModel.result = "";
        if (value.select("td[class=result]").hasText())
        {
            detailModel.result = value.select("td[class=result]").text();
        }

        Elements numbers = value.select("span");
        for (Element number : numbers)
        {
            detailModel.numberList.add(Integer.valueOf(number.text()));
        }

        Collections.sort(detailModel.numberList);
        return detailModel;
    }

    @Override
    public String toString()
    {
        return "DetailModel{" + "seq='" + seq + '\'' + ", result=" + result +
                ", numberList=" + numberList.toString() + '}';
    }

    public String getSeq()
    {
        return seq;
    }

    public String getResult()
    {
        return result;
    }

    public ArrayList getNumberList()
    {
        return numberList;
    }
}
