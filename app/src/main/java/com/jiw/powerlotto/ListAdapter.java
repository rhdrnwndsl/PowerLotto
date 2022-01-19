package com.jiw.powerlotto;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class ListAdapter extends ArrayAdapter<ListModel> {
    ArrayList<ListModel> listModelArrayList;
    int resource;
    PowerSDK mPowerSdk;
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<ListModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listModelArrayList = (ArrayList<ListModel>) objects;
        mPowerSdk = PowerSDK.getInstance();
    }

    /** * 해당 아이템 삭제
     * @param item
     */
    private void removeItem(ListModel item) {
        listModelArrayList.remove(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resource, parent, false); // 뷰 항목들 객체 초기화
            initViewItem(convertView);
        }

        // 표시 내용 초기화
        initViewDisplay(convertView, position);
        return convertView;
    }

    /** * 리스트뷰 최초 표시할 내용의 항목들 초기화 처리
     * @param convertView
     *
     */
    private void initViewItem(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textRound = convertView.findViewById(R.id.text_round);
        viewHolder.textDate = convertView.findViewById(R.id.text_date);
        viewHolder.textAnswerNumArrayList = new ArrayList<>();
        viewHolder.layoutAnswer = convertView.findViewById(R.id.layout_answer);
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num1));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num2));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num3));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num4));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num5));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_num6));
        viewHolder.textAnswerNumArrayList.add((TextView) convertView.findViewById(R.id.answer_plus));
        viewHolder.textPrice = convertView.findViewById(R.id.text_price);
        viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
        viewHolder.btnDelete.setOnClickListener(btnDeleteClick);
        viewHolder.viewDetailHolderArrayList = new ArrayList<>();
        viewHolder.viewDetailHolderArrayList.add(getViewDetailItem(convertView, R.id.layout_detail_a));
        viewHolder.viewDetailHolderArrayList.add(getViewDetailItem(convertView, R.id.layout_detail_b));
        viewHolder.viewDetailHolderArrayList.add(getViewDetailItem(convertView, R.id.layout_detail_c));
        viewHolder.viewDetailHolderArrayList.add(getViewDetailItem(convertView, R.id.layout_detail_d));
        viewHolder.viewDetailHolderArrayList.add(getViewDetailItem(convertView, R.id.layout_detail_e));
        convertView.setTag(viewHolder);
    }

    View.OnClickListener btnDeleteClick = new View.OnClickListener() {
        @Override public void onClick(final View view) {
            new AlertDialog.Builder(getContext())
                    .setMessage("해당 내용을 삭제하시겠습니까?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // 확인시 처리 로직
                            ListModel listModel = (ListModel) view.getTag();
                            removeItem(listModel);
                            notifyDataSetChanged();
                            try{
                                mPowerSdk.DeleteQRData(listModel.getUrl());
                            }
                            catch (SQLiteException ex)
                            {
                                Log.d("LIST_ADAPTER",ex.toString());
                            }
                        }}) .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소시 처리 로직
                                }}) .show();
        }
    };

    /** * 상세 항목 초기화하여 가져오기
     * @param parent *
     * @param resource *
     * @return
     */
    private ViewDetailHolder getViewDetailItem(View parent, int resource) {
        ViewDetailHolder viewDetailHolder = new ViewDetailHolder();
        viewDetailHolder.layoutDetail = parent.findViewById(resource);
        viewDetailHolder.textSeq = viewDetailHolder.layoutDetail.findViewById(R.id.text_seq);
        viewDetailHolder.textResult = viewDetailHolder.layoutDetail.findViewById(R.id.text_result);
        viewDetailHolder.textNumArrayList = new ArrayList<>();
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num1));
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num2));
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num3));
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num4));
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num5));
        viewDetailHolder.textNumArrayList.add((TextView) viewDetailHolder.layoutDetail.findViewById(R.id.text_num6));
        return viewDetailHolder;
    }

    private void initViewDisplay(View convertView, int position) {
        ListModel listModel = getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textRound.setText("제" + listModel.getRound() + "회");
        viewHolder.textDate.setText(listModel.getDate() + " 추첨");
        viewHolder.btnDelete.setTag(listModel);

        // 추첨 결과 있는지에 따라서 처리
        if(listModel.isCheck().equals("1")) {
            viewHolder.layoutAnswer.setVisibility(View.VISIBLE);
            viewHolder.textPrice.setVisibility(View.VISIBLE);
            for(int i=0; i<viewHolder.textAnswerNumArrayList.size(); i++) {
                TextView textAnswerNum = viewHolder.textAnswerNumArrayList.get(i);
                int num = (int) listModel.getAnswerNumberList().get(i);
                textAnswerNum.setText(String.valueOf(num));
                textAnswerNum.setBackgroundResource(getNumberBackgroundResource(num));
            }

            if(listModel.getPrice() == null) {
                viewHolder.textPrice.setText("다음 기회를 노려보세요");
            } else {
                viewHolder.textPrice.setText("당첨금 : " + listModel.getPrice());
            }
        } else {
            viewHolder.layoutAnswer.setVisibility(View.GONE);
            viewHolder.textPrice.setVisibility(View.GONE);
        }

        // 상세 항목 데이터 표시 초기화
        for(int i=0; i<5; i++) {
            initViewDetailDisplay(viewHolder, listModel, i);
        }
    }

    private void initViewDetailDisplay(ViewHolder viewHolder, ListModel listModel, int position) {
        ViewDetailHolder viewDetailHolder = (ViewDetailHolder) viewHolder.getViewDetailHolderArrayList().get(position);
        if(listModel.getDetailModelArrayList().size() <= position) {
            viewDetailHolder.layoutDetail.setVisibility(View.GONE);
            return;
        }

        viewDetailHolder.layoutDetail.setVisibility(View.VISIBLE);
        DetailModel detailModel = (DetailModel) listModel.getDetailModelArrayList().get(position);
        viewDetailHolder.textSeq.setText(detailModel.getSeq());
        viewDetailHolder.textResult.setText(detailModel.getResult());

        int _isWonCount = 0;
        for(int i=0; i<detailModel.getNumberList().size(); i++) {
            TextView textNum = viewDetailHolder.textNumArrayList.get(i);
            int num = (int) detailModel.getNumberList().get(i);
            boolean isWon = listModel.isWon(num);
            textNum.setText(String.valueOf(num));

            if(isWon) {
                textNum.setTextColor(Color.parseColor("#FFFFFF"));
                textNum.setBackgroundResource(getNumberBackgroundResource(num));
            } else {
                textNum.setTextColor(Color.parseColor("#000000"));
                textNum.setBackgroundResource(R.drawable.white_circle);
            }
        }
    }

    public int getNumberBackgroundResource(int number) {
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

    @Data
    class ViewHolder {
        TextView textRound;
        TextView textDate;
        LinearLayout layoutAnswer;
        TextView textPrice;
        ImageButton btnDelete;
        ArrayList<TextView> textAnswerNumArrayList;
        ArrayList<ViewDetailHolder> viewDetailHolderArrayList;

        public ArrayList getViewDetailHolderArrayList()
        {
            return viewDetailHolderArrayList;
        }
    }

    @Data class ViewDetailHolder {
        LinearLayout layoutDetail;
        TextView textSeq;
        TextView textResult;
        ArrayList<TextView> textNumArrayList;
    }
}

