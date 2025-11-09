package com.example.myapplication.helper;



import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.model.ToHop;

import java.util.List;

public class TableTohopHelper {
    public static void addTableRow(TableLayout tablelayout, String []texts){
        TableRow row = new TableRow(tablelayout.getContext());
        for(String t:texts){
            row.addView(createCell(tablelayout,t));
        }
        tablelayout.addView(row);
    }
    public static TextView createCell(TableLayout tableLayout, String text){
        TextView tv = new TextView(tableLayout.getContext());
        tv.setText(text);
        tv.setPadding(16,16,16,16);
        tv.setTextSize(14);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public static void polulateTohopTable(TableLayout tableLayout, List<ToHop> dataList){
        tableLayout.removeAllViews();
        addTableRow(tableLayout,new String[]{"TT","Tên ngành","Mã ngành","Tổ hợp"});
        for(int i = 0 ; i < dataList.size();i++){
            ToHop tohop = dataList.get(i);
            addTableRow(tableLayout, new String []{
                    String.valueOf(i+1),
                    tohop.getName(),
                    tohop.getId(),
                    tohop.getTohop()
            });
        }
    }


}
