package com.jiw.powerlotto;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import lombok.Data;

/**
 * 로또 용지별 모델
 */
@Data
public class ListModel {
    long id;
    String url; //qr 코드 url
    int round; //회차 정보
    String date; //추첨 날짜
    String price; //당첨금액

    ArrayList<Integer> answerNumberList = new ArrayList<>(); //정답 번호 리스트
    ArrayList<DetailModel> detailModelArrayList = new ArrayList<>(); //구매 번호 세트

    private static String TAG = ListModel.class.getName();
    public static ListModel build(String finalUrl, String value) {

        // 스크랩핑 데이터 정의
        Document document = Jsoup.parse(value);
        ListModel listModel = new ListModel();
        listModel.url = finalUrl;

        // 회차 정보 초기화
        listModel.initParseRound(document);
        // 총 당첨금액 정보 초기화
        listModel.initParsePrice(document);
        // 추첨 날짜 정보 초기화
        listModel.initParseDate(document);
        // 당첨 번호 있는 경우 처리
        listModel.initParsePickNumber(document);
        // 구매 내역 번호 리스트 정의
        listModel.initParseNumberList(document);

        return listModel;

    }

    private void initParseNumberList(Document document)
    {
        Elements numbers = document.select("div[class=list_my_number]").select("tbody tr");
        for (Element number : numbers) {
            DetailModel detailModel = DetailModel.build(number);
            Log.e(TAG, "build: " + detailModel.toString() );
            detailModelArrayList.add(detailModel);
        }
    }

    private void initParsePickNumber(Document document)
    {
        Elements list = document.select("div[class=winner_number]").select("div[class=list]");
        if(list != null && list.size() > 0)
        {
            initPickNumber(list.get(0));
        }
    }

    private void initParseDate(Document document)
    {
        date = document.select("span[class=date]")
                .html()
                .replace("추첨", "")
                .trim();
    }

    private void initParsePrice(Document document)
    {
        if(document.select("span[class=key_clr1]").size() > 1)
        {
            price = document.select("span[class=key_clr1]").get(1).html();
        }
    }
    private void initParseRound(Document document)
    {
        round = Integer.parseInt(document.select("span[class=key_clr1]").get(0)
                .html()
                .replace("제", "")
                .replace("회", ""));
    }

    public void initPickNumber(Element list)
    {
        Elements numbers = list.select("span");
        for (Element number : numbers)
        {
            answerNumberList.add(Integer.valueOf(number.text()));
        }
    }

    public void print()
    {
        Log.e(TAG, "round : " + round);
        Log.e(TAG, "date : " + date);
        Log.e(TAG, "price : " + price);
        Log.e(TAG, "answer numbers : " + answerNumberList.toString());
    }

    /** * 추첨 결과 데이터 비교하였는지 여부 * @return */
    public String isCheck()
    {
        return answerNumberList.size() > 0 ? "1" : "0";
    }

    public String getRound()
    {
        return String.valueOf(round);
    }

    public String getDate()
    {
        return date;
    }

    public ArrayList getAnswerNumberList()
    {
        return answerNumberList;
    }

    public ArrayList getDetailModelArrayList()
    {
        return detailModelArrayList;
    }

    public String getPrice()
    {
        return price;
    }

    public boolean isWon(int _won)
    {
        for (int i = 0; i <answerNumberList.size(); i++)
        {
            if ((int) answerNumberList.get(i) == _won)
            {
                return true;
            }
        }
        return false;
    }

}
