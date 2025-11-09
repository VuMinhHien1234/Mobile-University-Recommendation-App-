package com.example.myapplication.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.model.Mohinh;


import java.util.List;

public class TableMohinhHelper {
    private static void addTableRow(TableLayout tableLayout, String[] texts){
        TableRow row = new TableRow(tableLayout.getContext());
        for(String t: texts){
            row.addView(createCell(tableLayout, t));
        }
        tableLayout.addView(row);
    }

    private static TextView createCell(TableLayout tableLayout, String text){
        TextView tv = new TextView(tableLayout.getContext());
        tv.setText(text);
        tv.setPadding(16,16,16,16);
        tv.setTextSize(14);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public static void populateNganhTable(TableLayout tableLayout, List<Mohinh> dataList){
        tableLayout.removeAllViews();
        addTableRow(tableLayout, new String[]{"TT", "Mã ngành", "Ngành đào tạo", "BVH", "BVS", "Phạm vi"});
        for(int i =0 ; i < dataList.size();i++){
            Mohinh nganh = dataList.get(i);
            addTableRow(tableLayout, new String[]{
                    String.valueOf(i+1),
                    nganh.getId(),
                    nganh.getName(),
                    String.valueOf(nganh.getBvh()),
                    String.valueOf(nganh.getBvs()),
                    nganh.getPhamvi()
            });
        }
    }

}
