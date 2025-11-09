package com.example.myapplication.ui.module2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Module2DataAdapter;
import com.example.myapplication.adapter.TruongAdapterTotal;
import com.example.myapplication.databinding.FragmentModule2Binding;
import com.example.myapplication.model.Module2Data;
import com.example.myapplication.model.Truong;
import com.example.myapplication.ui.login.LoginViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module2Fragment extends Fragment {

    private List<String> danhSachMon;
    private String monDuocChon1 = "",monDuocChon2="",monDuocChon3="";
    private EditText editTexttoan, editTextvan, editTextmon3, editTextmon4, editTextmon5, editTextdiemcong, editTexthsa, editTexttsa,editTextmien,editTexttp,editTextnganh,editTextDh;
    private Spinner spinnerMon1,spinnerMon2,spinnerMon3;
    private Button btnDetails;
    private Module2DataAdapter truongAdapter;
    private RecyclerView recyclerView;
    private Module2ViewModel viewModel;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_module2, container, false);

         viewModel = new ViewModelProvider(this).get(Module2ViewModel.class);
         spinnerMon1 = view.findViewById(R.id.spinnerMon1);
         spinnerMon2 = view.findViewById(R.id.spinnerMon2);
         spinnerMon3 = view.findViewById(R.id.spinnerMon3);
         editTextdiemcong = view.findViewById(R.id.plus);
         editTexttoan = view.findViewById(R.id.toan);
         editTextvan = view.findViewById(R.id.van);
         editTextmon3=view.findViewById(R.id.mon3);
         editTextmon4=view.findViewById(R.id.mon4);
         editTextmon5=view.findViewById(R.id.mon5);
         editTextdiemcong=view.findViewById(R.id.plus);
         editTexthsa=view.findViewById(R.id.hsa);
         editTexttsa=view.findViewById(R.id.tsa);
         editTextmien =view.findViewById(R.id.region);
         editTexttp = view.findViewById(R.id.city);
         editTextnganh = view.findViewById(R.id.expected_major);
         editTextDh = view.findViewById(R.id.expected_univertity);
         btnDetails = view.findViewById(R.id.btnDetail);
         recyclerView = view.findViewById(R.id.recyclerViewModule2Data);
         danhSachMon = Arrays.asList("Chọn","Tiếng Anh", "Lý", "Hoá", "Sinh", "Sử", "Địa", "Tin");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                danhSachMon
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        truongAdapter = new Module2DataAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(truongAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        spinnerMon1.setAdapter(adapter);
        spinnerMon2.setAdapter(adapter);
        spinnerMon3.setAdapter(adapter);

        spinnerMon1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemView, int position, long id) {
                monDuocChon1 = danhSachMon.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monDuocChon1 = "";
            }
        });

        spinnerMon2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemView, int position, long id) {
                monDuocChon2 = danhSachMon.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monDuocChon2 = "";
            }
        });

        spinnerMon3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemView, int position, long id) {
                monDuocChon3 = danhSachMon.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monDuocChon3 = "";
            }
        });

        btnDetails.setOnClickListener(v ->{
            String toan = editTexttoan.getText().toString().trim();
            String van = editTextvan.getText().toString().trim();
            String mon3 = editTextmon3.getText().toString().trim();
            String mon4 = editTextmon4.getText().toString().trim();
            String mon5 = editTextmon5.getText().toString().trim();
            String diemcong = editTextdiemcong.getText().toString().trim();
            String hsa = editTexthsa.getText().toString().trim();
            String tsa = editTexttsa.getText().toString().trim();
            String tenmon3 = monDuocChon1;
            String tenmon4 = monDuocChon2;
            String tenmon5 = monDuocChon3;
            String mien = editTextmien.getText().toString().trim();
            String tp = editTexttp.getText().toString().trim();
            String nganh = editTextnganh.getText().toString().trim();
            String dh = editTextDh.getText().toString().trim();
            viewModel.sendDatatoServer(toan,van,mon3,mon4,mon5,tenmon3,tenmon4,tenmon5,diemcong,hsa,tsa,mien,tp,nganh,dh);


        });
        viewModel.getModule2DataListLiveData().observe(getViewLifecycleOwner(),this::showTruongList);
        return view;

    }
    private void showTruongList(List<Module2Data> list) {
        if (list != null) {
            truongAdapter.updateData(list);
            Toast.makeText(requireContext(), "Loaded successfully universities", Toast.LENGTH_SHORT).show();
        } else {
            truongAdapter.updateData(new ArrayList<>());
            Toast.makeText(requireContext(), "No data received from server", Toast.LENGTH_LONG).show();
        }
    }
}
