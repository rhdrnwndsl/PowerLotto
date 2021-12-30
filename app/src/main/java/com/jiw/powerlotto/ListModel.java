package com.jiw.powerlotto;

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



}
